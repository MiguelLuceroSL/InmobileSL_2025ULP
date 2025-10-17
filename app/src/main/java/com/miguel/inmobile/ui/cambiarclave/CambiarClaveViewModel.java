package com.miguel.inmobile.ui.cambiarclave;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavDirections;

import com.miguel.inmobile.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mensaje = new MutableLiveData<>();
    private final MutableLiveData<NavDirections> navCommand = new MutableLiveData<>();

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public LiveData<NavDirections> getNavCommand() {
        return navCommand;
    }

    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
    }

    public void cambiarClave(String actual, String nueva, String repetir) {
        try {
            String token = ApiClient.leerToken(getApplication());
            ApiClient.InmobileService api = ApiClient.getInmobileService();
            if (!nueva.equals(repetir)) {
                mensaje.postValue("Las contraseñas no coinciden");
                return;
            }
            Call<Void> llamada = api.cambiarClave("Bearer " + token, actual, nueva);
            llamada.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        mensaje.postValue("Clave cambiada");
                        Toast.makeText(getApplication(), "Clave cambiada", Toast.LENGTH_SHORT).show();
                        navCommand.postValue(
                                CambiarClaveFragmentDirections.actionCambiarClaveFragmentToNavGallery()
                        );
                    } else {
                        mensaje.postValue("No se pudo cambiar la clave: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    mensaje.postValue("Error en servidor: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            mensaje.postValue("Error: " + e.getMessage());
        }
    }
}