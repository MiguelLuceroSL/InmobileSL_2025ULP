package com.miguel.inmobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miguel.inmobile.R;
import com.miguel.inmobile.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel vm;
    private FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(HomeViewModel.class);


        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getViewLifecycleOwnerLiveData().observe(getViewLifecycleOwner(), lifecycleOwner -> {
                if (lifecycleOwner != null) {
                    mapFragment.getMapAsync(googleMap -> {
                        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        vm.getUbicacionInmobiliaria().observe(lifecycleOwner, ubicacion -> {
                            vm.getTituloMarker().observe(lifecycleOwner, titulo -> {
                                vm.getZoom().observe(lifecycleOwner, zoom -> {
                                    googleMap.addMarker(new MarkerOptions().position(ubicacion).title(titulo));
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, zoom));
                                });
                            });
                        });
                    });
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}