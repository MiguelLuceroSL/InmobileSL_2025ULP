package com.miguel.inmobile.ui.contratos;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.miguel.inmobile.R;
import com.miguel.inmobile.modelo.Inmueble;
import java.util.List;
public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder> {
    private List<Inmueble> inmuebles;
    private LayoutInflater inflater;
    private Context context;

    public ContratoAdapter(List<Inmueble> inmuebles, LayoutInflater inflater, Context context) {
        this.inmuebles = inmuebles;
        this.inflater = inflater;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = inflater.inflate(R.layout.contrato_card, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String urlBase = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
        Inmueble i = inmuebles.get(position);
        holder.tvDireccion.setText(i.getDireccion());
        holder.tvMonto.setText("Valor: $" + i.getValor());
        holder.tvFechaInicio.setText("Tipo: " + i.getTipo());
        holder.tvFechaFin.setText("Uso: " + i.getUso());
        Glide.with(context)
                .load(urlBase + i.getImagen())
                .placeholder(R.drawable.inm)
                .error("null")
                .into(holder.imgInmuebleContrato);

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", i);
            Navigation.findNavController(v).navigate(R.id.nav_detalleContrato, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvFechaInicio, tvFechaFin, tvMonto;
        private ImageView imgInmuebleContrato;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccionContrato);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
            imgInmuebleContrato = itemView.findViewById(R.id.imgInmuebleContrato);
            tvMonto = itemView.findViewById(R.id.tvMonto);
        }
    }
}