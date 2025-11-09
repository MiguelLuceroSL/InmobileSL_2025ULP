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

    public PagoAdapter(List<Pago> pagos, LayoutInflater inflater) {
        this.pagos = pagos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = inflater.inflate(R.layout.pago_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pago p = pagos.get(position);
        holder.tvFecha.setText("Fecha: " + p.getFechaPago());
        holder.tvMonto.setText("Monto: $" + p.getMonto());
        holder.tvDetalle.setText("Detalle: " + p.getDetalle());
        holder.tvEstado.setText("Estado: " + (p.isEstado() ? "Pagado" : "Pendiente"));
    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvMonto, tvDetalle, tvEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFechaPago);
            tvMonto = itemView.findViewById(R.id.tvMontoPago);
            tvDetalle = itemView.findViewById(R.id.tvDetallePago);
            tvEstado = itemView.findViewById(R.id.tvEstadoPago);
        }
    }
}