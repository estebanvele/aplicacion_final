package com.esteban.aplicacion_final;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    private EditText etEmail, etContraseña;
    private Button btnRegistrar;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializa la referencia de la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Vincula las vistas
        etEmail = findViewById(R.id.editTextEmail);
        etContraseña = findViewById(R.id.editTextContraseña);
        btnRegistrar = findViewById(R.id.buttonRegistrar);

        // Configura el clic del botón de registro
        btnRegistrar.setOnClickListener(view -> registrarUsuario());
    }

    private void registrarUsuario() {
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

        // Guarda la información del usuario en Firebase Realtime Database
        Usuario usuario = new Usuario(email, contraseña);
        mDatabase.child("usuarios").child(email.replace(".", ",")).setValue(usuario)
                .addOnSuccessListener(aVoid -> {
                    // El usuario se registró exitosamente
                    Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    // Abre la actividad de inicio de sesión
                    startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                    finish(); // Cierra la actividad de registro
                })
                .addOnFailureListener(e -> {
                    // Hubo un error al registrar el usuario
                    Toast.makeText(getApplicationContext(), "Error al registrar usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
