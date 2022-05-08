package com.nsf.appfreiinscricoes.model;

public class TbPeridoInscricao
{
    public int idPeriodo;
    public int dsAno;
    public String dtInicio;
    public String dtFim;
    public String messageB;
    public String messageA;

    public String getMessageB() {
        return messageB;
    }

    public void setMessageB(String messageB) {
        this.messageB = messageB;
    }

    public String getMessageA() {
        return messageA;
    }

    public void setMessageA(String messageA) {
        this.messageA = messageA;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getDsAno() {
        return dsAno;
    }

    public void setDsAno(int dsAno) {
        this.dsAno = dsAno;
    }

    public String getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(String dtInicio) {
        this.dtInicio = dtInicio;
    }

    public String getDtFim() {
        return dtFim;
    }

    public void setDtFim(String dtFim) {
        this.dtFim = dtFim;
    }
}
