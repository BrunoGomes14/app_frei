package com.nsf.appfreiinscricoes.model;

public class TbInformacoesExtra {
    private int idInformacaoExtra;
    private String dsComoConheceu = null;
    private float dsRenda;
    private int qtdPessoasResidentesCasa;
    private int qtdPessoasTrabalhando;


    // Getter Methods

    public int getIdInformacaoExtra() {
        return idInformacaoExtra;
    }

    public String getDsComoConheceu() {
        return dsComoConheceu;
    }

    public float getDsRenda() {
        return dsRenda;
    }

    public int getQtdPessoasResidentesCasa() {
        return qtdPessoasResidentesCasa;
    }

    public int getQtdPessoasTrabalhando() {
        return qtdPessoasTrabalhando;
    }

    // Setter Methods

    public void setIdInformacaoExtra(int idInformacaoExtra) {
        this.idInformacaoExtra = idInformacaoExtra;
    }

    public void setDsComoConheceu(String dsComoConheceu) {
        this.dsComoConheceu = dsComoConheceu;
    }

    public void setDsRenda(Float dsRenda) {
        this.dsRenda = dsRenda;
    }

    public void setQtdPessoasResidentesCasa(int qtdPessoasResidentesCasa) {
        this.qtdPessoasResidentesCasa = qtdPessoasResidentesCasa;
    }

    public void setQtdPessoasTrabalhando(int qtdPessoasTrabalhando) {
        this.qtdPessoasTrabalhando = qtdPessoasTrabalhando;
    }
}
