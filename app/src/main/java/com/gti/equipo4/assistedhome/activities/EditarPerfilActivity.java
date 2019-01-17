package com.gti.equipo4.assistedhome.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import androidx.appcompat.app.AppCompatActivity;

public class EditarPerfilActivity extends AppCompatActivity {

    private ImageButton btnCleanNombre;
    private ImageButton btnCleanEmail;
    private ImageButton btnCleanTlfn;
    private Button btnSave;

    private EditText editNombre;
    private EditText editEmail;
    private EditText editTlfn;
    private FirebaseUser usuario;
    private ImageView fotoUsuario;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



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

        editNombre.setText( extras.getString( "nombre" ) );
        editEmail.setText( extras.getString( "correo" ) );
        editTlfn.setText( extras.getString( "telefono" ) );

        // Foto de usuario

        final Uri urlImagen = usuario.getPhotoUrl();
        if (urlImagen != null) {
            fotoUsuario = findViewById(R.id.imageViewEditPerfil );
            Picasso.with(context).load(urlImagen).into(fotoUsuario);
            fotoUsuario.setImageURI(urlImagen);
        }

        //sexo
        radioGroup = findViewById( R.id.radioGroupGenero );
        int id = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(id);


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
                int id = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(id);
                Map<String, Object> update = new HashMap<>();
                update.put("nombre", editNombre.getText().toString());
                update.put("email", editEmail.getText().toString());
                update.put("telefono", Double.parseDouble( editTlfn.getText().toString() ));
                update.put("sexo", radioButton.getText().toString());

                db.collection("Usuarios" ).document(usuario.getUid()).set(update);
                finish();
            }
        });


    }

}
