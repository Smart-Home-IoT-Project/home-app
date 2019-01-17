package com.gti.equipo4.assistedhome.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gti.equipo4.assistedhome.R;

public class Alerts {

    // Preferences
    SharedPreferences sharedPreferences;

    // Max and min values
    double maxTempValue;
    double minTempValue;

    double maxHumValue;
    double minHumValue;

    // Notification manager
    Notifications newNot;



    public Alerts (Activity activity, Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        PreferenceManager.setDefaultValues(context, R.xml.preferencias, false);

        newNot = new Notifications(context);



    }

    public void checkTemp(double value){
        // Read preferences
        maxTempValue = getPreferences("maxTempValue");
        minTempValue = getPreferences("minTempValue");


        // Check
        if (value < minTempValue){
            newNot.createNotification("Alerta temperatura", "Temperatura anormalmente baja");
        }

        if(value > maxTempValue){
            newNot.createNotification("Alerta temperatura", "Temperatura anormalmente alta");
        }
    }

    public void checkHum(double value){
        // Read preferences
        maxHumValue = getPreferences("maxHumValue");
        minHumValue = getPreferences("minHumValue");

        // Check
        if (value < minHumValue){
            newNot.createNotification("Alerta humedad", "Humedad anormalmente baja");
        }

        if(value > maxHumValue){
            newNot.createNotification("Alerta humedad", "Humedad anormalmente alta");
        }
    }

    private int getPreferences(String key){
        String value = sharedPreferences.getString(key, "0");

        int intValue = Integer.parseInt(value);
        //Log.d("paco",value);

        return intValue;
    }
}
