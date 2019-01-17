package com.gti.equipo4.assistedhome.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.gti.equipo4.assistedhome.R;

public class PreferenciasFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
