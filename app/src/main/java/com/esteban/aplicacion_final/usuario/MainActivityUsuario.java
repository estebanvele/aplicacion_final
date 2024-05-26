package com.esteban.aplicacion_final.usuario;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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

public class MainActivityUsuario extends AppCompatActivity implements VerProductosAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private VerProductosAdapter verProductosAdapter;
    private List<Producto> productList;
    private DatabaseReference mDatabase;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_usuario);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        verProductosAdapter = new VerProductosAdapter(this, productList);
        recyclerView.setAdapter(verProductosAdapter);

        verProductosAdapter.setOnItemClickListener(this); // Establecer el listener de clic en el adaptador

        mDatabase = FirebaseDatabase.getInstance().getReference().child("productos");

        // Referencia al EditText de búsqueda
        editTextSearch = findViewById(R.id.edit_text_search);

        // Escucha los cambios en el texto de búsqueda
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterProducts(s.toString());
            }
        });

        // Cargar todos los productos
        loadProducts();
    }

    // Método para cargar todos los productos desde Firebase
    private void loadProducts() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    productList.add(producto);
                }
                verProductosAdapter.showAllProducts(); // Mostrar todos los productos cargados
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivityUsuario.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para filtrar los productos por la letra ingresada
    private void filterProducts(String query) {
        verProductosAdapter.filterList(query);
    }

    // Método para manejar el clic en un producto
    @Override
    public void onItemClick(Producto producto) {
        Toast.makeText(this, "Producto comprado: " + producto.getNombre(), Toast.LENGTH_SHORT).show();
    }
}
