package com.nsf.appfreiinscricoes.model;

import java.io.Serializable;

public class TbDetalhesCurso implements Serializable
{
    public int idDetalhesCurso;
    public String dsVisaoGeral;
    public String dsCargaHoraria;
    public String dsDuracao;
    public String dsMercado;
    public String nrIdadeMinima;
    public String dsEscolaridadeMinima;
    public String dsContribuicaoMensal;
    public String nrIdadeMaxima;

    public String getDsEscolaridadeMinima() {
        return dsEscolaridadeMinima;
    }

    public void setDsEscolaridadeMinima(String dsEscolaridadeMinima) {
        this.dsEscolaridadeMinima = dsEscolaridadeMinima;
    }

    public String getDsMercado() {
        return dsMercado;
    }

    public void setDsMercado(String dsMercado) {
        this.dsMercado = dsMercado;
    }

    public String getNrIdadeMinima() {
        return nrIdadeMinima;
    }

    public void setNrIdadeMinima(String nrIdadeMinima) {
        this.nrIdadeMinima = nrIdadeMinima;
    }

    public String getLinkVideoApresentacao() {
        return linkVideoApresentacao;
    }

    public void setLinkVideoApresentacao(String linkVideoApresentacao) {
        this.linkVideoApresentacao = linkVideoApresentacao;
    }

    public String linkVideoApresentacao;

    public String getDsDuracao() {
        return dsDuracao;
    }

    public void setDsDuracao(String dsDuracao) {
        this.dsDuracao = dsDuracao;
    }

    public int getIdDetalhesCurso() {
        return idDetalhesCurso;
    }

    public void setIdDetalhesCurso(int idDetalhesCurso) {
        this.idDetalhesCurso = idDetalhesCurso;
    }

    public String getDsVisaoGeral() {
        return dsVisaoGeral;
    }

    public void setDsVisaoGeral(String dsVisaoGeral) {
        this.dsVisaoGeral = dsVisaoGeral;
    }

    public String getDsCargaHoraria() {
        return dsCargaHoraria;
    }

    public void setDsCargaHoraria(String dsCargaHoraria) {
        this.dsCargaHoraria = dsCargaHoraria;
    }
}
