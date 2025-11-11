package com.miguel.inmobile.ui.slideshow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.miguel.inmobile.R;
import com.miguel.inmobile.databinding.FragmentSlideshowBinding;
import com.miguel.inmobile.ui.login.LoginActivity;
//SALIR
public class SlideshowFragment extends Fragment {
    private FragmentSlideshowBinding binding;
    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //inflo el layout del fragment
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (binding == null) return; //por si el fragmento ya se destruyo, si es null no hace nada

        //muestro un dialogo para confirmar si quiere salir
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmación de salida")
                .setMessage("¿Desea cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> logout()) //si elige si llama a logout
                .setNegativeButton("Cancelar", (dialog, which) -> { //si cancela vuelve al home
                    dialog.dismiss();
                    Navigation.findNavController(root).navigate(R.id.nav_home);

                    Navigation.findNavController(root).popBackStack(R.id.nav_slideshow, true);
                })
                .show();
    }

    private void logout() {
        //borro el token guardado en las preferencias
        SharedPreferences prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().remove("token").apply();

        //creo el intent para ir al login
        Intent intent = new Intent(getActivity(), LoginActivity.class);

        //limpio la pila de actividades
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent); //inicio la activity de login
        requireActivity().finish(); //finalizo la activity actual
    }

    @Override
    public void onDestroyView() {
        Log.d("ErrorP","onDestroy!!!");
        super.onDestroyView();
        binding = null;
    }
}