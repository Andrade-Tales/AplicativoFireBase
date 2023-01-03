package com.aplicativofirebase.view.formcadastro

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplicativofirebase.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // INTERAÇÃO COM A CAIXA DE TEXTO EMAIL E SENHA!
        binding.btCadastrar.setOnClickListener { view ->

            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            // POP UP DE AVISO SNACKBAR!
            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar =
                    Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { cadastro ->
                        if (cadastro.isSuccessful) {
                            val snackbar = Snackbar.make(
                                view,
                                "Sucesso ao cadastrar usuário!",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.setBackgroundTint(Color.BLUE)
                            snackbar.show()

                            // DEIXANDO A CAIXA DE TEXTO VAZIA APÓS A INSERÇÃO DE EMAIL E SENHA!
                            binding.editEmail.setText("")
                            binding.editSenha.setText("")
                        }
                    }.addOnFailureListener { exception ->

                        // TRATAMENTO DE EXCEPTION!
                        val mensagemErro = when (exception) {
                            is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres!"
                            is FirebaseAuthInvalidCredentialsException -> "Digite um email válido!"
                            is FirebaseAuthUserCollisionException -> "Esta conta já foi cadastrada!"
                            is FirebaseNetworkException -> "Sem conexão com a internet!"
                            else -> "Erro ao cadastrar usuário!"
                        }
                        val snackbar =
                            Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
            }
        }
    }
}