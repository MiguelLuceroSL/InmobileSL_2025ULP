package com.miguel.inmobile.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<LatLng> ubicacionInmobiliaria = new MutableLiveData<>();
    private final MutableLiveData<String> tituloMarker = new MutableLiveData<>();
    private final MutableLiveData<Float> zoom = new MutableLiveData<>();

    public HomeViewModel() {
        ubicacionInmobiliaria.setValue(new LatLng(-33.2940, -66.3344));
        tituloMarker.setValue("Inmobiliaria LP");
        zoom.setValue(18f);
    }

    public LiveData<LatLng> getUbicacionInmobiliaria() {
        return ubicacionInmobiliaria;
    }

    public LiveData<String> getTituloMarker() {
        return tituloMarker;
    }

    public LiveData<Float> getZoom() {
        return zoom;
    }
}