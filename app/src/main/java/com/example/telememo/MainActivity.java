package com.example.telememo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnOpticas, btnSoftware, btnCiberseguridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar los botones
        btnOpticas = findViewById(R.id.btnOpticas);
        btnSoftware = findViewById(R.id.btnSoftware);
        btnCiberseguridad = findViewById(R.id.btnCiberseguridad);


        btnSoftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanzar el juego con la temática de Software
                lanzarJuegoConTematica("Software");
            }
        });

        btnCiberseguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanzar el juego con la temática de Ciberseguridad
                lanzarJuegoConTematica("Ciberseguridad");
            }
        });
        btnOpticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanzar el juego con la temática de Ópticas
                lanzarJuegoConTematica("Ópticas");
            }
        });
    }
    private void lanzarJuegoConTematica(String tematica) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("tematica", tematica);
        startActivity(intent);
    }
}