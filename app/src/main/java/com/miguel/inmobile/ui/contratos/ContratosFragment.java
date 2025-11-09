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
    private ContratosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contratos, container, false);

        RecyclerView rv = root.findViewById(R.id.rvContratos);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        vm = new ViewModelProvider(this).get(ContratosViewModel.class);
        vm.getInmueblesMutable().observe(getViewLifecycleOwner(), inmuebles -> {
            ContratoAdapter adapter = new ContratoAdapter(inmuebles, getLayoutInflater(), getContext());
            rv.setAdapter(adapter);
        });
        vm.cargarInmueblesConContrato();
        return root;
    }
}