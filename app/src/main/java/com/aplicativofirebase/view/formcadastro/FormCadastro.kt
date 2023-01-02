package com.aplicativofirebase.view.formcadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplicativofirebase.R
import com.aplicativofirebase.databinding.ActivityFormCadastroBinding

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}