package com.larissa.ecoenergy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TelaMenu : AppCompatActivity() {
    
    private lateinit var btnCalculadoraC : Button
    private lateinit var btnIdeias : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnCalculadoraC = findViewById(R.id.btnCalculadoraConsumo)
        btnIdeias = findViewById(R.id.btnIdeias)

        btnCalculadoraC.setOnClickListener {
            val navegarCalcC = Intent(this, TelaCalculadoraConsumo::class.java)
            startActivity(navegarCalcC)
        }

        btnIdeias.setOnClickListener {
            val navegarIdeias = Intent(this, TelaIdeias::class.java)
            startActivity(navegarIdeias)
        }
    }
}