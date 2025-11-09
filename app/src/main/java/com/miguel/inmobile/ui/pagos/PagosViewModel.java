package com.miguel.inmobile.ui.pagos;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miguel.inmobile.modelo.Contrato;
import com.miguel.inmobile.modelo.Pago;
import com.miguel.inmobile.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> pagosMutable;

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pago>> getPagosMutable() {
        if (pagosMutable == null)
            pagosMutable = new MutableLiveData<>();
        return pagosMutable;
    }

    public void cargarPagos(Contrato contrato) {
        Log.d("prueba","cargarPagos pago vm");
        String token = ApiClient.leerToken(getApplication());
        Call<List<Pago>> call = ApiClient.getInmobileService().obtenerPagosPorContrato("Bearer " + token, contrato.getIdContrato());
        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    Log.d("prueba","Pago enviado (pago vm): "+response.body().toString());
                    pagosMutable.postValue(response.body());
                } else {
                    Log.d("prueba","Error al traer el pago (pago vm): "+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("prueba","Error en el servidor (pago vm): "+t.getMessage());
                t.printStackTrace();
            }
        });
    }
}