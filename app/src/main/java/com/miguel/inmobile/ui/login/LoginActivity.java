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
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    private LlamarViewModel llamarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        llamarViewModel = new ViewModelProvider(this).get(LlamarViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view -> {
            Log.d("LoginP", "Usuario: " + binding.etUser.getText().toString() +
                    " Contraseña: " + binding.etPass.getText().toString());
            viewModel.login(binding.etUser.getText().toString(), binding.etPass.getText().toString());
        });

        viewModel.getMutAbrirMain().observe(this, o -> {
            Log.d("LoginP", "Login correcto");
            startActivity(new Intent(getApplication(), MainActivity.class));
            finish();
        });

        viewModel.getMutError().observe(this, mensaje -> {
            binding.tvError.setText(mensaje.toString());
            binding.etPass.setText("");
            binding.etUser.setText("");
        });

        llamarViewModel.getDeteccionAgitado().observe(this, agitado -> {
                Log.d("Agitado", "Se detectó agitación");
        });
    }
}