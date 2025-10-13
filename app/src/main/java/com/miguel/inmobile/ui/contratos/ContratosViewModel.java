package com.miguel.inmobile.ui.contratos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContratosViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ContratosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el contratos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}