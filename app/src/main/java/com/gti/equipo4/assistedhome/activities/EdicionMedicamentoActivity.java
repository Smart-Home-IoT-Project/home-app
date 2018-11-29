package com.gti.equipo4.assistedhome.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.fragments.medicines.MedicinesTabFragment1;
import com.gti.equipo4.assistedhome.fragments.menu.sensors;
import com.gti.equipo4.assistedhome.interfaces.Medicines;
import com.gti.equipo4.assistedhome.model.Medicine;
import com.gti.equipo4.assistedhome.adapters.*;
import com.gti.equipo4.assistedhome.model.Weight;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EdicionMedicamentoActivity extends AppCompatActivity {

    private long id;
    private String _id; //Clave del lugar
    private Medicine medicina;
    private EditText nombre;
    private EditText dias;
    private EditText cantidad;
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private NfcAdapter mNfcAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_medicines);
        Bundle extras = getIntent().getExtras();/*
        new AlertDialog.Builder(this)
                .setTitle("¿Quieres coger los datos por NFC?").setMessage("Esto es una aplicación IoT dedicada a hacerte la vida mas facil")
                .setPositiveButton( "SI" , null)
                .setNegativeButton("No", null)
                .show();*/
        id = extras.getLong("id", -1);
        if (id == -1) {
            medicina = new Medicine();
            _id = null;
        } else {
            medicina = MedicinesTabFragment1.adaptador.getItem((int) id);
            _id = MedicinesTabFragment1.adaptador.getKey((int) id);
        }
        nombre = findViewById(R.id.editNombre);
        nombre.setText(medicina.getNombre());
        cantidad = findViewById(R.id.editCantidad);
        cantidad.setText(Integer.toString(medicina.getCantidad()));
        dias = findViewById(R.id.editDias);
        dias.setText(medicina.getDias());

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);


        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
            nombre.setText("NFC is disabled.");
        } else {
            nombre.setText(R.string.app_name);
        }
        handleIntent(getIntent());


    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edicion_medicines, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_guardar:
                medicina.setNombre(nombre.getText().toString());
                medicina.setCantidad(Integer.parseInt(cantidad.getText().toString()));
                medicina.setDias(dias.getText().toString());
                if (id!=-1) { MedicinesTabFragment1.medicinas.actualiza(_id, medicina); }
                if (id == -1) { MedicinesTabFragment1.medicinas.anyade( medicina );}
                finish();
                return true;
            case R.id.accion_cancelar:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        stopForegroundDispatch(this, mNfcAdapter);

        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    public class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {

            byte[] payload = record.getPayload();

            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

            int languageCodeLength = payload[0] & 0063;

            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // modelo de como deberia de estar escrito el tag
                //s = "{\"nombre\":\"fluimocil\",\"cantidad\":5\"dias\"Luner,Martes...}";
                JsonParser jsonParser = new JsonParser();
                JsonObject data = (JsonObject) jsonParser.parse(result).getAsJsonObject();
                nombre.setText(""+ data.getAsJsonObject().get("nombre").getAsString());
                cantidad.setText(""+ data.getAsJsonObject().get("cantidad").getAsString());
                dias.setText(""+ data.getAsJsonObject().get("dias").getAsString());

            }
        }
    }

}//()
