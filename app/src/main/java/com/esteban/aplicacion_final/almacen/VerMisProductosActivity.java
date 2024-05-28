package com.esteban.aplicacion_final.almacen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esteban.aplicacion_final.R;
import com.esteban.aplicacion_final.models.Producto;
import com.esteban.aplicacion_final.usuario.MainActivityUsuario;
import com.esteban.aplicacion_final.usuario.VerProductosAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VerMisProductosActivity extends AppCompatActivity{
    private String nombreAlmacen;
    private RecyclerView recyclerView;
    private VerMisProductosAdapter verMisProductosAdapter;
    private List<Producto> productList;
    private DatabaseReference mDatabase;
    private Button btnSalirMainAlmacen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mis_productos);

        recyclerView = findViewById(R.id.almacen_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        productList = new ArrayList<>();
        verMisProductosAdapter = new VerMisProductosAdapter(getApplicationContext(), productList);
        recyclerView.setAdapter(verMisProductosAdapter);

        nombreAlmacen = getIntent().getStringExtra("nombreAlmacen");

        Toast.makeText(getApplicationContext(), nombreAlmacen, Toast.LENGTH_LONG).show();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnSalirMainAlmacen = findViewById(R.id.btnSalirMainAlmacen);
        btnSalirMainAlmacen.setOnClickListener(view -> salirMainAlmacen());

        mDatabase.child("productos").orderByChild("nombreAlmacen").equalTo(nombreAlmacen).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    productList.add(producto);
                }
                verMisProductosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void salirMainAlmacen(){
        startActivity(new Intent(getApplicationContext(), MainActivityAlmacen.class).putExtra("nombre", nombreAlmacen));

    }
}