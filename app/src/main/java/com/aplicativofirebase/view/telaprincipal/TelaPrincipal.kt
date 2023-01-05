package com.aplicativofirebase.view.telaprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aplicativofirebase.databinding.ActivityTelaPrincipalBinding
import com.aplicativofirebase.view.formlogin.FormLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPrincipalBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btDeslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val voltarTelaLogin = Intent(this, FormLogin::class.java)
            startActivity(voltarTelaLogin)
            finish()
        }

        // SALVANDO DADOS NO DB
        binding.btGravarDadosDB.setOnClickListener {

            val usuariosMap = hashMapOf(
                "nome" to "Katariny",
                "sobrenome" to "Bueno Alves",
                "idade" to 24
            )

            db.collection("Usuários").document("Katariny")
                .set(usuariosMap).addOnCompleteListener {
                    Log.d("db_save", "Sucesso ao salvar dados do usuário!")
                }.addOnFailureListener {

                }
        }

        // LER DADOS DO DB
//        binding.btLerDadosDB.setOnClickListener {
//            db.collection("Usuários").document("Katariny")
//                .addSnapshotListener { documento, error ->
//                    if (documento != null) {
//                        val idade = documento.getLong("idade")
//                        binding.txtResultadoNome.text = documento.getString("nome")
//                        binding.txtResultadoSobrenome.text = documento.getString("sobrenome")
//                        binding.txtResultadoIdade.text = idade.toString()
//
//                    }
//                }
//        }

        // LER DADOS DO DB
        binding.btLerDadosDB.setOnClickListener {
            db.collection("Usuários").document("Tales")
                .addSnapshotListener { documento, error ->
                    if (documento != null) {
                        val idade = documento.getLong("idade")
                        binding.txtResultadoNome.text = documento.getString("nome")
                        binding.txtResultadoSobrenome.text = documento.getString("sobrenome")
                        binding.txtResultadoIdade.text = idade.toString()

                    }
                }
        }

        // ATUALIZAR DADOS NO DB
        binding.btAtualizarDadosDB.setOnClickListener {
            db.collection("Usuários").document("Tales")
                .update("sobrenome", "Andrade dos Santos", "idade", 26).addOnCompleteListener {
                    Log.d("db_update", "Sucesso ao atualizar os dados do usuário!")

                }
        }

        binding.btDeletarDadosDB.setOnClickListener {
            db.collection("Usuários").document("Katariny")
                .delete().addOnCompleteListener {
                    Log.d("db_delete", "Sucesso ao excluir os dados do usuário!")

                }
        }

    }
}