package com.gti.equipo4.assistedhome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.model.Medicine;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MedicinesAdapter extends
        RecyclerView.Adapter<MedicinesAdapter.ViewHolder> {
    protected com.gti.equipo4.assistedhome.interfaces.Medicines medicinas;
    // Lista de lugares a mostrar
    protected LayoutInflater inflador; // Crea Layouts a partir del XML
    protected Context contexto;

    // Lo necesitamos para el inflador
    public MedicinesAdapter(Context contexto, com.gti.equipo4.assistedhome.interfaces.Medicines medicinas) {
        this.contexto = contexto;
        this.medicinas = medicinas;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public TextView dias;
        public TextView cantidad;


        public ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreMedicine);
            dias = itemView.findViewById(R.id.diasMedicine);
            cantidad = itemView.findViewById(R.id.cantidadMedicamento);
        }
    }
    // Creamos el ViewHolder con la vista de un elemento sin personalizar
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_lista_medicines, parent, false);
        return new ViewHolder(v);
    }
    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Medicine medicina = (Medicine) medicinas.elemento(posicion);
        personalizaVista(holder, medicina);
    }
    // Personalizamos un ViewHolder a partir de un lugar
    public static void personalizaVista(ViewHolder holder, Medicine medicina) {
        String stringNombre = ""+medicina.getNombre();
        String stringDias = ""+medicina.getDias();
        String stringCantidad = ""+medicina.getCantidad();
        holder.nombre.setText(stringNombre);
        holder.dias.setText(stringDias);
        holder.cantidad.setText(stringCantidad);
    }
    // Indicamos el número de elementos de la lista
    @Override public int getItemCount() {
        return medicinas.tamanyo();
    }
}