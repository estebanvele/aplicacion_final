package com.esteban.aplicacion_final.usuario;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esteban.aplicacion_final.R;
import com.esteban.aplicacion_final.models.Producto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivityUsuario extends AppCompatActivity implements ProductoAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ProductoAdapter productoAdapter;
    private List<Producto> productList;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_usuario);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productoAdapter = new ProductoAdapter(this, productList);
        recyclerView.setAdapter(productoAdapter);

        productoAdapter.setOnItemClickListener(this); // Establecer el listener de clic en el adaptador

        mDatabase = FirebaseDatabase.getInstance().getReference().child("productos");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    productList.add(producto);
                }
                productoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivityUsuario.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Método para manejar el clic en un producto
    @Override
    public void onItemClick(Producto producto) {
        // Aquí puedes implementar la lógica para realizar la compra
        Toast.makeText(this, "Producto comprado: " + producto.getNombre(), Toast.LENGTH_SHORT).show();
    }
}
