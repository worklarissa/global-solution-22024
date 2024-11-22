package com.larissa.ecoenergy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var btnComecar: Button = findViewById(R.id.btnComecar)

        btnComecar.setOnClickListener {
            val navergarMain = Intent(this, TelaLogin::class.java)
            startActivity(navergarMain)
        }


    }

}