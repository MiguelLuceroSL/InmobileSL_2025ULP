package com.miguel.inmobile.ui.login;

import android.app.Application;
import android.content.Intent;
import android.media.tv.interactive.AppLinkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miguel.inmobile.MainActivity;
import com.miguel.inmobile.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mAbrirMain = new MutableLiveData<>();



    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMutError() {
        return mError;
    }

    public LiveData<String> getMutAbrirMain() {
        return mAbrirMain;
    }

    public void login(String user, String clave) {
        ApiClient.InmobileService api = ApiClient.getInmobileService();
        Call<String> llamada = api.login(user, clave);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    mAbrirMain.postValue("Login hecho");
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                } else {
                    mError.setValue("Usuario o contraseña incorrectos");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mError.setValue("¡¡¡Error!!!");
                Log.e("ErrorP", t.getMessage());
            }
        });
        //if (user.equals("miguel") && clave.equals("123")) {
        //    mAbrirMain.setValue("Login hecho");
        //} else {
        //    mError.setValue("Usuario o contraseña incorrectos");
        //}
    }
}
