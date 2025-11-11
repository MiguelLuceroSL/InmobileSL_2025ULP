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
//PROPIETARIO
public class GalleryViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> mutPropietario = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutEstado = new MutableLiveData<>(); //true = editable
    private MutableLiveData<String> mutTexto = new MutableLiveData<>(); //texto del boton
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

    //guarda cambios o habilita edicion segun el texto del boton
    public void guardar(String textoBoton, String nombre, String apellido, String dni, String email, String telefono){
        if (textoBoton.equalsIgnoreCase("Editar")){
            //si esta en modo editar habilito los campos
            mutEstado.setValue(true);
            mutTexto.setValue("Guardar cambios");
        }else{

            //validaciones
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

            //creo el objeto con los nuevos datos
            Propietario propietarioActualizado = new Propietario();
            propietarioActualizado.setIdPropietario(mutPropietario.getValue().getIdPropietario());
            propietarioActualizado.setNombre(nombre);
            propietarioActualizado.setApellido(apellido);
            propietarioActualizado.setDni(dni);
            propietarioActualizado.setEmail(email);
            propietarioActualizado.setTelefono(telefono);
            propietarioActualizado.setClave(null); // la clave no se toca aca

            // llamo al endpoint para actualizar los datos
            String token = ApiClient.leerToken(getApplication());
            Call<Propietario> llamada = ApiClient.getInmobileService().actualizarPropietario("Bearer "+token, propietarioActualizado);
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        // si esta bien actualizo el LiveData
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
            //vuelvo a modo lectura
            mutEstado.setValue(false);
            mutTexto.setValue("Editar");
        }
    }

    //trae los datos del propietario logueado
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

    //navega al fragment de cambiar clave
    public void irACambiarClave() {
        navCommand.setValue(GalleryFragmentDirections.actionNavGalleryToCambiarClaveFragment());
    }
}