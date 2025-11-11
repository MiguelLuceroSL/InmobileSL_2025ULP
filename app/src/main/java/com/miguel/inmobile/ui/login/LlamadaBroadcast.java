package com.miguel.inmobile.ui.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.content.ContextCompat;
import android.widget.Toast;

public class LlamadaBroadcast extends BroadcastReceiver {
    public static boolean appIniciada = false; //si la app ya arranco

    @Override
    public void onReceive(Context context, Intent intent) {
        //veo si hubo agite
        boolean deteccionAgitado = intent.getBooleanExtra("deteccion_agitado", false);
        if (deteccionAgitado) {
            realizarLlamada(context);
        }
        appIniciada = true;
    }

    //aca hace la llamada
    private void realizarLlamada(Context context) {
        Intent intentLlamada = new Intent(Intent.ACTION_CALL);
        intentLlamada.setData(Uri.parse("tel:2664553747")); //telefono del profe
        intentLlamada.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //verifico permisos
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intentLlamada);
        } else {
            Toast.makeText(context, "Permiso denegado para llamadas telefónicas", Toast.LENGTH_LONG).show();
        }
    }
}