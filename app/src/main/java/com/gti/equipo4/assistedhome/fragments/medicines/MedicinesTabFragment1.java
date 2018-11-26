package com.gti.equipo4.assistedhome.fragments.medicines;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.activities.*;
import com.gti.equipo4.assistedhome.adapters.*;
import com.gti.equipo4.assistedhome.model.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MedicinesTabFragment1 extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    public static MedicinesAdapterUI adaptador;
    public static MedicineAsinc medicinas;
    FirebaseUser usuario;
    String uidUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.medicines_tab_fragment_1, container, false);

        usuario = FirebaseAuth.getInstance().getCurrentUser();
        uidUsuario = usuario.getUid();


        final View view = inflater.inflate(R.layout.medicines_tab_fragment_1, container, false);
        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        //Recogida datos db
                Query query = FirebaseFirestore.getInstance()
                        .collection("Casa_1213") // TODO: Coger id de la casa dinamicamente
                        .document("medicamentos")
                        .collection(uidUsuario) // Documento del usuario
                        .limit(50);
                FirestoreRecyclerOptions<Medicine> opciones = new FirestoreRecyclerOptions
                        .Builder<Medicine>().setQuery(query, Medicine.class).build();
                adaptador = new MedicinesAdapterUI(opciones);

        recyclerView.setAdapter(adaptador);
        adaptador.startListening();
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MedicinesTabFragment1.super.getContext(), VistaMedicamentoActivity.class);
                i.putExtra("id", (long) recyclerView.getChildAdapterPosition(v));
                startActivity(i);
            }
        });

        medicinas = new MedicineAsinc();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() .permitAll().build());

        return view;

    }
}