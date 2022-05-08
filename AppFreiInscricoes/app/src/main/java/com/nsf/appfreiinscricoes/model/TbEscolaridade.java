package com.nsf.appfreiinscricoes.model;

public class TbEscolaridade
{
    private int idEscolaridade;
    private String dsEscolaridade = null;
    private String tpEscola = null;
    private String dsEscola = null;


    // Getter Methods

    public int getIdEscolaridade() {
        return idEscolaridade;
    }

    public String getDsEscolaridade() {
        return dsEscolaridade;
    }

    public String getTpEscola() {
        return tpEscola;
    }

    public String getDsEscola() {
        return dsEscola;
    }

    // Setter Methods

    public void setIdEscolaridade(int idEscolaridade) {
        this.idEscolaridade = idEscolaridade;
    }

    public void setDsEscolaridade(String dsEscolaridade) {
        this.dsEscolaridade = dsEscolaridade;
    }

    public void setTpEscola(String tpEscola) {
        this.tpEscola = tpEscola;
    }

    public void setDsEscola(String dsEscola) {
        this.dsEscola = dsEscola;
    }
}
