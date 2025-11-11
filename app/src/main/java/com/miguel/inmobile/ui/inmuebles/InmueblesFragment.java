package com.miguel.inmobile.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguel.inmobile.R;
import com.miguel.inmobile.databinding.FragmentInmueblesBinding;
import com.miguel.inmobile.modelo.Inmueble;

import java.util.List;

public class InmueblesFragment extends Fragment {
    private FragmentInmueblesBinding binding;
    private InmueblesViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //creo el viewmodel
        vm = new ViewModelProvider(this).get(InmueblesViewModel.class);

        //inflo el layout con binding
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);

        //observo la lista de inmuebles
        vm.getmInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                //cuando cambia la lista actualizo el adapter
                InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
                RecyclerView rv = binding.rvListaInmueble;

                rv.setAdapter(adapter);
                rv.setLayoutManager(glm);
            }
        });

        //boton flotante para agregar inmueble
        binding.fabAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.cargarInmuebleFragment);
            }
        });

        //llamo al metodo que carga los inmuebles
        vm.leerInmuebles();

        return binding.getRoot(); //limpio el binding al destruir la vista
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}