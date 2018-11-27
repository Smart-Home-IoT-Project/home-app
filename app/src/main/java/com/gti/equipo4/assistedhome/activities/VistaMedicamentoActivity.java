package com.gti.equipo4.assistedhome.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.adapters.MedicinesAdapter;
import com.gti.equipo4.assistedhome.adapters.MedicinesAdapterUI;
import com.gti.equipo4.assistedhome.fragments.medicines.MedicinesTabFragment1;
import com.gti.equipo4.assistedhome.interfaces.Medicines;
import com.gti.equipo4.assistedhome.model.Medicine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static androidx.core.content.FileProvider.getUriForFile;

public class VistaMedicamentoActivity extends AppCompatActivity {
    private long id; //Posición en el RecyclerView
    private Medicine medicina;
    final static int RESULTADO_EDITAR = 1;
    private ImageView imageView;
    final static int RESULTADO_GALERIA = 2;
    final static int RESULTADO_FOTO = 3;
    private Uri uriFoto;
    private String _id; //Clave del lugar
    private static final int SOLICITUD_PERMISO_READ_EXTERNAL_STORAGE = 0;
    private static final int SOLICITUD_PERMISO_CALL_PHONE = 0;



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_medicamento);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id", -1);
        imageView = findViewById(R.id.foto);
        medicina = MedicinesTabFragment1.adaptador.getItem((int) id);
        _id = MedicinesTabFragment1.adaptador.getKey((int) id);
        actualizarVistas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vista_medicamento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_editar:
                Intent i = new Intent(this, EdicionMedicamentoActivity.class);
                i.putExtra("id",id);
                startActivityForResult(i, RESULTADO_EDITAR);
                return true;
            case R.id.accion_borrar:
                borrarLugar((int) id);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULTADO_EDITAR) {
            actualizarVistas();
            findViewById(R.id.vistaMedicamento).invalidate();
        } else if (requestCode == RESULTADO_GALERIA) {
            if (resultCode == Activity.RESULT_OK) {
                medicina.setFoto(data.getDataString());
                ponerFoto(imageView, medicina.getFoto());
                actualizaLugar();
            } else {
                Toast.makeText(this, "Foto no cargada",Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == RESULTADO_FOTO) {
            if (resultCode == Activity.RESULT_OK && medicina!=null && uriFoto!=null) {
                medicina.setFoto(uriFoto.toString());
                ponerFoto(imageView, medicina.getFoto());
                actualizaLugar();
            } else {
                Toast.makeText(this, "Error en captura", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void borrarLugar(final int id) {
        new AlertDialog.Builder(this)
                .setTitle("Borrado de medicamento")
                .setMessage("¿Esta seguro de que quieres eliminar este medicamento?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        MedicinesTabFragment1.medicinas.borrar(_id);
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @SuppressLint("SetTextI18n")
    public void actualizarVistas() {
        if (medicina.getNombre() == null) {
            findViewById(R.id.nombreMedicamento).setVisibility(View.GONE);
        } else {
            findViewById(R.id.nombreMedicamento).setVisibility(View.VISIBLE);
            TextView nombre = findViewById(R.id.nombreMedicamento);
            nombre.setText(medicina.getNombre());
        }

        if (medicina.getCantidad() == 0) {
            findViewById(R.id.cantidad).setVisibility(View.GONE);
            findViewById(R.id.imageViewCantidad).setVisibility(View.GONE);
            findViewById(R.id.textCantidad).setVisibility(View.GONE);

        } else {
            findViewById(R.id.cantidad).setVisibility(View.VISIBLE);
            TextView cantidad = findViewById(R.id.cantidad);
            cantidad.setText(Integer.toString(medicina.getCantidad()));
        }

        if (medicina.getDias() == null) {
            findViewById(R.id.dias).setVisibility(View.GONE);
            findViewById(R.id.imageViewDias).setVisibility(View.GONE);
        } else {
            findViewById(R.id.dias).setVisibility(View.VISIBLE);
            TextView dias = findViewById(R.id.dias);
            dias.setText(medicina.getDias());
        }

        ponerFoto(imageView, medicina.getFoto());

    }

    public void galeria(View view) {

        String action;
        if (android.os.Build.VERSION.SDK_INT >= //4.4
                android.os.Build.VERSION_CODES.KITKAT) {
            action = Intent.ACTION_OPEN_DOCUMENT;
        } else {
            action = Intent.ACTION_PICK;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(action, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, RESULTADO_GALERIA);

        } else {
            solicitarPermiso(Manifest.permission.READ_EXTERNAL_STORAGE, "Sin el permiso"+
                            " administrar imagenes",
                    SOLICITUD_PERMISO_READ_EXTERNAL_STORAGE, this);
        }
    }

    public void ponerFoto(ImageView imageView, String uri) {
        if (uri != null && !uri.isEmpty() && !uri.equals("null")) {
            imageView.setImageBitmap(reduceBitmap(this, uri, 1024, 1024));
        } else {
            imageView.setImageBitmap(null);
        }
    }

    public void tomarFoto (View view) {
        String fileName = "img_" + (System.currentTimeMillis() / 1000);
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        if (Build.VERSION.SDK_INT >= 24) {
            uriFoto = getUriForFile(this, "fileProvider", file); }
        else {
            uriFoto = Uri.fromFile(file); }
        Intent intent   = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra (MediaStore.EXTRA_OUTPUT, uriFoto);
        super.startActivityForResult(intent, RESULTADO_FOTO);
    }

    public void eliminarFoto(View view) {
        medicina.setFoto(null);
        ponerFoto(imageView, null);
        actualizaLugar();
    }

    public static Bitmap reduceBitmap(Context contexto, String uri, int maxAncho, int maxAlto) {
        try {
            InputStream input = null;
            Uri u = Uri.parse(uri);
            if (u.getScheme().equals("http") || u.getScheme().equals("https")) {
                input = new URL(uri).openStream();
            } else {
                input = contexto.getContentResolver().openInputStream(u);
            }
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));

            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(input, null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Fichero/recurso de imagen no encontrado",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace(); return null;
        } catch (IOException e) {
            Toast.makeText(contexto, "Error accediendo a imagen", Toast.LENGTH_LONG).show();
            e.printStackTrace(); return null;
        }
    }

    void actualizaLugar(){ MedicinesTabFragment1.medicinas.actualiza(_id, medicina); }

    public static void solicitarPermiso(final String permiso, String justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad, permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad, new String[]{permiso}, requestCode);
                        }})
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_READ_EXTERNAL_STORAGE) {
            if (grantResults.length== 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                View v = findViewById(R.id.galeria);
                galeria(v);
            } else { Toast.makeText(this, "Sin el permiso, no puedo realizar la " + "acción", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
