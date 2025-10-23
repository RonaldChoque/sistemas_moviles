package com.example.aplicacion1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtener los elementos de la interfad
        EditText editText = findViewById(R.id.texto);
        Button button = findViewById(R.id.boton);

        //Configurar el listener

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = editText.getText().toString();

                //Mostrar el mensaje Toast
                Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_LONG).show();
            }
        });
    }
}