package com.gti.equipo4.assistedhome.fragments.menu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.activities.MainActivity;
import com.gti.equipo4.assistedhome.activities.Mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import androidx.fragment.app.Fragment;

import static com.firebase.ui.auth.AuthUI.TAG;
import static com.gti.equipo4.assistedhome.activities.Mqtt.broker;
import static com.gti.equipo4.assistedhome.activities.Mqtt.qos;
import static com.gti.equipo4.assistedhome.activities.Mqtt.topicRoot;
import static com.gti.equipo4.assistedhome.activities.Mqtt.clientId;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CasaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CasaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment implements MqttCallback {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private View view;

    public Switch luces;

    //----------------MQTT---------------------
    MqttClient client;
    //----------------MQTT---------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //---------------MQTT---------------------
        try {
            Log.i(Mqtt.TAG, "Conectando al broker " + broker);
            client = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(60);
            connOpts.setWill(topicRoot+"WillTopic", "App desconectada".getBytes(),
                    qos, false);
            client.connect(connOpts);
        } catch (MqttException e) {
            Log.e(Mqtt.TAG, "Error al conectar.", e);
        }

        try {
            Log.i(Mqtt.TAG, "Suscrito a " + topicRoot+"cmnd/POWER");
            client.subscribe(topicRoot+"cmnd/POWER", qos);
            client.setCallback(this);
        } catch (MqttException e) {
            Log.e(Mqtt.TAG, "Error al suscribir.", e);
        }
        //---------------MQTT---------------------
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);
        //view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        luces = view.findViewById(R.id.luzswitch);

        //---------------MQTT---------------------
        luces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (luces.isChecked()) {
                    try {
                        Log.i(Mqtt.TAG, "Publicando mensaje: " + "encender sonoff");
                        MqttMessage message = new MqttMessage("ON".getBytes());
                        message.setQos(qos);
                        message.setRetained(false);
                        client.publish(topicRoot + "cmnd/POWER", message);
                        Toast.makeText(getContext(), "Luces encendidas", Toast.LENGTH_SHORT).show();
                    } catch (MqttException e) {
                        luces.setChecked(false);
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error", e);
                    }
                }
                if (!luces.isChecked()) {
                    try {
                        Log.i(Mqtt.TAG, "Publicando mensaje: " + "apagar sonoff");
                        MqttMessage message = new MqttMessage("OFF".getBytes());
                        message.setQos(qos);
                        message.setRetained(false);
                        client.publish(topicRoot + "cmnd/POWER", message);
                        Toast.makeText(getContext(), "Luces apagadas", Toast.LENGTH_SHORT).show();
                    } catch (MqttException e) {
                        luces.setChecked(true);
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error", e);
                    }
                }
            }
        });
        //---------------MQTT---------------------

        // Inflate the layout for this fragment
        return view;
    }

    @Override public void connectionLost(Throwable cause) {
        Log.d(TAG, "Conexión perdida");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        final String payload = new String(message.getPayload());
        Log.d(TAG, "Recibiendo: " + topic + "->" + payload);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                luces = view.findViewById(R.id.luzswitch);
                if (payload.contains("ON")) {
                    luces.setChecked(true);
                    Toast.makeText(getContext(), "Luces encendidas", Toast.LENGTH_SHORT).show();
                }
                if (payload.contains("OFF")) {
                    luces.setChecked(false);
                    Toast.makeText(getContext(), "Luces apagadas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
