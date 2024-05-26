package com.esteban.aplicacion_final.usuario;

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

import java.util.ArrayList;
import java.util.List;

public class VerProductosAdapter extends RecyclerView.Adapter<VerProductosAdapter.ProductoViewHolder> {

    private Context mContext;
    private List<Producto> mProductoList;
    private List<Producto> mFilteredProductoList; // Lista filtrada
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
        mFilteredProductoList = new ArrayList<>(productList); // Inicializar la lista filtrada con todos los productos
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_usuario_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = mFilteredProductoList.get(position); // Usar la lista filtrada
        holder.nombreTextView.setText(producto.getNombre());
        holder.nombreAlmacenTextView.setText("Nombre del almacén: " + producto.getNombreAlmacen());
        holder.descripcionTextView.setText(producto.getDescripcion());
        holder.precioTextView.setText("Precio: " + producto.getPrecio());
        holder.cantidadTextView.setText("Cantidad: " + producto.getCantidad());

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

    // Método para mostrar todos los productos sin filtrar
    public void showAllProducts() {
        mFilteredProductoList.clear();
        mFilteredProductoList.addAll(mProductoList);
        notifyDataSetChanged(); // Notificar al RecyclerView que los datos han cambiado
    }

    @Override
    public int getItemCount() {
        return mFilteredProductoList.size(); // Usar la lista filtrada para obtener el tamaño
    }

    // Método para filtrar la lista de productos según la letra ingresada
    public void filterList(String query) {
        mFilteredProductoList.clear(); // Limpiar la lista filtrada
        if (query.isEmpty()) {
            mFilteredProductoList.addAll(mProductoList); // Mostrar todos los productos si la consulta está vacía
        } else {
            String filterPattern = query.toLowerCase().trim();
            for (Producto producto : mProductoList) {
                if (producto.getNombre().toLowerCase().startsWith(filterPattern)) {
                    mFilteredProductoList.add(producto);
                }
            }
        }
        notifyDataSetChanged(); // Notificar al RecyclerView que los datos han cambiado
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
