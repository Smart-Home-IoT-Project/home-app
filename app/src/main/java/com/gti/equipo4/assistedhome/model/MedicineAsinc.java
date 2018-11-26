package com.gti.equipo4.assistedhome.model;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gti.equipo4.assistedhome.interfaces.MedicinesAsync;

import androidx.annotation.NonNull;

public class MedicineAsinc implements MedicinesAsync {
    private CollectionReference medicinas;

    public MedicineAsinc() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        medicinas = db.collection("Medicamentos");
    }

    public void elemento(String id, final EscuchadorElemento escuchador) {
        medicinas.document(id).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Medicine medicina = task.getResult().toObject(Medicine.class);
                            escuchador.onRespuesta(medicina);
                        } else {
                            Log.e("Firebase", "Error al leer", task.getException());
                            escuchador.onRespuesta(null);
                        }
                    }
                });
    }

    public void anyade(Medicine medicina) {
        medicinas.document().set(medicina); //o lugares.add(lugar);
    }

    public String nuevo() {
        return medicinas.document().getId();
    }
    public void borrar(String id) {
        medicinas.document(id).delete();
    }
    public void actualiza(String id, Medicine medicina) {
        medicinas.document(id).set(medicina);
    }
    public void tamanyo(final EscuchadorTamanyo escuchador) {
        medicinas.get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            escuchador.onRespuesta(task.getResult().size());
                        } else {
                            Log.e("Firebase","Error en tamanyo",task.getException());
                            escuchador.onRespuesta(-1);
                        }
                    }
                });
    }
}