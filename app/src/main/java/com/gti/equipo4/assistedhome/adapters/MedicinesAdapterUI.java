package com.gti.equipo4.assistedhome.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.model.Medicine;

import java.util.List;

import androidx.annotation.NonNull;

public class MedicinesAdapterUI extends
        FirestoreRecyclerAdapter<Medicine, MedicinesAdapter.ViewHolder>{
    protected View.OnClickListener onClickListener;
    public MedicinesAdapterUI(
            @NonNull FirestoreRecyclerOptions<Medicine> options) {
        super(options);
    }
    @NonNull
    @Override public MedicinesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento_lista_medicines, parent, false);
        return new MedicinesAdapter.ViewHolder(view);
    }
    @Override
    protected void onBindViewHolder(@NonNull MedicinesAdapter.ViewHolder holder, int position, @NonNull Medicine medicamento) {
        MedicinesAdapter.personalizaVista(holder, medicamento);
        holder.itemView.setOnClickListener(onClickListener);
    }
    public void setOnItemClickListener(View.OnClickListener onClick) {
        onClickListener = onClick;
    }
    public String getKey(int pos) {
        return super.getSnapshots().getSnapshot(pos).getId();
    }
}
