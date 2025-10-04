package com.example.clubdeportivobola8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) //muestra pantalla inicial al abrir la app
        configurarPantallaInicial()
    }

    private fun configurarPantallaInicial() {
        val btnLogin =
            findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnIniciarSesion)
        val btnRegister =
            findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnRegistrarse)

        // Configurar btns de los forms Iniciar Sesion y Registrarse
        btnLogin.setOnClickListener {
            setContentView(R.layout.activity_login_form) // Cambia a formulario login
            configurarFormularioLogin()
        }

        btnRegister.setOnClickListener {
            setContentView(R.layout.activity_register_form) // Cambia a formulario registro
            configurarFormularioRegistro()
        }
    }

            //Botones de los forms para atras y cancelar (vuelven a pantalla login inicial)
    private fun configurarFormularioLogin() {
        val btnBack = findViewById<ImageButton>(R.id.btnBackLogin)
        btnBack.setOnClickListener {
            setContentView(R.layout.login)
            configurarPantallaInicial()
        }
    }

    private fun configurarFormularioRegistro() {
        val btnCancel = findViewById<TextView>(R.id.btnCancelRegister)
        btnCancel.setOnClickListener {
            setContentView(R.layout.login)
            configurarPantallaInicial()
        }
    }



}



