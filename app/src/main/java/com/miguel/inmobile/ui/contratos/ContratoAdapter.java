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
    private List<Inmueble> inmuebles; //aca guardo la lista de inmuebles que se muestran
    private LayoutInflater inflater; //y aca uso el inflater para inflar el layout de cada item
    private Context context; //contexto para glide y demas

    //constructor que recibe la lista, el inflater y el contexto
    public ContratoAdapter(List<Inmueble> inmuebles, LayoutInflater inflater, Context context) {
        this.inmuebles = inmuebles;
        this.inflater = inflater;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflo el layout de cada tarjeta del recycler
        View root = inflater.inflate(R.layout.contrato_card, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //obtengo el inmueble que toca mostrar en esta posicion
        String urlBase = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
        Inmueble i = inmuebles.get(position);

        //cargo los datos en los textview
        holder.tvDireccion.setText(i.getDireccion());
        holder.tvMonto.setText("Valor: $" + i.getValor());
        holder.tvFechaInicio.setText("Tipo: " + i.getTipo());
        holder.tvFechaFin.setText("Uso: " + i.getUso());
        //uso glide para mostrar la imagen del inmueble
        Glide.with(context)
                .load(urlBase + i.getImagen())
                .placeholder(R.drawable.inm)
                .error("null")
                .into(holder.imgInmuebleContrato);

        //click del item para ir al detalle del contrato
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", i);
            Navigation.findNavController(v).navigate(R.id.nav_detalleContrato, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size(); //devuelvo la cantidad de inmuebles
    }

    //viewholder que guarda las referencias de los elementos de la card
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvFechaInicio, tvFechaFin, tvMonto;
        private ImageView imgInmuebleContrato;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //aca vinculo los elementos del layout con las variables
            tvDireccion = itemView.findViewById(R.id.tvDireccionContrato);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
            imgInmuebleContrato = itemView.findViewById(R.id.imgInmuebleContrato);
            tvMonto = itemView.findViewById(R.id.tvMonto);
        }
    }
}