package com.miguel.inmobile.ui.gallery;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.miguel.inmobile.databinding.FragmentGalleryBinding;
import com.miguel.inmobile.modelo.Propietario;
public class GalleryFragment extends Fragment {
    private GalleryViewModel vm;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(GalleryViewModel.class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);

        vm.getMutPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etDni.setText(propietario.getDni());
                binding.etEmail.setText(propietario.getEmail());
                binding.etTelefono.setText(propietario.getTelefono());
            }
        });

        vm.getMutEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombre.setEnabled(aBoolean);
            }
        });

        vm.getMutTexto().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditar.setText(s);
            }
        });

        vm.leerPropietario();
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String dni = binding.etDni.getText().toString();
                String email = binding.etEmail.getText().toString();
                String telefono = binding.etTelefono.getText().toString();

                vm.guardar(binding.btnEditar.getText().toString(), nombre, apellido, dni, email, telefono);
                binding.etNombre.setEnabled(vm.getMutEstado().getValue());
                binding.etApellido.setEnabled(vm.getMutEstado().getValue());
                binding.etDni.setEnabled(vm.getMutEstado().getValue());
                binding.etEmail.setEnabled(vm.getMutEstado().getValue());
                binding.etTelefono.setEnabled(vm.getMutEstado().getValue());
            }
        });
        vm.getNavCommand().observe(getViewLifecycleOwner(),
                directions -> NavHostFragment.findNavController(this).navigate(directions)
        );
        binding.btnCambiarClave.setOnClickListener(v -> vm.irACambiarClave());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}