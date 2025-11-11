package com.miguel.inmobile.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.miguel.inmobile.MainActivity;
import com.miguel.inmobile.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding; //viewbinding para acceder a las vistas del layout
    private LoginViewModel viewModel; //viewModel que maneja el login
    private LlamarViewModel llamarViewModel; //viewModel que detecta agitacion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instancias de los ViewModel
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        llamarViewModel = new ViewModelProvider(this).get(LlamarViewModel.class);

        //inflo el layout con viewbinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //accion del boton de login
        binding.btnLogin.setOnClickListener(view -> {
            Log.d("LoginP", "Usuario: " + binding.etUser.getText().toString() +
                    " Contraseña: " + binding.etPass.getText().toString());
            //llamo al metodo login del viewmodel con los datos ingresados
            viewModel.login(binding.etUser.getText().toString(), binding.etPass.getText().toString());
        });

        //observo si el login fue exitoso
        viewModel.getMutAbrirMain().observe(this, o -> {
            Log.d("LoginP", "Login correcto");
            //abro la actividad principal y cierra el login
            startActivity(new Intent(getApplication(), MainActivity.class));
            finish();
        });

        //si hubo error en el login
        viewModel.getMutError().observe(this, mensaje -> {
            binding.tvError.setText(mensaje.toString());
            //limpio los campos al mostrar el error
            binding.etPass.setText("");
            binding.etUser.setText("");
        });

        //observo la deteccion de agitacion
        llamarViewModel.getDeteccionAgitado().observe(this, agitado -> {
                Log.d("Agitado", "Se detectó agitación");
        });
    }
}