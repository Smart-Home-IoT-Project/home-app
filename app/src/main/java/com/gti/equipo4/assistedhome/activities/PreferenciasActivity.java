package com.gti.equipo4.assistedhome.activities;

import android.os.Bundle;
import com.gti.equipo4.assistedhome.fragments.PreferenciasFragment;

import androidx.appcompat.app.AppCompatActivity;

public class PreferenciasActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenciasFragment()).commit();
    }
}
