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
    private HomeViewModel vm;
    private FragmentHomeBinding binding;
    @Override public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(this).get(HomeViewModel.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(vm::setMapa);
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}