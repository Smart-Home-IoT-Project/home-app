package com.gti.equipo4.assistedhome.interfaces;

import com.gti.equipo4.assistedhome.model.Medicine;

public interface MedicinesAsync {

    interface EscuchadorElemento{
        void onRespuesta(Medicine medicina);
    }

    interface EscuchadorTamanyo{
        void onRespuesta(long tamanyo);
    }

    void elemento(String id, EscuchadorElemento escuchador);
    void anyade(Medicine medicina);
    String nuevo();
    void borrar(String id);
    void actualiza(String id, Medicine medicina);
    void tamanyo(EscuchadorTamanyo escuchador);

}
