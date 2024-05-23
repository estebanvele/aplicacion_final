package com.esteban.aplicacion_final.almacen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esteban.aplicacion_final.R;
import com.esteban.aplicacion_final.models.Producto;
import com.esteban.aplicacion_final.usuario.VerProductosAdapter;

import java.util.List;

public class VerMisProductosAdapter extends RecyclerView.Adapter<VerMisProductosAdapter.ProductoViewHolder>{
    private Context mContext;
    private List<Producto> mProductoList;
    private VerProductosAdapter.OnItemClickListener mListener;

    public VerMisProductosAdapter(Context context, List<Producto> productList) {
        mContext = context;
        mProductoList = productList;
    }

    @NonNull
    @Override
    public VerMisProductosAdapter.ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_almacen_producto, parent, false);
        return new VerMisProductosAdapter.ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerMisProductosAdapter.ProductoViewHolder holder, int position) {
        Producto producto = mProductoList.get(position);
        holder.nombreTextView.setText(producto.getNombre());
        holder.descripcionTextView.setText(producto.getDescripcion());
        holder.precioTextView.setText(String.valueOf(producto.getPrecio()));
        holder.cantidadTextView.setText(String.valueOf(producto.getCantidad()));
    }

    @Override
    public int getItemCount() {
        return mProductoList.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView;
        public TextView descripcionTextView;
        public TextView precioTextView;
        public TextView cantidadTextView;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombre_producto);
            descripcionTextView = itemView.findViewById(R.id.descripcion_producto);
            precioTextView = itemView.findViewById(R.id.precio_producto);
            cantidadTextView = itemView.findViewById(R.id.cantidad_producto);
        }
    }
}
