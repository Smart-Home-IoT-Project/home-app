package com.gti.equipo4.assistedhome.fragments.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.activities.ReadNFCActivity;
import com.gti.equipo4.assistedhome.activities.VistaMedicamentoActivity;
import com.gti.equipo4.assistedhome.fragments.medicines.MedicinesTabFragment1;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class sensors extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent i = new Intent(sensors.super.getContext() , ReadNFCActivity.class);
        super.startActivity(i);
        return inflater.inflate(R.layout.sensors, container, false);
    }
}