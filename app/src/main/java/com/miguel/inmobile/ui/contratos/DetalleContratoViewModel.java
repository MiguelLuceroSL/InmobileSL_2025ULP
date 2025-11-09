package com.miguel.inmobile.ui.contratos;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.miguel.inmobile.modelo.Contrato;
import com.miguel.inmobile.modelo.Inmueble;
import com.miguel.inmobile.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> contratoMutable;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Contrato> getContratoMutable() {
        if (contratoMutable == null)
            contratoMutable = new MutableLiveData<>();
        return contratoMutable;
    }

    public void cargarContratoPorInmueble(Inmueble inmueble) {
        String token = ApiClient.leerToken(getApplication());
        Call<Contrato> call = ApiClient.getInmobileService().obtenerContratoPorInmueble("Bearer " + token, inmueble.getIdInmueble());
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()) {
                    contratoMutable.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}