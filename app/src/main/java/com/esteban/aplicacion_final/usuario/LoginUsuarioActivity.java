package com.esteban.aplicacion_final.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.esteban.aplicacion_final.almacen.LoginAlmacenActivity;
import com.esteban.aplicacion_final.R;
import com.esteban.aplicacion_final.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUsuarioActivity extends AppCompatActivity {

    private EditText etEmail, etContraseña;
    private Button btnIniciarSesion, btnRegistrarse, btnLoginAlmacen; // Añade el botón para login en el almacén
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        // Inicializa Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inicializa la referencia de la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Vincula las vistas
        etEmail = findViewById(R.id.editTextEmail);
        etContraseña = findViewById(R.id.editTextContraseña);
        btnIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        btnRegistrarse = findViewById(R.id.buttonRegistro); // Añade el botón de registro
        btnLoginAlmacen = findViewById(R.id.buttonLoginAlmacen); // Añade el botón para login en el almacén

        // Configura el clic del botón de inicio de sesión
        btnIniciarSesion.setOnClickListener(view -> iniciarSesion());

        // Configura el clic del botón de registro
        btnRegistrarse.setOnClickListener(view -> abrirRegistro());

        // Configura el clic del botón para login en el almacén
        btnLoginAlmacen.setOnClickListener(view -> abrirLoginAlmacen());
    }

    private void iniciarSesion() {
        String email = etEmail.getText().toString().trim();
        String contraseña = etContraseña.getText().toString().trim();

        // Valida que los campos no estén vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(contraseña)) {
            Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Valida el formato del email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica las credenciales con la información almacenada en Firebase
        mDatabase.child("usuarios").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // El usuario con este correo electrónico existe en la base de datos
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        Usuario usuario = userSnapshot.getValue(Usuario.class);
                        if (usuario.getContraseña().equals(contraseña)) {
                            // Contraseña correcta, inicia sesión
                            // Redirige al usuario a la actividad principal
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginUsuarioActivity.this, MainActivityUsuario.class));
                            finish(); // Cierra la actividad de inicio de sesión para evitar que el usuario vuelva atrás
                            return;
                        } else {
                            // Contraseña incorrecta
                            Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // El usuario con este correo electrónico no está registrado
                    Toast.makeText(getApplicationContext(), "No se encontró ninguna cuenta con este correo electrónico", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error en la lectura de la base de datos
                Toast.makeText(getApplicationContext(), "Error al iniciar sesión: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirRegistro() {
        startActivity(new Intent(LoginUsuarioActivity.this, RegistroUsuarioActivity.class));
    }

    private void abrirLoginAlmacen() {
        startActivity(new Intent(LoginUsuarioActivity.this, LoginAlmacenActivity.class));
    }
}
