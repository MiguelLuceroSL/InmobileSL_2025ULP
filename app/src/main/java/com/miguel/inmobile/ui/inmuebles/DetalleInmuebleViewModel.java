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

    //devuelvo el livedata del inmueble
    public LiveData<Inmueble> getInmuebleMut() {
        return inmuebleMut;
    }

    //constructor
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    //obtengo el inmueble del bundle que viene del fragment anterior
    public void obtenerInmueble(Bundle inmuebleBundle){
        Inmueble inmueble = (Inmueble) inmuebleBundle.getSerializable("inmueble");

        if(inmueble != null){
            this.inmuebleMut.setValue(inmueble);
        }

    }

    //actualizo el estado disponible del inmueble
    public void actualizarInmueble(Boolean disponible){
        //creo nuevo objeto inmueble
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(disponible);

        //seteo id del inmueble actual
        inmueble.setIdInmueble(this.inmuebleMut.getValue().getIdInmueble());

        //obtengo el token
        String token = ApiClient.leerToken(getApplication());

        // llamo a la api para actualizar el inmueble
        Call<Inmueble> llamada = ApiClient.getInmobileService().actualizarInmueble("Bearer " + token, inmueble);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                Log.d("Boolean","Response: "+response);
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Inmueble actualizado correctamente", Toast.LENGTH_SHORT).show();
                    //inmueble.setValue(response.body());
                } else {
                    //si falla muestro error
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