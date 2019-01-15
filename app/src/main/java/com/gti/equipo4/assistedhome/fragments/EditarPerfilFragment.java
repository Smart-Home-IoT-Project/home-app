package com.gti.equipo4.assistedhome.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gti.equipo4.assistedhome.R;

import androidx.fragment.app.Fragment;

public class EditarPerfilFragment extends Fragment {
    
    private ImageButton btnCleanNombre;
    private ImageButton btnCleanEmail;
    private ImageButton btnCleanTlfn;
    private EditText editNombre;
    private EditText editEmail;
    private EditText editTlfn;
    private RadioGroup grupoSexo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edicion_perfil, container, false);


        btnCleanNombre =  view.findViewById( R.id.imageButtonNombre);
        btnCleanEmail =  view.findViewById( R.id.imageButtonEmail);
        btnCleanTlfn =  view.findViewById( R.id.imageButtonTlfn);

        editNombre = view.findViewById(R.id.editTextNombre);
        editEmail = view.findViewById(R.id.editTextEmail);
        editTlfn = view.findViewById(R.id.editTextTlfn);

        btnCleanNombre.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                editNombre.setText("");
            }
        });
        btnCleanEmail.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                editEmail.setText("");
            }
        });
        btnCleanTlfn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                editTlfn.setText("");
            }
        });

        return view;
    }




}
