package com.miguel.inmobile.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.miguel.inmobile.R;
import com.miguel.inmobile.databinding.FragmentCargarInmuebleBinding;

public class CargarInmuebleFragment extends Fragment {

    private FragmentCargarInmuebleBinding binding;

    private CargarInmuebleViewModel mViewModel;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static CargarInmuebleFragment newInstance() {
        return new CargarInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //opciones de los spinners
        String[] usos = {"Residencial", "Comercial", "Industrial", "Oficina", "Educativo", "Sanitario", "Recreativo", "Rural", "Turístico"};
        String[] tipos = {"Casa", "Departamento", "Local", "Oficina", "Depósito", "Edificio", "Cabaña", "Terreno"};

        //adapters para los spinners
        ArrayAdapter<String> usoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, usos);
        usoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spUso.setAdapter(usoAdapter);

        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, tipos);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spTipo.setAdapter(tipoAdapter);

        //inflo el xml del fragment
        binding = FragmentCargarInmuebleBinding.inflate(getLayoutInflater());

        //creo el viewmodel
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CargarInmuebleViewModel.class);

        //configuro la galeria
        abrirGaleria();

        //cuando toco el boton abre la galeria
        binding.btnSubirImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        //cuando toco guardar manda los datos al viewmodel
        binding.btguardarinmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.cargarInmueble(binding.etdireccion.getText().toString(),
                        binding.etprecio.getText().toString(),
                        binding.spUso.getSelectedItem().toString(),
                        binding.spTipo.getSelectedItem().toString(),
                        binding.etambientes.getText().toString(),
                        binding.etsuperficie.getText().toString(),
                        binding.cbdisponible.isChecked());
            }
        });

        return binding.getRoot();
    }

    private void abrirGaleria() {
        //armo el intent para abrir galeria
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //registro el resultado de la galeria
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Log.d("imagen","RESULT: "+ result);
                //cuando cambia la imagen se muestra
                mViewModel.getmUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
                    @Override
                    public void onChanged(Uri uri) {
                        binding.imgInmueble.setImageURI(uri);
                    }
                });

                //le paso la foto al viewmodel
                mViewModel.recibirFoto(result);

            }
        });
    }

}