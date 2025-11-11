package com.miguel.inmobile.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.miguel.inmobile.R;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //inflo el layout del fragment
        View root = inflater.inflate(R.layout.fragment_inquilinos, container, false);

        //creo el viewmodel
        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);

        //le paso los datos del inquilino que vinieron por argumento
        vm.setInquilino(getArguments());

        //busco los textview del layout
        TextView tvNombre = root.findViewById(R.id.tvNombreInquilino);
        TextView tvApellido = root.findViewById(R.id.tvApellidoInquilino);
        TextView tvDni = root.findViewById(R.id.tvDniInquilino);
        TextView tvTelefono = root.findViewById(R.id.tvTelefonoInquilino);
        TextView tvEmail = root.findViewById(R.id.tvEmailInquilino);

        //observo el livedata para cuando llega el inquilino muestre los datos
        vm.getInquilino().observe(getViewLifecycleOwner(), inquilino -> {
            tvNombre.setText("Nombre: " + inquilino.getNombre());
            tvApellido.setText("Apellido: " + inquilino.getApellido());
            tvDni.setText("DNI: " + inquilino.getDni());
            tvTelefono.setText("Teléfono: " + inquilino.getTelefono());
            tvEmail.setText("Email: " + inquilino.getEmail());
        });

        return root; //retorno la vista
    }
}