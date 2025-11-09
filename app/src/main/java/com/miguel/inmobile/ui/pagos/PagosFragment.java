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
        View root = inflater.inflate(R.layout.fragment_pagos, container, false);
        RecyclerView rv = root.findViewById(R.id.rvPagos);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        vm = new ViewModelProvider(this).get(PagosViewModel.class);
        vm.getPagosMutable().observe(getViewLifecycleOwner(), pagos -> {
            PagoAdapter adapter = new PagoAdapter(pagos, getLayoutInflater());
            rv.setAdapter(adapter);
        });

        Contrato contrato = (Contrato) getArguments().getSerializable("contrato");
        Log.d("prueba", "Contrato enviado: "+contrato.toString());
        vm.cargarPagos(contrato);

        return root;
    }
}