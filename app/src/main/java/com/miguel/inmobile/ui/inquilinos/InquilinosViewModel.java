package com.miguel.inmobile.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miguel.inmobile.modelo.Inquilino;

public class InquilinosViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> inquilinoMutable = new MutableLiveData<>();

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
    }

    //retorno el live data con el inquilino
    public LiveData<Inquilino> getInquilino() {
        return inquilinoMutable;
    }

    public void setInquilino(Bundle bundle) {
        //si el bundle no es nulo saco el inquilino que vino
        if (bundle != null) {
            Inquilino inquilino = (Inquilino) bundle.getSerializable("inquilino");
            //guardo el inquilino en el live data
            inquilinoMutable.setValue(inquilino);
        }
    }
}