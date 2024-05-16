package com.esteban.aplicacion_final.producto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.esteban.aplicacion_final.R;
import com.esteban.aplicacion_final.models.Producto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrearProducto extends AppCompatActivity {

    private String nombreAlmacen;
    private EditText nombreEt, descripcionEt, precioEt, cantidadEt, nombreAlmacenEt;
    private Button crear_productoBtn;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Declaramos los elementos del layout
        nombreEt = findViewById(R.id.nombre);
        descripcionEt = findViewById(R.id.descripcion);
        precioEt = findViewById(R.id.precio);
        cantidadEt = findViewById(R.id.cantidad);
        crear_productoBtn = findViewById(R.id.crear_producto);
        nombreAlmacenEt = findViewById(R.id.nombreAlmacen);
        nombreAlmacen = getIntent().getStringExtra("nombre");
        nombreAlmacenEt.setText(nombreAlmacen);

        crear_productoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreEt.getText().toString();
                String descripcion = descripcionEt.getText().toString();
                Integer precio = Integer.parseInt(precioEt.getText().toString());
                Integer cantidad = Integer.parseInt(cantidadEt.getText().toString());
                String nombreAlmacen = nombreAlmacenEt.getText().toString();

                if (nombre.trim().isEmpty() || descripcion.trim().isEmpty()) {
                    if (precio == null || cantidad == null) {
                        Toast.makeText(getApplicationContext(), "Llena todos los campos antes de crear producto!", Toast.LENGTH_LONG).show();
                    }
                }

                Producto producto = new Producto(nombre, descripcion, precio, cantidad, nombreAlmacen);

                DatabaseReference refProducto = mDatabase.child("productos");

                refProducto.orderByChild("nombre").equalTo(nombre).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            // Generar una nueva clave única para este almacén
                            String productoId = refProducto.push().getKey();

                            // Guardar el almacén utilizando la clave generada
                            refProducto.child(productoId).setValue(producto)
                                    .addOnSuccessListener(aVoid -> {
                                        // Éxito al guardar el almacén
                                        Toast.makeText(getApplicationContext(), "Producto creado correctamente", Toast.LENGTH_SHORT).show();
                                        // Aquí puedes realizar cualquier acción adicional después de guardar el almacén, como redirigir al usuario a otra actividad
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error al guardar el almacén
                                        Toast.makeText(getApplicationContext(), "Error al crear el producto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else if (dataSnapshot.exists()){
                            Toast.makeText(getApplicationContext(), "Ya existe un producto con este nombre", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}