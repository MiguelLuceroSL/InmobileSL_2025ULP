package com.miguel.inmobile.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.maps.SupportMapFragment;
import com.miguel.inmobile.R;
import com.miguel.inmobile.databinding.FragmentHomeBinding;
public class HomeFragment extends Fragment {
    private HomeViewModel vm; //el viewmodel
    private FragmentHomeBinding binding; //binding que conecta el layout con el fragment
    @Override public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        //inflo el layout
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot(); //devuelvo la vista del binding
    }
    @Override public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //una instancia del viewmodel
        vm = new ViewModelProvider(this).get(HomeViewModel.class);

        //busco el fragment del mapa que esta en el xml usando el id
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        //le paso el mapa al viewmodel para que lo configure
        mapFragment.getMapAsync(vm::setMapa);
    }
    @Override public void onDestroyView() {
        //cuando se destruye la vista limpio el binding para evitar fugas de memoria
        super.onDestroyView();
        binding = null;
    }
}