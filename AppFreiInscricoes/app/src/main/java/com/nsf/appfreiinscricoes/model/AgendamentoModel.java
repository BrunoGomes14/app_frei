package com.nsf.appfreiinscricoes.model;

public class AgendamentoModel
{
    String dtAgendamento;
    String hrAgendamento;
    int idUsuario;
    int dsCompareceu;

    public String getDtAgendamento() {
        return dtAgendamento;
    }

    public void setDtAgendamento(String dtAgendamento) {
        this.dtAgendamento = dtAgendamento;
    }

    public String getHrAgendamento() {
        return hrAgendamento;
    }

    public void setHrAgendamento(String hrAgendamento) {
        this.hrAgendamento = hrAgendamento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getDsCompareceu() {
        return dsCompareceu;
    }

    public void setDsCompareceu(int dsCompareceu) {
        this.dsCompareceu = dsCompareceu;
    }
}
