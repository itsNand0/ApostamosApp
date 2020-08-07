package com.example.apostamos;

public class ListaSolicitudRetiro {
    private String usuario, celular, email;
    private String retirar;

    public ListaSolicitudRetiro(String usuario, String celular, String retirar, String email) {
        this.usuario = usuario;
        this.celular = celular;
        this.retirar = retirar;
        this.email = email;
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

    public String getEmail() {
        return email;
    }
}
