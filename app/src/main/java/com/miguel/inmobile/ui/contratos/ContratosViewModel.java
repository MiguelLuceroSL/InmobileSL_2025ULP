package com.miguel.inmobile.ui.contratos;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.miguel.inmobile.modelo.Inmueble;
import com.miguel.inmobile.request.ApiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ContratosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> inmueblesMutable;

    public ContratosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getInmueblesMutable() {
        if (inmueblesMutable == null)
            inmueblesMutable = new MutableLiveData<>();
        return inmueblesMutable;
    }

    public void cargarInmueblesConContrato() {
        String token = ApiClient.leerToken(getApplication());
        Call<List<Inmueble>> call = ApiClient.getInmobileService().obtenerInmueblesConContratoVigente("Bearer " + token);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    Log.d("inmuebles", response.body().toString());
                    inmueblesMutable.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}