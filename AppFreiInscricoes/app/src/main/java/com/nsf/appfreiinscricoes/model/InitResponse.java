package com.nsf.appfreiinscricoes.model;

import java.util.ArrayList;

public class InitResponse
{
    int situacao;
    TbPeridoInscricao periodo;
    Boolean notificacoesPendentes;
    public String linkPalesta;

    public int getSituacao() {
        return situacao;
    }

    public void setiSituacao(int iSituacao) {
        this.situacao = iSituacao;
    }

    public TbPeridoInscricao getPeriodo() {
        return periodo;
    }

    public void setPeriodo(TbPeridoInscricao periodo) {
        this.periodo = periodo;
    }

    public Boolean getNotificacoes() {
        return notificacoesPendentes;
    }

    public void setNotificacoes(Boolean notificacoes) {
        this.notificacoesPendentes = notificacoes;
    }
}
