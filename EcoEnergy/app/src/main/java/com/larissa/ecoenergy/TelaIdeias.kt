package com.larissa.ecoenergy

import android.content.Intent
import android.os.Bundle
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

class TelaIdeias : AppCompatActivity() {

    private lateinit var btnCriar: Button
    private lateinit var listaIdeias: RecyclerView
    private lateinit var adapter: PensamentoAdapter

    private val retrofitService by lazy{
        RetrofitHelper.retrofit.create(PensamentoApi::class.java)
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
            val navegarEditIdea = Intent(this, TelaEditarIdeia::class.java)
            startActivity(navegarEditIdea)

        }

    }

    private fun fetchPensamentos() {
        retrofitService.getPensamentos().enqueue(object : Callback<List<Pensamento>> {
             override fun onResponse(call: Call<List<Pensamento>>, response: Response<List<Pensamento>>) {
                if (response.isSuccessful) {
                    val pensamentos = response.body()
                    pensamentos?.let {
                        adapter = PensamentoAdapter(it)
                        listaIdeias.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@TelaIdeias, "Erro ao carregar pensamentos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Pensamento>>, t: Throwable) {
                Toast.makeText(this@TelaIdeias, "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
