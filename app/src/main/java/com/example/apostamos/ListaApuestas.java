package com.example.apostamos;

import android.net.Uri;

public class ListaApuestas {
    private String apostador;
    private int imgClub;
    private String apuesta;

    public ListaApuestas( String apostador, int imgClub, String apuesta) {
        this.apostador = apostador;
        this.imgClub = imgClub;
        this.apuesta = apuesta;
    }

    public String getApostador() {
        return apostador;
    }

    public int getImgClub() {
        return imgClub;
    }

    public String getApuesta() {
        return apuesta;
    }
}
