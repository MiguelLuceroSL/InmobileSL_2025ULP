package com.miguel.inmobile.ui.cambiarclave;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.miguel.inmobile.R;
public class CambiarClaveFragment extends Fragment {
    private CambiarClaveViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cambiar_clave, container, false);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CambiarClaveViewModel.class);
        EditText etActual = v.findViewById(R.id.etClaveActual);
        EditText etNueva = v.findViewById(R.id.etClaveNueva);
        EditText etRepetir = v.findViewById(R.id.etClaveRepetir);
        Button btnCambiar = v.findViewById(R.id.btnGuardarClave);

        btnCambiar.setOnClickListener(b ->
                vm.cambiarClave(
                        etActual.getText().toString(),
                        etNueva.getText().toString(),
                        etRepetir.getText().toString()
                )
        );

        vm.getMensaje().observe(getViewLifecycleOwner(),
                msg -> Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        );

        vm.getNavCommand().observe(getViewLifecycleOwner(),
                directions -> NavHostFragment.findNavController(this).navigate(directions)
        );
        return v;
    }
}