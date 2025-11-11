package com.miguel.inmobile.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.miguel.inmobile.modelo.Inmueble;
import com.miguel.inmobile.request.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class CargarInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> mUri = new MutableLiveData<>();

    public CargarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Uri> getmUri() {
        return mUri;
    }

    public void recibirFoto(ActivityResult result) {
        //si selecciono algo
        if (result.getResultCode() == RESULT_OK) {
            //agarro la uri
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salida", uri.toString());
            //guardo la uri
            mUri.setValue(uri);
        }
    }

    public void cargarInmueble(String direccion, String valor, String tipo, String uso, String ambientes, String superficie, Boolean estado){
        int superficies, ambientess;
        double precio;
        try{
            //convierto los datos a numero
            precio = Double.parseDouble(valor);
            superficies = Integer.parseInt(superficie);
            ambientess = Integer.parseInt(ambientes);

            //valido campos
            if(direccion.isEmpty() || valor.isEmpty() || tipo.isEmpty() || uso.isEmpty() || ambientes.isEmpty() || superficie.isEmpty()){
                Toast.makeText(getApplication(), "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mUri.getValue() == null) {
                Toast.makeText(getApplication(), "Debe seleccionar una imagen", Toast.LENGTH_SHORT).show();
                return;
            }

            //creo el objeto inmueble
            Inmueble inmueble = new Inmueble();
            inmueble.setDireccion(direccion);
            inmueble.setValor(precio);
            inmueble.setTipo(tipo);
            inmueble.setUso(uso);
            inmueble.setAmbientes(ambientess);
            inmueble.setSuperficie(superficies);
            inmueble.setDisponible(estado);

            //convierto la imagen a bytes
            byte[] imagen = convertirImg();

            //paso el inmueble a json
            String inmuebleJson = new Gson().toJson(inmueble);

            //armo los requestbody
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody imagenBody = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", imagenBody);

            //leo el token
            String token = ApiClient.leerToken(getApplication());

            //hago la llamada a la api
            ApiClient.InmobileService api = ApiClient.getInmobileService();

            //hago la llamada asincronica
            Call<Inmueble> llamada = api.cargarInmueble("Bearer "+token, imagenPart, inmuebleBody);
            llamada.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    Log.d("salida", response.toString());
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "Inmueble cargado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), "Error al cargar el inmueble: "+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error en servidor: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (NumberFormatException ex){
            Toast.makeText(getApplication(), "Debe ingresar numeros en los campos precio, superficie y ambientes", Toast.LENGTH_SHORT).show();
            return;
        }



    }

    private byte[] convertirImg(){
        try{
            //agarro la uri y la paso a bytes
            Uri uri = mUri.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return baos.toByteArray();
        }catch (FileNotFoundException ex){
            Toast.makeText(getApplication(), "Error al convertir la imagen", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }
    }

}