package com.larissa.ecoenergy

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TelaCalculadoraConsumo : AppCompatActivity() {

    private lateinit var btnCalculadora: Button
    private lateinit var valorWatts: EditText
    private lateinit var valorHoras: EditText
    private lateinit var resultado: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_calculadora_consumo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnCalculadora = findViewById(R.id.btnCalcular)
        valorWatts = findViewById(R.id.inputWatts)
        valorHoras = findViewById(R.id.inputHoras)
        resultado = findViewById(R.id.txtResultado)

        btnCalculadora.setOnClickListener {
            calcularConsumoDiario()
        }
    }

    fun calcularConsumoDiario( ){
       val numWatts = valorWatts.text.toString().toFloatOrNull();
        val numHoras = valorHoras.text.toString().toFloatOrNull();

        if (numWatts != null && numHoras != null) {
            val conta = (numWatts * numHoras) / 1000f
            resultado.text = "O valor do consumo diário do seu equipamento é: ${conta} kWh".format(conta)
        } else {
            resultado.text = "Por favor, insira ambos os números";
        }
    }
}

