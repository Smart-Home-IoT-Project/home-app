package com.gti.equipo4.assistedhome.fragments.menu;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.gti.equipo4.assistedhome.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

public class perfil extends Fragment {

    private View view;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.perfil, container, false);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        String uidUsuario = usuario.getUid();
        final String[] tlfn = new String[1];
        final String[] genero = new String[1];
/*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Usuarios")
                .document(uidUsuario)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            tlfn[0] = (String) doc.get("telefono");
                            genero[0] = (String) doc.get("sexo");
                        }
                    }
                });

*/
        TextView nombre = view.findViewById(R.id.userName);
        nombre.setText(usuario.getDisplayName());
        TextView correo = view.findViewById(R.id.userEmail);
        correo.setText(usuario.getEmail());

        // Foto de usuario
        Uri urlImagen = usuario.getPhotoUrl();
        if (urlImagen != null) {
            ImageView fotoUsuario = view.findViewById(R.id.imageViewPerfil );
            fotoUsuario.setImageURI(urlImagen);
        }


        return view;
    }
}