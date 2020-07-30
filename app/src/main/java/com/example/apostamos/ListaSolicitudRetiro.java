package com.example.apostamos;

public class ListaSolicitudRetiro {
    private String usuario, celular;
    private String retirar;

    public ListaSolicitudRetiro(String usuario, String celular, String retirar) {
        this.usuario = usuario;
        this.celular = celular;
        this.retirar = retirar;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getCelular() {
        return celular;
    }

    public String getRetirar() {
        return retirar;
    }
}
