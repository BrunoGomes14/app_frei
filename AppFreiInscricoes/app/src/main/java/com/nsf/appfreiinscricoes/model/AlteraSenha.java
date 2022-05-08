package com.nsf.appfreiinscricoes.model;

public class AlteraSenha
{
    private int idUsuario;
    private String dsNovaSenha;
    private String cdVerificador;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDsNovaSenha() {
        return dsNovaSenha;
    }

    public void setDsNovaSenha(String dsNovaSenha) {
        this.dsNovaSenha = dsNovaSenha;
    }

    public String getCdVerificador() {
        return cdVerificador;
    }

    public void setCdVerificador(String cdVerificador) {
        this.cdVerificador = cdVerificador;
    }
}
