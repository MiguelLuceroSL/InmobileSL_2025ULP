package com.miguel.inmobile.ui.contratos;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miguel.inmobile.R;
public class ContratosFragment extends Fragment {
    private ContratosViewModel vm; //viewModel

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflo el layout del fragment
        View root = inflater.inflate(R.layout.fragment_contratos, container, false);

        //obtengo el recycler y le doy un layout manager lineal
        RecyclerView rv = root.findViewById(R.id.rvContratos);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        //aca creo el viewmodel
        vm = new ViewModelProvider(this).get(ContratosViewModel.class);
        vm.getInmueblesMutable().observe(getViewLifecycleOwner(), inmuebles -> {
            //cuando cambia la lista creo el adapter y lo seteo
            ContratoAdapter adapter = new ContratoAdapter(inmuebles, getLayoutInflater(), getContext());
            rv.setAdapter(adapter);
        });

        //llamo al metodo que carga los inmuebles con contrato
        vm.cargarInmueblesConContrato();

        return root; //devuelvo la vista
    }
}