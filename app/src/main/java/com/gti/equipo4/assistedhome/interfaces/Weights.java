package com.gti.equipo4.assistedhome.interfaces;

import com.gti.equipo4.assistedhome.model.Weight;

public interface Weights {
    Weight elemento(int id); //Devuelve el elemento dado su id
    void anyade(Weight peso); //Añade el elemento indicado
    int nuevo(); //Añade un elemento en blanco y devuelve su id
    void borrar(int id); //Elimina el elemento con el id indicado
    int tamanyo(); //Devuelve el número de elementos
    void actualiza(int id, Weight lugar); //Reemplaza un elemento
}


