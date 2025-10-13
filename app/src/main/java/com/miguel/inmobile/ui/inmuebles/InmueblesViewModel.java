package com.miguel.inmobile.ui.inmuebles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InmueblesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public InmueblesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el inmuebles fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}