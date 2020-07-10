package com.example.apostamos;

import android.net.Uri;

public class ListaApuestas {
    private String apostador;
    private String club;
    private String apuesta;

    public ListaApuestas( String apostador, String apuesta, String club) {
        this.apostador = apostador;
        this.club = club;
        this.apuesta = apuesta;
    }

    public String getApostador() {
        return apostador;
    }

    public String getClub() {return club; }

    public String getApuesta() {
        return apuesta;
    }

}
