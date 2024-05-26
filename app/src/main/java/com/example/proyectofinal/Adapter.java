package com.example.proyectofinal;

import android.content.Context;

import java.util.List;

public class Adapter {

    Context context;
    List<Administrador>arrayAdministrador;

    public Adapter(Context context, List<Administrador> arrayAdministrador) {
        this.context = context;
        this.arrayAdministrador = arrayAdministrador;
    }
}


