package com.esteban.aplicacion_final.almacen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.esteban.aplicacion_final.R;
import com.esteban.aplicacion_final.models.Almacen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistroAlmacenActivity extends AppCompatActivity {

    private EditText etNombreAlmacen, etEmail, etContraseña;
    private Button btnRegistrarAlmacen, btnIrALogin;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_almacen);

        // Inicialización de Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Vinculación de vistas
        etNombreAlmacen = findViewById(R.id.editTextNombreAlmacen);
        etEmail = findViewById(R.id.editTextEmail);
        etContraseña = findViewById(R.id.editTextContraseña);
        btnRegistrarAlmacen = findViewById(R.id.buttonRegistrarAlmacen);
        btnIrALogin = findViewById(R.id.buttonIrALogin);

        // Configuración del clic del botón de registro de almacén
        btnRegistrarAlmacen.setOnClickListener(view -> registrarAlmacen());

        // Configuración del clic del botón para ir a la actividad de login
        btnIrALogin.setOnClickListener(view -> abrirLoginAlmacen());
    }

    private void registrarAlmacen() {
        String nombreAlmacen = etNombreAlmacen.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contraseña = etContraseña.getText().toString().trim();

        if (TextUtils.isEmpty(nombreAlmacen) || TextUtils.isEmpty(email) || TextUtils.isEmpty(contraseña)) {
            Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear una instancia de Almacen con los datos proporcionados
        Almacen almacen = new Almacen(nombreAlmacen, email, contraseña);

        // Obtener una referencia a la ubicación en la base de datos para este almacén
        DatabaseReference refAlmacen = mDatabase.child("almacenes");

        refAlmacen.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // Generar una nueva clave única para este almacén
                    String almacenId = refAlmacen.push().getKey();

                    // Guardar el almacén utilizando la clave generada
                    refAlmacen.child(almacenId).setValue(almacen)
                            .addOnSuccessListener(aVoid -> {
                                // Éxito al guardar el almacén
                                Toast.makeText(getApplicationContext(), "Almacén registrado correctamente", Toast.LENGTH_SHORT).show();
                                // Aquí puedes realizar cualquier acción adicional después de guardar el almacén, como redirigir al usuario a otra actividad
                            })
                            .addOnFailureListener(e -> {
                                // Error al guardar el almacén
                                Toast.makeText(getApplicationContext(), "Error al registrar el almacén: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else if (dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Ya existe una cuenta con este email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Error al realizar la consulta
                Toast.makeText(getApplicationContext(), "Error al registrar el almacén: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirLoginAlmacen() {
        // Abre la actividad de login de almacén
        Intent intent = new Intent(RegistroAlmacenActivity.this, LoginAlmacenActivity.class);
        startActivity(intent);
    }
}
