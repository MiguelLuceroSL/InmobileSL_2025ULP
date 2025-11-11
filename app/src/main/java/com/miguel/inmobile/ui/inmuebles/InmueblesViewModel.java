package com.miguel.inmobile.ui.inmuebles;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.miguel.inmobile.modelo.Inmueble;
import com.miguel.inmobile.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mText = new MutableLiveData<>();
    private final MutableLiveData<List<Inmueble>> mInmueble = new MutableLiveData<>();

    //constructor inicializa y carga los inmuebles
    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        leerInmuebles();
    }

    public LiveData<String> getmText() {
        return mText;
    } //devuelve la lista de inmuebles

    public LiveData<List<Inmueble>> getmInmueble() {
        return mInmueble;
    }

    public LiveData<String> getText() {
        return mText;
    }

    //metodo para pedir los inmuebles a la api
    public void leerInmuebles(){
        //recupero token
        String token = ApiClient.leerToken(getApplication());

        //llamada a la api
        ApiClient.InmobileService api = ApiClient.getInmobileService();
        Call<List<Inmueble>> llamada = api.obtenerInmuebles("Bearer "+token);

        //hago la llamada asincronica
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()){
                    //si sale bien actualizo la lista
                    mInmueble.postValue(response.body());
                } else {
                    //si no hay datos muestro mensaje
                    Toast.makeText(getApplication(), "No hay inmuebles disponibles: "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                //si falla el servidor muestro error
                Toast.makeText(getApplication(), "Error en servidor: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}