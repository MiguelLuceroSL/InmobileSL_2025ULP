package com.miguel.inmobile.ui.inquilinos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InquilinosViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public InquilinosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el inquilinos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}