package com.nsf.appfreiinscricoes.model;

public class ResumoInscricaoModel
{
    public int idUsuario;
    public int dsCodigoInscricao ;
    public String nmUsuario ;
    public String dsCpf ;
    public String dsCursoPrimeiro;
    public String dsTurnoPrimeiro ;
    public String dsCursoSegundo ;
    public String dsTurnoSegundo ;
    public int dsConfirmaPgto ;
    public String dsAgendamentoData;
    public String dsAgendamentoHora;
    public int dsPossuiAgendamento;
    public int dsCompareceuAgendamento;
    public String mensagem = "";

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getDsCodigoInscricao() {
        return dsCodigoInscricao;
    }

    public void setDsCodigoInscricao(int dsCodigoInscricao) {
        this.dsCodigoInscricao = dsCodigoInscricao;
    }

    public String getNmUsuario() {
        return nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }

    public String getDsCpf() {
        return dsCpf;
    }

    public void setDsCpf(String dsCpf) {
        this.dsCpf = dsCpf;
    }

    public String getDsCursoPrimeiro() {
        return dsCursoPrimeiro;
    }

    public void setDsCursoPrimeiro(String dsCursoPrimeiro) {
        this.dsCursoPrimeiro = dsCursoPrimeiro;
    }

    public String getDsTurnoPrimeiro() {
        return dsTurnoPrimeiro;
    }

    public void setDsTurnoPrimeiro(String dsTurnoPrimeiro) {
        this.dsTurnoPrimeiro = dsTurnoPrimeiro;
    }

    public String getDsCursoSegundo() {
        return dsCursoSegundo;
    }

    public void setDsCursoSegundo(String dsCursoSegundo) {
        this.dsCursoSegundo = dsCursoSegundo;
    }

    public String getDsTurnoSegundo() {
        return dsTurnoSegundo;
    }

    public void setDsTurnoSegundo(String dsTurnoSegundo) {
        this.dsTurnoSegundo = dsTurnoSegundo;
    }

    public int getDsConfirmaPgto() {
        return dsConfirmaPgto;
    }

    public void setDsConfirmaPgto(int dsConfirmaPgto) {
        this.dsConfirmaPgto = dsConfirmaPgto;
    }
}
