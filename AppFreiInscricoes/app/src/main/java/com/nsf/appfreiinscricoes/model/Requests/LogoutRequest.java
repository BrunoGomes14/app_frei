package com.nsf.appfreiinscricoes.model.Requests;

public class LogoutRequest
{
    private int idUsuario;
    private String dsTokenFirebase;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDsTokenFirebase() {
        return dsTokenFirebase;
    }

    public void setDsTokenFirebase(String dsTokenFirebase) {
        this.dsTokenFirebase = dsTokenFirebase;
    }
}
