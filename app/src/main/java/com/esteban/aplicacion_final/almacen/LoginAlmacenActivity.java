package com.esteban.aplicacion_final.almacen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.esteban.aplicacion_final.R;
import com.esteban.aplicacion_final.models.Almacen;
import com.esteban.aplicacion_final.usuario.LoginUsuarioActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAlmacenActivity extends AppCompatActivity {

    private EditText etEmail, etContraseña;
    private Button btnIniciarSesion, btnRegistrarNuevoAlmacen, btnLoginUsuario;
    private String almacenKey;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_almacen);

        // Inicializa la referencia de la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference("almacenes");

        // Vincula las vistas
        etEmail = findViewById(R.id.editTextEmail);
        etContraseña = findViewById(R.id.editTextContraseña);
        btnIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        btnRegistrarNuevoAlmacen = findViewById(R.id.buttonRegistrarNuevoAlmacen);
        btnLoginUsuario = findViewById(R.id.buttonLoginUsuario);

        // Configura el clic del botón de inicio de sesión
        btnIniciarSesion.setOnClickListener(view -> verificarInformacionAlmacen());

        // Configura el clic del botón para registrar un nuevo almacén
        btnRegistrarNuevoAlmacen.setOnClickListener(view -> abrirRegistroAlmacen());
        btnLoginUsuario.setOnClickListener(view -> abrirLoginUsuario());
    }

    private void verificarInformacionAlmacen() {
        String email = etEmail.getText().toString().trim();
        String contraseña = etContraseña.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(contraseña)) {
            Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el formato del email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Consulta la información del almacén en Firebase
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // El almacén con este correo electrónico existe en la base de datos
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Almacen almacen = snapshot.getValue(Almacen.class);
                        if (almacen != null && almacen.getContraseña().equals(contraseña)) {
                            // Contraseña correcta, permitir acceso al almacén
                            Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            // Redirige al usuario a MainActivityAlmacen
                            Intent intent = new Intent(LoginAlmacenActivity.this, MainActivityAlmacen.class)
                                    .putExtra("email", almacen.getEmail())
                                    .putExtra("contraseña", almacen.getContraseña())
                                    .putExtra("nombre", almacen.getNombre());
                            startActivity(intent);
                            finish(); // Cierra la actividad de inicio de sesión del almacén
                            return;
                        } else {
                            // Contraseña incorrecta
                            Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // No se encontró ninguna cuenta con este correo electrónico
                    Toast.makeText(getApplicationContext(), "No se encontró ninguna cuenta con este correo electrónico", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error en la lectura de la base de datos
                Toast.makeText(getApplicationContext(), "Error al iniciar sesión: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirRegistroAlmacen() {
        // Abre la actividad de registro de un nuevo almacén
        startActivity(new Intent(LoginAlmacenActivity.this, RegistroAlmacenActivity.class));
    }

    private void abrirLoginUsuario() {
        startActivity(new Intent(getApplicationContext(), LoginUsuarioActivity.class));
    }
}
