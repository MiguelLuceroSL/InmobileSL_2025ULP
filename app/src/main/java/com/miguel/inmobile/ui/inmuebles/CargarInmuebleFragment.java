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
        binding = FragmentCargarInmuebleBinding.inflate(getLayoutInflater());
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CargarInmuebleViewModel.class);
        abrirGaleria();
        binding.btnSubirImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        binding.btguardarinmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.cargarInmueble(binding.etdireccion.getText().toString(),
                        binding.etprecio.getText().toString(),
                        binding.ettipo.getText().toString(),
                        binding.etuso.getText().toString(),
                        binding.etambientes.getText().toString(),
                        binding.etsuperficie.getText().toString(),
                        binding.cbdisponible.isChecked());
            }
        });

        return binding.getRoot();
    }

    private void abrirGaleria() {

        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Log.d("imagen","RESULT: "+ result);
                mViewModel.getmUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
                    @Override
                    public void onChanged(Uri uri) {
                        binding.imgInmueble.setImageURI(uri);
                    }
                });

                mViewModel.recibirFoto(result);

            }
        });
    }

}