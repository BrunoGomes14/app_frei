package com.nsf.appfreiinscricoes.model;

import com.google.gson.annotations.SerializedName;

public class ErrorModel
{
    @SerializedName("codigoErro")
    private int codigoErro;

    @SerializedName("mensagemErro")
    private String mensagemErro;

    public int getCodigoErro() {
        return codigoErro;
    }

    public void setCodigoErro(int codigoErro) {
        this.codigoErro = codigoErro;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
}
