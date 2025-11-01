package com.miguel.inmobile.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<GoogleMap> mapa = new MutableLiveData<>();

    private final LatLng ubicacionInmobiliaria = new LatLng(-33.2940, -66.3344);
    private final String tituloMarker = "Inmobiliaria LP";
    private final float zoom = 18f;

    public LiveData<GoogleMap> getMapa() {
        return mapa;
    }

    public void setMapa(GoogleMap map) {
        mapa.setValue(map);
        configurarMapa(map);
    }

    private void configurarMapa(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.addMarker(new MarkerOptions().position(ubicacionInmobiliaria).title(tituloMarker));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionInmobiliaria, zoom));
    }
}