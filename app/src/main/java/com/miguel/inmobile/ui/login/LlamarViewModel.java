package com.miguel.inmobile.ui.login;

import android.app.Application;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class LlamarViewModel extends AndroidViewModel implements SensorEventListener {
    private Sensor acelerometro; //sensor de movimiento
    private long ultimoTiempo; //tiempo anterior
    private float ultimo_x, ultimo_y, ultimo_z; //valores previos
    private static final int AGITAR = 600; //nivel de agite
    private MutableLiveData<Boolean> deteccionAgitado; //si se agito

    //constructor
    public LlamarViewModel(@NonNull Application application) {
        super(application);
        SensorManager sensorManager = (SensorManager) application.getSystemService(Application.SENSOR_SERVICE);
        if (sensorManager != null) {
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public MutableLiveData<Boolean> getDeteccionAgitado() {
        if (deteccionAgitado == null) {
            deteccionAgitado = new MutableLiveData<>();
        }
        return deteccionAgitado;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long tiempoActual = System.currentTimeMillis();

            //controlo tiempo entre lecturas
            if ((tiempoActual - ultimoTiempo) > 100) {
                long tiempoDiferencia = (tiempoActual - ultimoTiempo);
                ultimoTiempo = tiempoActual;

                //calculo velocidad
                float velocidad = Math.abs(x + y + z - ultimo_x - ultimo_y - ultimo_z) / tiempoDiferencia * 10000;

                //si se agito fuerte
                if (velocidad > AGITAR) {
                    deteccionAgitado.setValue(true);
                    enviarBroadcastDeteccionAgitado();
                } else {
                    deteccionAgitado.setValue(false);
                }

                //guardo los valores
                ultimo_x = x;
                ultimo_y = y;
                ultimo_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onCleared() {
        super.onCleared();
        //aca deja de escuchar el sensor
        SensorManager sensorManager = (SensorManager) getApplication().getSystemService(Application.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    //manda el broadcast
    private void enviarBroadcastDeteccionAgitado() {
        Intent intent = new Intent(getApplication().getApplicationContext(), LlamadaBroadcast.class);
        intent.putExtra("deteccion_agitado", true);
        getApplication().sendBroadcast(intent);
    }
}