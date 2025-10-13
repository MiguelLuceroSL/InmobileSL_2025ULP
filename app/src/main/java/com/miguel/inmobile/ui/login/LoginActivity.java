package com.miguel.inmobile.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.miguel.inmobile.MainActivity;
import com.miguel.inmobile.R;
import com.miguel.inmobile.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginP","Usuario: "+binding.etUser.getText().toString()+" Contraseña: "+binding.etPass.getText().toString());
                viewModel.login(binding.etUser.getText().toString(), binding.etPass.getText().toString());
            }
        });

        viewModel.getMutAbrirMain().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Log.d("LoginP", "Login correcto");
                startActivity(new Intent(getApplication(), MainActivity.class));
                finish();
            }
        });

        viewModel.getMutError().observe(this, new Observer() {
            @Override
            public void onChanged(Object mensaje) {
                binding.tvError.setText(mensaje.toString());
                binding.etPass.setText("");
                binding.etUser.setText("");
            }
        });



    }
}