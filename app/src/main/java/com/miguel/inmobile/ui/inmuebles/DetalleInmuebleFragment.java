package com.miguel.inmobile.ui.inmuebles;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.miguel.inmobile.R;
import com.miguel.inmobile.request.ApiClient;
import com.miguel.inmobile.databinding.FragmentDetalleInmuebleBinding;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mViewModel;
    private FragmentDetalleInmuebleBinding binding;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //inflo el layout y creo el binding
        binding = FragmentDetalleInmuebleBinding.bind(getLayoutInflater().inflate(R.layout.fragment_detalle_inmueble, container, false));

        //creo el viewmodel
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        //observo el inmueble para mostrar los datos
        mViewModel.getInmuebleMut().observe(getViewLifecycleOwner(), inmueble -> {
            binding.tvIdInmueble.setText(inmueble.getIdInmueble() + "");
            binding.tvDireccionI.setText(inmueble.getDireccion());
            binding.tvUsoI.setText(inmueble.getUso());
            binding.tvAmbientesI.setText(inmueble.getAmbientes() + "");
            binding.tvLatitudI.setText(inmueble.getLatitud() + "");
            binding.tvLongitudI.setText(inmueble.getLongitud() + "");
            binding.tvValorI.setText(inmueble.getValor() + "");

            //cargo la imagen con glide
            Glide.with(this)
                    .load(ApiClient.URLBASE + inmueble.getImagen())
                    .placeholder(R.drawable.inm)
                    .error("null")
                    .into(binding.imgInmueble);
            Log.d("Boolean","Estado en el que llega primero: "+inmueble.isDisponible());
            binding.checkDisponible.setChecked(inmueble.isDisponible());
        });

        //obtengo el inmueble del bundle
        mViewModel.obtenerInmueble(getArguments());

        //cuando se toca el check se actualiza el estado
        binding.checkDisponible.setOnClickListener(v -> {
            mViewModel.actualizarInmueble(binding.checkDisponible.isChecked());
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}