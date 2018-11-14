package com.gti.equipo4.assistedhome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.model.Weight;

import androidx.recyclerview.widget.RecyclerView;

public class Weights extends
        RecyclerView.Adapter<Weights.ViewHolder> {
    protected com.gti.equipo4.assistedhome.interfaces.Weights lugares;
    // Lista de lugares a mostrar
    protected LayoutInflater inflador; // Crea Layouts a partir del XML
    protected Context contexto;

    // Lo necesitamos para el inflador
    public Weights(Context contexto, com.gti.equipo4.assistedhome.interfaces.Weights lugares) {
        this.contexto = contexto;
        this.lugares = lugares;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView peso;
        public TextView altura;
        public TextView hora;


        public ViewHolder(View itemView) {
            super(itemView);
            peso = itemView.findViewById(R.id.weightText);
            altura = itemView.findViewById(R.id.height_text);
            hora = itemView.findViewById(R.id.hour);

        }
    }
    // Creamos el ViewHolder con la vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_lista, parent, false);
        return new ViewHolder(v);
    }
    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Weight lugar = lugares.elemento(posicion);
        personalizaVista(holder, lugar);
    }
// Personalizamos un ViewHolder a partir de un lugar
public static void personalizaVista(ViewHolder holder, Weight lugar) {
        String stringPeso = ""+lugar.getPeso();
        String stringAltura = ""+lugar.getAltura();
        holder.peso.setText(stringPeso);
        holder.altura.setText(stringAltura);
        holder.hora.setText(lugar.getHora());


}
    // Indicamos el número de elementos de la lista
    @Override public int getItemCount() {
        return lugares.tamanyo();
    }
}