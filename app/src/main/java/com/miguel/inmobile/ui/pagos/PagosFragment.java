package com.miguel.inmobile.ui.pagos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel.inmobile.R;
import com.miguel.inmobile.modelo.Contrato;
import com.miguel.inmobile.modelo.Pago;

import java.util.List;

public class PagosFragment extends Fragment {
    private PagosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflo el layout del fragment
        View root = inflater.inflate(R.layout.fragment_pagos, container, false);

        //obtengo el recyclerview y le pongo un layoutmanager para que muestre los pagos
        RecyclerView rv = root.findViewById(R.id.rvPagos);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        //creo el viewmodel
        vm = new ViewModelProvider(this).get(PagosViewModel.class);

        //observo la lista de pagos y actualizo el recycler cuando cambia
        vm.getPagosMutable().observe(getViewLifecycleOwner(), pagos -> {
            PagoAdapter adapter = new PagoAdapter(pagos, getLayoutInflater());
            rv.setAdapter(adapter);
        });

        //recibo el contrato pasado por argumentos
        Contrato contrato = (Contrato) getArguments().getSerializable("contrato");
        Log.d("prueba", "Contrato enviado: "+contrato.toString());

        //llamo al metodo del viewmodel para cargar los pagos
        vm.cargarPagos(contrato);

        return root; //devuelvo la vista
    }
}