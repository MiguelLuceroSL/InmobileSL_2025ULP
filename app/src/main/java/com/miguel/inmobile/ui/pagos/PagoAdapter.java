package com.miguel.inmobile.ui.pagos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel.inmobile.R;
import com.miguel.inmobile.modelo.Pago;

import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolder> {
    private List<Pago> pagos;
    private LayoutInflater inflater;

    //constructor que recibe la lista de pagos y el inflater
    public PagoAdapter(List<Pago> pagos, LayoutInflater inflater) {
        this.pagos = pagos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflo el layout de cada item de pago
        View root = inflater.inflate(R.layout.pago_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //obtengo el pago actual
        Pago p = pagos.get(position);

        //muestra los datos del pago en los textviews
        holder.tvFecha.setText("Fecha: " + p.getFechaPago());
        holder.tvMonto.setText("Monto: $" + p.getMonto());
        holder.tvDetalle.setText("Detalle: " + p.getDetalle());
        holder.tvEstado.setText("Estado: " + (p.isEstado() ? "Pagado" : "Pendiente"));
    }

    @Override
    public int getItemCount() {
        return pagos.size(); //cantidad de pagos
    }

    //referencias de los textviews
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvMonto, tvDetalle, tvEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //conecto las variables con los elementos del layout
            tvFecha = itemView.findViewById(R.id.tvFechaPago);
            tvMonto = itemView.findViewById(R.id.tvMontoPago);
            tvDetalle = itemView.findViewById(R.id.tvDetallePago);
            tvEstado = itemView.findViewById(R.id.tvEstadoPago);
        }
    }
}