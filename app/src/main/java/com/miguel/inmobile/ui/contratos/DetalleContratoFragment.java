package com.miguel.inmobile.ui.contratos;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.miguel.inmobile.R;
import com.miguel.inmobile.modelo.Inmueble;
public class DetalleContratoFragment extends Fragment {
    private DetalleContratoViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detalle_contrato, container, false);

        TextView tvDireccion = root.findViewById(R.id.tvDireccion);
        TextView tvFechas = root.findViewById(R.id.tvFechas);
        TextView tvMonto = root.findViewById(R.id.tvMontoDetalle);
        Button btnVerPagos = root.findViewById(R.id.btnVerPagos);
        Button btnVerInquilino = root.findViewById(R.id.btnVerInquilino);


        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        vm.getContratoMutable().observe(getViewLifecycleOwner(), c -> {
            tvDireccion.setText(c.getInmueble().getDireccion());
            tvFechas.setText("Desde: " + c.getFechaInicio() + " hasta: " + c.getFechaFinalizacion());
            tvMonto.setText("Monto: $" + c.getMontoAlquiler());

            btnVerPagos.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("contrato", c);
                Log.d("contrato", "Contrato enviado: " + c.toString());
                Log.d("contrato", "Bundle enviado: " + bundle.toString());
                Log.d("contrato", "Inmueble enviado: " + c.getInmueble().toString());
                Navigation.findNavController(v).navigate(R.id.nav_pagos, bundle);
            });

            btnVerInquilino.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inquilino", c.getInquilino());
                Navigation.findNavController(v).navigate(R.id.nav_inquilino, bundle);
            });
        });

        Inmueble inmueble = (Inmueble) getArguments().getSerializable("inmueble");
        vm.cargarContratoPorInmueble(inmueble);

        return root;
    }
}