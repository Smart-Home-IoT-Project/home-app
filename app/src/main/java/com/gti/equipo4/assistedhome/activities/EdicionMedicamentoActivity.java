package com.gti.equipo4.assistedhome.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.interfaces.Medicines;
import com.gti.equipo4.assistedhome.model.Medicine;
import com.gti.equipo4.assistedhome.adapters.*;
import com.gti.equipo4.assistedhome.model.Weight;

import androidx.appcompat.app.AppCompatActivity;

public class EdicionMedicamentoActivity extends AppCompatActivity {
/*
    private long id;
    private String _id; //Clave del lugar
    private Medicine medicina;
    private EditText nombre;
    private Spinner dias;
    private EditText cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_lugar);
        Bundle extras = getIntent().getExtras();

        id = extras.getLong("id", -1);
        if (_id != null) {
            medicina = new Medicine();
            _id = null;
        } else {
            medicina = MedicinesAdapterUI.getItem((int) id);
            _id = MedicinesAdapterUI.getKey((int) id);
        }
        nombre = findViewById(R.id.nombreMedicine);
        nombre.setText(Medicine.getNombre());
        cantidad = findViewById(R.id.cantidadMedicamentos);
        cantidad.setText(Medicine.setCantidad());



    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edicion_lugar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_guardar:
                medicina.setNombre(nombre.getText().toString());
                medicina.setCantidad(Integer.parseInt(cantidad.getText().toString()));
                if (id!=-1) { Medicines.actualiza(_id, medicina); }
                finish();
                return true;
            case R.id.accion_cancelar:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
}//()
