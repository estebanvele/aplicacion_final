package com.esteban.aplicacion_final.almacen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.esteban.aplicacion_final.R;
import com.esteban.aplicacion_final.producto.CrearProducto;

public class MainActivityAlmacen extends AppCompatActivity {

    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_almacen);

        nombre = getIntent().getStringExtra("nombre");

        TextView titulo = findViewById(R.id.titulo);
        titulo.setText("Dash Board \n" + nombre);

        Button crearProducto = findViewById(R.id.crear_btn);
        Button salir = findViewById(R.id.salir_btn);
        Button ver = findViewById(R.id.ver_btn);
        Button editar = findViewById(R.id.editar_btn);

        crearProducto.setOnClickListener(v -> abrirCrearProducto());
        salir.setOnClickListener(v -> salirDashboard());
        ver.setOnClickListener(v -> verProductos());
    }

    private void abrirCrearProducto() {
        Intent intentCrear = new Intent(getApplicationContext(), CrearProducto.class)
                .putExtra("nombre", nombre);
        startActivity(intentCrear);
    }

    private void salirDashboard() {
        Intent intentSalir = new Intent(getApplicationContext(), LoginAlmacenActivity.class);
        startActivity(intentSalir);
        finish();
    }

    private void verProductos() {
        Intent intentVer = new Intent(getApplicationContext(), VerMisProductosActivity.class)
                .putExtra("nombreAlmacen", nombre);
        startActivity(intentVer);
    }


}
