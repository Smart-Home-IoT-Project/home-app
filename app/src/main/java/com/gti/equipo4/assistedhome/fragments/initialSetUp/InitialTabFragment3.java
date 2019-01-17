package com.gti.equipo4.assistedhome.fragments.initialSetUp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gti.equipo4.assistedhome.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

public class InitialTabFragment3 extends Fragment {

    View view;
    private ImageButton btnCleanNombre;
    private ImageButton btnCleanEmail;
    private ImageButton btnCleanTlfn;
    private Button btnSave;
    private RadioButton b1, b2, b3;

    private EditText editNombre;
    private EditText editEmail;
    private EditText editTlfn;
    private FirebaseUser usuario;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.app_intro3, container, false );
        final Context context = super.getContext();

        usuario = FirebaseAuth.getInstance().getCurrentUser();


        btnCleanNombre = view.findViewById( R.id.imageButtonInitialName );
        btnCleanEmail = view.findViewById( R.id.imageButtonInitialEmail );
        btnCleanTlfn = view.findViewById( R.id.imageButtonInitialTlfn );
        btnSave = view.findViewById( R.id.buttonInitialGuardar );
        b1 = view.findViewById( R.id.radioButtonInitialMale );
        b2 = view.findViewById( R.id.radioButtonFemale );
        b3 = view.findViewById( R.id.radioButtonInitialOtro );

        //grupoBtn = view.findViewById(R.id.grupoBtnGeneroIntro);


        editNombre = view.findViewById( R.id.editTextInitialName );
        editEmail = view.findViewById( R.id.editTextInitialEmail );
        editTlfn = view.findViewById( R.id.editTextInitialTlfn );

        editNombre.setText( usuario.getDisplayName() );
        editEmail.setText( usuario.getEmail() );
        editTlfn.setText( "" );

        btnCleanNombre.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                editNombre.setText( "" );
            }
        } );
        btnCleanEmail.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                editEmail.setText( "" );
            }
        } );
        btnCleanTlfn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                editTlfn.setText( "" );
            }
        } );
        btnSave.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                Map<String, Object> update = new HashMap<>();
                update.put("nombre", editNombre.getText().toString());
                update.put("email", editEmail.getText().toString());
                update.put("telefono", Double.parseDouble( editTlfn.getText().toString() ));
                update.put( "sexo", "" );

                db.collection("Usuarios" ).document(usuario.getUid()).set(update);
                getActivity().finish();
            }
        } );
        return  view;
    }
}
