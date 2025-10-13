package com.miguel.inmobile.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.miguel.inmobile.R;

public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<Runnable> action = new MutableLiveData<>();

    public LiveData<Runnable> getAction() {
        return action;
    }

    public void onMenuItemSelected(int itemId) {
        // Siempre que se seleccione algo, emitís la acción de cerrar el drawer
        action.setValue(() -> {});
    }
}
