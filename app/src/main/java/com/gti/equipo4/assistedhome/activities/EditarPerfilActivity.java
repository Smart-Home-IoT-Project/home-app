package com.gti.equipo4.assistedhome.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gti.equipo4.assistedhome.R;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class EditarPerfilActivity extends AppCompatActivity {

    private ImageButton btnCleanNombre;
    private ImageButton btnCleanEmail;
    private ImageButton btnCleanTlfn;
    private Button btnSave;

    private EditText editNombre;
    private EditText editEmail;
    private EditText editTlfn;
    private RadioGroup grupoSexo;
    private FirebaseUser usuario;
    private ImageView fotoUsuario;


    @Override
    public void onCreate(Bundle savedInstanceState){


        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_perfil);
        Context context = super.getApplicationContext();

        Bundle extras = getIntent().getExtras();
        usuario = FirebaseAuth.getInstance().getCurrentUser();


        btnCleanNombre =  findViewById( R.id.imageButtonNombre);
        btnCleanEmail =  findViewById( R.id.imageButtonEmail);
        btnCleanTlfn =  findViewById( R.id.imageButtonTlfn);
        btnSave =  findViewById( R.id.buttonSaveEditPerfil);


        editNombre = findViewById(R.id.editTextNombre);
        editEmail = findViewById(R.id.editTextEmail);
        editTlfn = findViewById(R.id.editTextTlfn);

        editNombre.setText( usuario.getDisplayName() );
        editEmail.setText( usuario.getEmail() );
        editTlfn.setText( "" );

        // Foto de usuario
        Uri urlImagen = usuario.getPhotoUrl();
        if (urlImagen != null) {
            fotoUsuario = findViewById(R.id.imageViewEditPerfil );
            Picasso.with(context).load(urlImagen).into(fotoUsuario);
            fotoUsuario.setImageURI(urlImagen);
        }


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
        btnSave.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                //guardar;
                finish();
            }
        });


    }

}
