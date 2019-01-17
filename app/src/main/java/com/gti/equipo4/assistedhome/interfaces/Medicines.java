package com.gti.equipo4.assistedhome.interfaces;

import com.gti.equipo4.assistedhome.model.Medicine;

public interface Medicines {
    Medicine elemento(int id); //Devuelve el elemento dado su id
    void anyade(Medicine medicina); //Añade el elemento indicado
    int nuevo(); //Añade un elemento en blanco y devuelve su id
    void borrar(int id); //Elimina el elemento con el id indicado
    int tamanyo(); //Devuelve el número de elementos
    void actualiza(int id, Medicine medicina); //Reemplaza un elemento
}
