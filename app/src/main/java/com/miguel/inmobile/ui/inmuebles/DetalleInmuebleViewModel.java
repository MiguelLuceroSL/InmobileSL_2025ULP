package com.miguel.inmobile.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.miguel.inmobile.modelo.Inmueble;
import com.miguel.inmobile.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleMut = new MutableLiveData<>();

    public LiveData<Inmueble> getInmuebleMut() {
        return inmuebleMut;
    }


    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public void obtenerInmueble(Bundle inmuebleBundle){
        Inmueble inmueble = (Inmueble) inmuebleBundle.getSerializable("inmueble");

        if(inmueble != null){
            this.inmuebleMut.setValue(inmueble);
        }

    }

    public void actualizarInmueble(Boolean disponible){
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(disponible);
        Log.d("Boolean","Boolean que llega: "+disponible);
        Log.d("Boolean","Boolean que se actualiza: "+inmueble.isDisponible());
        inmueble.setIdInmueble(this.inmuebleMut.getValue().getIdInmueble());
        String token = ApiClient.leerToken(getApplication());
        Log.d("Boolean","Token: "+token);
        Log.d("Boolean","Inmueble que llega: "+inmueble);
        Call<Inmueble> llamada = ApiClient.getInmobileService().actualizarInmueble("Bearer " + token, inmueble);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                Log.d("Boolean","Response: "+response);
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Inmueble actualizado correctamente", Toast.LENGTH_SHORT).show();
                    //inmueble.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Error al actualizar el inmueble: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(getApplication(), "Error al contactar con el servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}