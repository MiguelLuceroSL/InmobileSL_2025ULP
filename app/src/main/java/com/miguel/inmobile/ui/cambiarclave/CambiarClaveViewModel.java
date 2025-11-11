package com.miguel.inmobile.ui.cambiarclave;
import android.app.Application;
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
    //livedata para mostrar mensajes al usuario

    private final MutableLiveData<String> mensaje = new MutableLiveData<>();

    //livedata para mandar ordenes de navegacion
    private final MutableLiveData<NavDirections> navCommand = new MutableLiveData<>();

    //getters
    public LiveData<String> getMensaje() {
        return mensaje;
    }
    public LiveData<NavDirections> getNavCommand() {
        return navCommand;
    }

    //constructor
    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
    }

    //logica para cambiar la clave
    public void cambiarClave(String actual, String nueva, String repetir) {
        try {
            //aca leo el token guardado del usuario
            String token = ApiClient.leerToken(getApplication());

            //obtengo el servicio de la api
            ApiClient.InmobileService api = ApiClient.getInmobileService();

            //valido que las nuevas contraseñas coincidan
            if (!nueva.equals(repetir)) {
                mensaje.postValue("Las contraseñas no coinciden");
                return;
            }

            //hago la llamada a la api para cambiar la clave
            Call<Void> llamada = api.cambiarClave("Bearer " + token, actual, nueva);

            //manejo la respuesta del servidor
            llamada.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        //si la respuesta fue ok aviso con un toast y mando a navegar
                        mensaje.postValue("Clave cambiada");
                        Toast.makeText(getApplication(), "Clave cambiada", Toast.LENGTH_SHORT).show();
                        navCommand.postValue(
                                CambiarClaveFragmentDirections.actionCambiarClaveFragmentToNavGallery()
                        );
                    } else {
                        //si no fue exitosa aviso el error
                        mensaje.postValue("No se pudo cambiar la clave: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    //si hubo error de conexion lo muestro
                    mensaje.postValue("Error en servidor: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            //aca atrapo cualquier otro error
            mensaje.postValue("Error: " + e.getMessage());
        }
    }
}