package com.miguel.inmobile.ui.gallery;
import android.app.Application;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavDirections;
import com.miguel.inmobile.modelo.Propietario;
import com.miguel.inmobile.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class GalleryViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> mutPropietario = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutEstado = new MutableLiveData<>();
    private MutableLiveData<String> mutTexto = new MutableLiveData<>();
    private final MutableLiveData<NavDirections> navCommand = new MutableLiveData<>();

    public GalleryViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<NavDirections> getNavCommand() {
        return navCommand;
    }

    public LiveData<String> getMutTexto() {
        return mutTexto;
    }

    public LiveData<Boolean> getMutEstado() {
        return mutEstado;
    }

    public LiveData<Propietario> getMutPropietario(){
        return mutPropietario;
    }

    public void guardar(String textoBoton, String nombre, String apellido, String dni, String email, String telefono){
        if (textoBoton.equalsIgnoreCase("Editar")){
            mutEstado.setValue(true);
            mutTexto.setValue("Guardar cambios");
        }else{

            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                Toast.makeText(getApplication(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.contains("@")) {
                Toast.makeText(getApplication(), "Email inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dni.length() < 7) {
                Toast.makeText(getApplication(), "DNI inválido", Toast.LENGTH_SHORT).show();
                return;
            }
            Propietario propietarioActualizado = new Propietario();
            propietarioActualizado.setIdPropietario(mutPropietario.getValue().getIdPropietario());
            propietarioActualizado.setNombre(nombre);
            propietarioActualizado.setApellido(apellido);
            propietarioActualizado.setDni(dni);
            propietarioActualizado.setEmail(email);
            propietarioActualizado.setTelefono(telefono);
            propietarioActualizado.setClave(null);



            String token = ApiClient.leerToken(getApplication());
            Call<Propietario> llamada = ApiClient.getInmobileService().actualizarPropietario("Bearer "+token, propietarioActualizado);
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        mutPropietario.postValue(response.body());

                    } else {
                        Toast.makeText(getApplication(), "No se pudo actualizar el propietario: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error de servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            mutEstado.setValue(false);
            mutTexto.setValue("Editar");
        }
    }

    public void leerPropietario(){
        String token = ApiClient.leerToken(getApplication());
        Call<Propietario> llamada = ApiClient.getInmobileService().obtenerPropietario("Bearer "+token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    mutPropietario.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "No se pudo obtener el propietario: "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de servidor: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void irACambiarClave() {
        navCommand.setValue(GalleryFragmentDirections.actionNavGalleryToCambiarClaveFragment());
    }
}