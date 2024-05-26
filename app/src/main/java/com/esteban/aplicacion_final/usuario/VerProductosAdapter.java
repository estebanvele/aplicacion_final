package com.esteban.aplicacion_final.usuario;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esteban.aplicacion_final.R;
import com.esteban.aplicacion_final.models.Producto;

import java.util.List;

public class VerProductosAdapter extends RecyclerView.Adapter<VerProductosAdapter.ProductoViewHolder> {

    private Context mContext;
    private List<Producto> mProductoList;
    private OnItemClickListener mListener;

    // Interfaz para manejar los clics en los elementos del RecyclerView
    public interface OnItemClickListener {
        void onItemClick(Producto producto);
    }

    // Método para configurar el listener del clic en el RecyclerView
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public VerProductosAdapter(Context context, List<Producto> productList) {
        mContext = context;
        mProductoList = productList;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_usuario_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = mProductoList.get(position);
        holder.nombreTextView.setText(producto.getNombre());
        holder.nombreAlmacenTextView.setText("Nombre del almacén: " + producto.getNombreAlmacen());
        holder.descripcionTextView.setText(producto.getDescripcion());
        holder.precioTextView.setText("Precio: " + String.valueOf(producto.getPrecio()));
        holder.cantidadTextView.setText("Cantidad: " + String.valueOf(producto.getCantidad()));

        // Agregar OnClickListener al botón "Comprar"
        holder.btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(producto);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductoList.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView;
        public TextView nombreAlmacenTextView;
        public TextView descripcionTextView;
        public TextView precioTextView;
        public TextView cantidadTextView;
        public Button btnComprar;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombre_producto);
            nombreAlmacenTextView = itemView.findViewById(R.id.nombre_almacen);
            descripcionTextView = itemView.findViewById(R.id.descripcion_producto);
            precioTextView = itemView.findViewById(R.id.precio_producto);
            cantidadTextView = itemView.findViewById(R.id.cantidad_producto);
            btnComprar = itemView.findViewById(R.id.btn_comprar);
        }


    }
}
