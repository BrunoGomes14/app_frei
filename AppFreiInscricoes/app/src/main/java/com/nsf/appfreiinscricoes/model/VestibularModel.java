package com.nsf.appfreiinscricoes.model;

public class VestibularModel
{
    public String dtVestibular;
    public String hrInicio;
    public String hrFim;
    public String linkProva;
    public int provaDisponivel;

    public String getDtVestibular() {
        return dtVestibular;
    }

    public void setDtVestibular(String dtVestibular) {
        this.dtVestibular = dtVestibular;
    }

    public String getHrInicio() {
        return hrInicio;
    }

    public void setHrInicio(String hrInicio) {
        this.hrInicio = hrInicio;
    }

    public String getHrFim() {
        return hrFim;
    }

    public void setHrFim(String hrFim) {
        this.hrFim = hrFim;
    }

    public String getLinkProva() {
        return linkProva;
    }

    public void setLinkProva(String linkProva) {
        this.linkProva = linkProva;
    }

    public int getProvaDisponivel() {
        return provaDisponivel;
    }

    public void setProvaDisponivel(int provaDisponivel) {
        this.provaDisponivel = provaDisponivel;
    }
}
