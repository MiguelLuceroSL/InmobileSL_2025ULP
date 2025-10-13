package com.miguel.inmobile.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguel.inmobile.R;
import com.miguel.inmobile.databinding.FragmentContratosBinding;
import com.miguel.inmobile.databinding.FragmentHomeBinding;
import com.miguel.inmobile.ui.home.HomeViewModel;

public class ContratosFragment extends Fragment {

    private FragmentContratosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContratosViewModel contratosViewModel =
                new ViewModelProvider(this).get(ContratosViewModel.class);

        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textContratos;
        contratosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}