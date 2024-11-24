package com.larissa.ecoenergy

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.larissa.ecoenergy.api.Pensamento
import com.larissa.ecoenergy.api.PensamentoApi
import com.larissa.ecoenergy.api.RetrofitHelper
import com.larissa.ecoenergy.databinding.ActivityTelaEditarIdeiaBinding
import kotlinx.coroutines.launch

class TelaEditarIdeia : AppCompatActivity() {

    private lateinit var binding: ActivityTelaEditarIdeiaBinding
    private val retrofitService by lazy { RetrofitHelper.retrofit.create(PensamentoApi::class.java) }
    private var posicaoIdeia: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaEditarIdeiaBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        posicaoIdeia = intent.getIntExtra("POSICAO_IDEIA", -1)
        val textoOriginal = intent.getStringExtra("TEXTO_IDEIA") ?: ""
        binding.editTextIdeia.setText(textoOriginal)

        binding.btnCriar.setOnClickListener { criarIdeia() }
        binding.btnEditar.setOnClickListener { editarIdeia() }
        binding.btnDeletar.setOnClickListener { deletarIdeia() }

        if (posicaoIdeia == -1) {
            binding.btnEditar.visibility = View.GONE
            binding.btnDeletar.visibility = View.GONE
        } else {
            binding.btnCriar.visibility = View.GONE
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun criarIdeia() {
        val opiniao = binding.editTextIdeia.text.toString()
        if (opiniao.isBlank()) {
            Toast.makeText(this, "Por favor, digite uma ideia", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val novaIdeia = Pensamento(opiniao = opiniao)
                val response = retrofitService.criarPensamento(novaIdeia)
                if (response.isSuccessful) {
                    Toast.makeText(this@TelaEditarIdeia, "Ideia criada com sucesso", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    runOnUiThread {
                        Toast.makeText(this@TelaEditarIdeia, "Erro ao criar ideia: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Erro de conexão ${e.message}"

                Toast.makeText(this@TelaEditarIdeia, "Erro de conexão: ${e.message}", Toast.LENGTH_SHORT).show()

                Log.e("TelaCriarIdeia", errorMessage)

            }
        }
    }

    private fun editarIdeia() {
        val opiniao = binding.editTextIdeia.text.toString()
        if (opiniao.isBlank()) {
            Toast.makeText(this, "Por favor, digite uma ideia", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                Log.d("TelaEditarIdeia", "ID da ideia recebido: $posicaoIdeia")
                Log.d("TelaEditarIdeia", "Tentando atualizar ideia na posição: $posicaoIdeia")

                if (posicaoIdeia == -1) {
                    Log.e("TelaEditarIdeia", "ID da ideia inválido")
                    Toast.makeText(this@TelaEditarIdeia, "Erro: ID da ideia inválido", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val ideiaAtualizada = mapOf("pensamento" to opiniao)
                val response = retrofitService.atualizarPensamento(posicaoIdeia, ideiaAtualizada)

                if (response.isSuccessful) {
                    Log.d("TelaEditarIdeia", "Ideia atualizada com sucesso")
                    Toast.makeText(this@TelaEditarIdeia, "Ideia atualizada com sucesso", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorCode = response.code()
                    Log.e("TelaEditarIdeia", "Erro ao atualizar ideia. Código: $errorCode, Erro: $errorBody")
                    Toast.makeText(this@TelaEditarIdeia, "Erro ao atualizar ideia", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                val errorMessage = "Erro de conexão ${e.message}"

                Toast.makeText(this@TelaEditarIdeia, "Erro de conexão: ${e.message}", Toast.LENGTH_SHORT).show()

                Log.e("TelaEditarIdeia", errorMessage, e)

            }
        }
    }

    private fun deletarIdeia() {
        lifecycleScope.launch {
            try {
                Log.d("TelaEditarIdeia", "Tentando deletar ideia na posição: $posicaoIdeia")
                val response = retrofitService.deletarPensamento(posicaoIdeia)
                if (response.isSuccessful) {
                    Toast.makeText(this@TelaEditarIdeia, "Ideia excluída com sucesso", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorCode = response.code()
                    Log.e("TelaEditarIdeia", "Erro ao excluir ideia. Código: $errorCode, Erro: $errorBody")
                    Toast.makeText(this@TelaEditarIdeia, "Erro ao excluir ideia", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                val errorMessage = "Erro de conexão ${e.message}"

                Toast.makeText(this@TelaEditarIdeia, "Erro de conexão: ${e.message}", Toast.LENGTH_SHORT).show()

                Log.e("TelaCriarIdeia", errorMessage, e)

            }
        }
    }
}