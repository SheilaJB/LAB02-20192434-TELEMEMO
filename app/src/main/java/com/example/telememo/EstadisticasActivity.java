package com.example.telememo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EstadisticasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        // Enlazar y configurar la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnNuevoJuego = findViewById(R.id.btnNuevoJuego);
        btnNuevoJuego.setOnClickListener(v -> {
            Intent intent = new Intent(EstadisticasActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close this activity
        });
    }

    // Menú :'v
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_estadistica, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_reproceso) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
