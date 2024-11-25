package com.larissa.ecoenergy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.larissa.ecoenergy.api.Pensamento
import com.larissa.ecoenergy.api.PensamentoAdapter
import com.larissa.ecoenergy.api.PensamentoApi
import com.larissa.ecoenergy.api.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity


class TelaIdeias : AppCompatActivity() {

    companion object {
        private const val CRIAR_IDEIA_REQUEST = 1
        private const val EDITAR_IDEIA_REQUEST = 2
    }

    private lateinit var btnCriar: Button
    private lateinit var listaIdeias: RecyclerView
    private lateinit var adapter: PensamentoAdapter

    private fun onItemClick(pensamento: Pensamento) {
        val intent = Intent(this, TelaEditarIdeia::class.java)
        intent.putExtra("ID_IDEIA", pensamento.id)
        intent.putExtra("TEXTO_IDEIA", pensamento.opiniao)
        editarIdeiaLauncher.launch(intent)
    }


    private val retrofitService by lazy{
        RetrofitHelper.retrofit.create(PensamentoApi::class.java)
    }

    private val criarIdeiaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            fetchPensamentos()
        }
    }

    private val editarIdeiaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            fetchPensamentos()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_ideias)

        listaIdeias = findViewById(R.id.ListaIdeias)
        listaIdeias.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchPensamentos()

        btnCriar= findViewById(R.id.btnCriarIdeia)
        btnCriar.setOnClickListener {
            val intent = Intent(this, TelaEditarIdeia::class.java)
            intent.putExtra("ID_IDEIA", -1)
            criarIdeiaLauncher.launch(intent)

        }

    }

    private fun fetchPensamentos() {
        retrofitService.getPensamentos().enqueue(object : Callback<List<Pensamento>> {
             override fun onResponse(call: Call<List<Pensamento>>, response: Response<List<Pensamento>>) {
                if (response.isSuccessful) {
                    val pensamentos = response.body()
                    pensamentos?.let {
                        adapter = PensamentoAdapter(it) { pensamento ->
                            onItemClick(pensamento)
                        }
                        listaIdeias.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@TelaIdeias, "Erro ao carregar pensamentos", Toast.LENGTH_SHORT).show()
                    Log.e("TelaIdeias", "Erro ao carregar pensamentos: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Pensamento>>, t: Throwable) {
                Toast.makeText(this@TelaIdeias, "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("TelaIdeias", "Falha na conexão: ${t.message}")
            }
        })
    }



    fun onActivity(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CRIAR_IDEIA_REQUEST, EDITAR_IDEIA_REQUEST -> {
                    // Lógica para lidar com a criação de uma nova ideia
                    fun iniciarCriacaoIdeia() {
                        val intent = Intent(this, TelaEditarIdeia::class.java)
                        startActivityForResult(intent, CRIAR_IDEIA_REQUEST)
                    }
                    fetchPensamentos() // Atualiza a lista de ideias
                }
                EDITAR_IDEIA_REQUEST -> {
                    // Lógica para lidar com a edição de uma ideia
                    fun iniciarEdicaoIdeia(position: Int, texto: String) {
                        val intent = Intent(this, TelaEditarIdeia::class.java)
                        intent.putExtra("POSICAO_IDEIA", position)
                        intent.putExtra("TEXTO_IDEIA", texto)
                        startActivityForResult(intent, EDITAR_IDEIA_REQUEST)
                    }
                    fetchPensamentos() // Atualiza a lista de ideias
                }
            }
        }
    }




}
