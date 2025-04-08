package com.example.telememo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    /* Gemini prompts
    * Promt :hay una forma que cuando incie el juego, el boton se esconda?
    * Promt: en gameactivity esta como :
private GridLayout  gridPalabras;//Seleccionar temática
        tematicaSeleccionada = getIntent().getStringExtra("tematica");
        gridPalabras = findViewById(R.id.gridPalabras); como soluciono el error?
    * Promt: como puedo tomar tiempo de la duracion de la partida? para mostrarlo en mensajes
    *
    *Promt: cuando presiono el boton de retroceder en el manu_estadistica, se detiene la simulacion.
    * a que se debe el problema?
    * */

    private GridLayout   gridPalabras;
    private int intentos = 0;
    private final int max_intentos = 3;
    private String tematicaSeleccionada;
    private List<String> oracionCorrecta;
    private List<String> palabrasDesordenadas;
    private List<String> seleccionUsuario = new ArrayList<>();
    private List<Button> botonesPalabras;
    private TextView textViewMensajes;
    private  Button btnJugar;
    private long tiempoInicio;
    private long tiempoFin;

    //Indicador si el juego terminó o no
    private boolean juegoTerminado = false;


    //menuu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_estadisticas) {
            Intent intent = new Intent(this, EstadisticasActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_reproceso) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Seleccionar temática
        tematicaSeleccionada = getIntent().getStringExtra("tematica");
        gridPalabras = findViewById(R.id.gridPalabras);

        if (tematicaSeleccionada == null) {
            // Looogsssssss
            Log.e("GameActivity", "Temática no recibida");
        }

        //Boton para iniciar juego
        btnJugar = findViewById(R.id.btnJugar);
        btnJugar.setOnClickListener(v -> {
            // Al presionar el botón "Iniciar" o "Nuevo juego", debería reiniciar la partida
            iniciarNuevoJuego();
        });

        //menu
        Toolbar toolbar = findViewById(R.id.topToolbar);
        setSupportActionBar(toolbar);



        //Texto de mensajes
        textViewMensajes = findViewById(R.id.textViewMensajes);

        // Inicializar juego
        inicializarJuego();
    }

    private void inicializarJuego() {
        oracionCorrecta = obtenerOracionCorrecta(tematicaSeleccionada);
        palabrasDesordenadas = new ArrayList<>(oracionCorrecta);
        Collections.shuffle(palabrasDesordenadas);
        cargarBotones();
        btnJugar.setText("Iniciar");

        //esconder boton porque me confunde
        btnJugar.setVisibility(View.VISIBLE);


        // Romar tiempo de la duracion de la partida para mostrarlo en mensajes
        tiempoInicio = System.currentTimeMillis();
    }

    private void reiniciarJuego() {
        //Si aun no termina el juego
        if (!juegoTerminado) {
            cancelarJuegoActual();
        }else {
            inicializarJuego();
        }
    }

    private void cancelarJuegoActual(){
        registrarEstadistica("Canceló");
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void iniciarNuevoJuego(){

        intentos = 0;
        seleccionUsuario.clear();
        juegoTerminado = false;
        tiempoInicio = System.currentTimeMillis();

        // Cargar nuevas palabras
        palabrasDesordenadas = new ArrayList<>(oracionCorrecta);
        Collections.shuffle(palabrasDesordenadas);
        cargarBotones();

        //Cambiar el texto del boton para indicar que es un nuevo juego
        btnJugar.setText("Nuevo juego");
        btnJugar.setVisibility(View.VISIBLE);

        // Limpiar el mensaje en el TextView
        textViewMensajes.setText("¡Juega de nuevo!");
    }

    private void registrarEstadistica(String resultado) {
        Log.d("Estadística", "Resultado: " + resultado + ", Temática: " + tematicaSeleccionada);


    }

    private List<String> obtenerOracionCorrecta(String tematica) {
        List<String> oracion = new ArrayList<>();

        String frase ="";

        switch (tematica) {
            case "Software":
                frase = new Random().nextBoolean() ?
                        "La fibra óptica envía datos a gran velocidad evitando cualquier interferencia eléctrica" :
                        "Los amplificadores EDFA mejoran la señal óptica en redes de larga distancia";
                break;
            case "Ciberseguridad":
                frase = new Random().nextBoolean() ?
                        "Una VPN encripta tu conexión para navegar de forma anónima y segura" :
                        "El ataque DDoS satura servidores con tráfico falso y causa caídas masivas";
                break;
            case "Ópticas":
                frase = new Random().nextBoolean() ?
                        "Los fragments reutilizan partes de pantalla en distintas actividades de la app" :
                        "Los intents permiten acceder a apps como la cámara o WhatsApp directamente";
                break;
        }
        oracion = Arrays.asList(frase.split(" "));
        return oracion;
    }

    private void cargarBotones() {
        gridPalabras.removeAllViews();
        botonesPalabras = new ArrayList<>();

        for (String palabra : palabrasDesordenadas) {
            Button boton = new Button(this);
            boton.setText("???");
            boton.setTag(palabra);
            boton.setOnClickListener(v -> manejarSeleccion((Button) v));
            botonesPalabras.add(boton);
            gridPalabras.addView(boton);
            //Logs para ver la palabra escondida que ni confio en el ramdom
            Log.d("GameActivity", "Cargando botón con palabra: " + palabra);
        }
    }

    private void manejarSeleccion(Button boton) {
        String palabra = (String) boton.getTag();
        int indiceSeleccion = seleccionUsuario.size();

        // Logs otra vez porque no confio :'v
        Log.d("GameActivity", "Palabra seleccionada: " + palabra + ", índice de selección: " + indiceSeleccion);

        if (palabra.equals(oracionCorrecta.get(indiceSeleccion))) {
            boton.setText(palabra); // Mostrar palabra
            boton.setEnabled(false);
            seleccionUsuario.add(palabra);

            // Mas logs
            Log.d("GameActivity", "Palabra correcta seleccionada: " + palabra);



            if (seleccionUsuario.size() == oracionCorrecta.size()) {

                tiempoFin = System.currentTimeMillis();
                long tiempoTranscurrido = (tiempoFin - tiempoInicio)/1000;
                textViewMensajes.setText("GANO /Duración: " + tiempoTranscurrido + " segundos.");
                textViewMensajes.setText("Número de Intentos: " + intentos);

                juegoTerminado = true;

                // Mostrar mensaje en el TextView
                textViewMensajes.setText("¡Correcto! Has completado la oración :D.");
                btnJugar.setText("Nuevo juego");
                btnJugar.setVisibility(View.VISIBLE);

            }
        } else {
            intentos++;
            //Mas logs x2
            Log.d("GameActivity", "Palabra incorrecta: " + palabra + ", intentos restantes: " + (max_intentos - intentos));

            textViewMensajes.setText("Palabra incorrecta: " + palabra + ", intentos restantes: " + (max_intentos - intentos));

            reiniciarSeleccion();
            if (intentos >= max_intentos) {
                tiempoFin = System.currentTimeMillis();
                long tiempoTranscurrido = (tiempoFin - tiempoInicio)/1000;


                //mostrarMensaje("Juego terminado. Se acabaron los intentos :(.");
                textViewMensajes.setText("Juego terminado. Se acabaron los intentos :(");
                textViewMensajes.setText("PERDIO / Duración: " + tiempoTranscurrido + " segundos.");
                juegoTerminado = true;
                btnJugar.setText("Nuevo juego");
                btnJugar.setVisibility(View.VISIBLE);
                deshabilitarBotones();
            }
        }
    }

    private void reiniciarSeleccion() {
        seleccionUsuario.clear();
        for (Button boton : botonesPalabras) {
            if (boton.isEnabled()) {
                boton.setText("???");
            }
        }
    }

    private void deshabilitarBotones() {
        for (Button boton : botonesPalabras) {
            boton.setEnabled(false);
        }
    }

}
