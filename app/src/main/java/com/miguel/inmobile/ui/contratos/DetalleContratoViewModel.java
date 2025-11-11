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
    private MutableLiveData<Contrato> contratoMutable; //contrato que se muestra en detalle

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    //getter si no existe lo crea
    public MutableLiveData<Contrato> getContratoMutable() {
        if (contratoMutable == null)
            contratoMutable = new MutableLiveData<>();
        return contratoMutable;
    }

    //cargo el contrato segun el inmueble recibido
    public void cargarContratoPorInmueble(Inmueble inmueble) {
        //leo el token del usuario
        String token = ApiClient.leerToken(getApplication());

        //llamada a la api para obtener el contrato por inmueble
        Call<Contrato> call = ApiClient.getInmobileService().obtenerContratoPorInmueble("Bearer " + token, inmueble.getIdInmueble());

        //ejecuto la llamada asincrónica
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()) {
                    //si esta bien actualizo el livedata con el contrato
                    contratoMutable.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                //si falla muestro el error en consola
                t.printStackTrace();
            }
        });
    }
}