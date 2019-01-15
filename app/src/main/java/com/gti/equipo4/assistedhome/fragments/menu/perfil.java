package com.gti.equipo4.assistedhome.fragments.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.activities.EditarPerfilActivity;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

public class perfil extends Fragment {

    private View view;
    private FirebaseUser usuario;
    private String genero;
    private long tlfn;
    private ImageView fotoUsuario;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.perfil, container, false);
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        String uidUsuario = usuario.getUid();
        final Context context = super.getContext();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Usuarios")
                .document(uidUsuario)
                .get()
                .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task){
                        if (task.isSuccessful()) {
                            genero = task.getResult().getString("sexo");
                            tlfn = task.getResult().getLong("telefono");
                            TextView viewGenero = view.findViewById(R.id.textViewGenero);
                            viewGenero.setText(genero);
                            TextView telefono = view.findViewById(R.id.textViewTlfn);
                            telefono.setText(Long.toString( tlfn ));
                        } else {
                            Log.e("Firestore", "Error al leer", task.getException());
                        }
                    }
                });

        TextView nombre = view.findViewById(R.id.userName);
        nombre.setText(usuario.getDisplayName());
        TextView correo = view.findViewById(R.id.userEmail);
        correo.setText(usuario.getEmail());

        // Foto de usuario
        Uri urlImagen = usuario.getPhotoUrl();
        if (urlImagen != null) {
            fotoUsuario = view.findViewById(R.id.imageViewPerfil );
            Picasso.with(getContext()).load(urlImagen).into(fotoUsuario);
            fotoUsuario.setImageURI(urlImagen);
        }

        Button botonEditar =  view.findViewById( R.id.buttonEditarPerfil);
        botonEditar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                String nombre = usuario.getDisplayName();
                String correo = usuario.getEmail();
                Intent i = new Intent(context, EditarPerfilActivity.class);
                i.putExtra("nombre", nombre)
                        .putExtra("correo", correo)
                        .putExtra("genero", genero)
                        .putExtra("tlfn", Long.toString( tlfn ));
                perfil.super.startActivity(i);
            }
        });

        return view;
    }

    public void lanzarEditarPerfil (View view){
        String nombre = usuario.getDisplayName();
        String correo = usuario.getEmail();
        Intent i = new Intent(this.getContext(), EditarPerfilActivity.class);
        i.putExtra("nombre", nombre)
                .putExtra("correo", correo)
                .putExtra("genero", genero)
                .putExtra("tlfn", Long.toString( tlfn ))
                .putExtra("user", usuario);
        startActivity(i);
    }
}