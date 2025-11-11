package com.miguel.inmobile.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class HomeViewModel extends ViewModel {
    private final MutableLiveData<GoogleMap> mapa = new MutableLiveData<>(); //livedata para guardar el mapa de google
    private final LatLng ubicacionInmobiliaria = new LatLng(-33.2940, -66.3344); //ubicacion con las coordenadas de la inmo
    private final String tituloMarker = "Inmobiliaria LP"; //titulo del marcador que se va a mostrar en el mapa
    private final float zoom = 18f; //nivel de zoom para acercar la camara al marcador
    public LiveData<GoogleMap> getMapa() { return mapa; } // devuelvo el livedata del mapa para poder observarlo desde el fragment

    //este metodo se llama cuando el mapa esta listo
    public void setMapa(GoogleMap map) {
        mapa.setValue(map); //guardo el mapa en el livedata
        configurarMapa(map); //llamo a otro metodo que configura el mapa
    }

    //aca se configura el mapa
    private void configurarMapa(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID); //cambio el tipo de mapa a hibrido (mezcla satelite y normal)

        //agrego un marcador en la ubicacion de la inmobiliaria con su titulo
        googleMap.addMarker(new MarkerOptions().position(ubicacionInmobiliaria).title(tituloMarker));

        //muevo la camara para que apunte al marcador y con el zoom
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionInmobiliaria, zoom));
    }
}