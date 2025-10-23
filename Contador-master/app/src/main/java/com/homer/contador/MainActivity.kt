package com.homer.contador

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge

class MainActivity : AppCompatActivity() {

    private lateinit var buttonIncrement: Button
    private lateinit var textCount: TextView

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        buttonIncrement = findViewById(R.id.btn_inc)
        textCount = findViewById(R.id.tv_num)

        // Configurar el listener del botón
        buttonIncrement.setOnClickListener {
            count++
            textCount.text = count.toString()
        }

    }
}
