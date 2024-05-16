package com.esteban.aplicacion_final.almacen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.esteban.aplicacion_final.producto.CrearProducto;
import com.esteban.aplicacion_final.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityAlmacen extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private String email, contraseña, nombre;
    private TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_almacen);

        //Necesario para hacer consultas al Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Traigo la info del perfil para así poder mandarla a las otras secciones
        email = getIntent().getStringExtra("email");
        contraseña = getIntent().getStringExtra("contraseña");
        nombre = getIntent().getStringExtra("nombre");

        titulo = findViewById(R.id.titulo);
        titulo.setText("Dash Board \n" + nombre);

        Button crearProducto = findViewById(R.id.crear_btn);

        crearProducto.setOnClickListener(v -> abrirCrearProducto());
    }

    public void abrirCrearProducto() {
        Intent intent = new Intent(this, CrearProducto.class)
                .putExtra("nombre", nombre);
        startActivity(intent);
    }

}