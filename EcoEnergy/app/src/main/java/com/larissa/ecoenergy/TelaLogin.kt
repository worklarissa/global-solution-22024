package com.larissa.ecoenergy

import android.content.Intent
import android.os.Bundle
//import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.larissa.ecoenergy.databinding.ActivityTelaLoginBinding

class TelaLogin : AppCompatActivity() {

    private lateinit var binding: ActivityTelaLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            logarUsuario()
        }
    }

    private fun logarUsuario(){
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputSenha.text.toString().trim()

        if(email.isEmpty() || password.isEmpty()) {
            showErrorDialog("Por favor, preencha todos os campos")
            return
        }

        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                startActivity(Intent(this,TelaMenu::class.java))
                finish()
            }
            .addOnFailureListener{exception->
                showErrorDialog("Erro ao fazer login. Verifique seu email e senha")
            }
    }

    private fun showErrorDialog(message: String){
        AlertDialog.Builder(this)
            .setTitle("Erro")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}