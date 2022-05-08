package com.nsf.appfreiinscricoes.model;

public class TbResidencia
{

    private int idResidencia;
    private String nrCep = null;
    private String dsEndereco = null;
    private String nrResidencia = null;
    private String dsComplemento = null;
    private String dsBairro = null;
    private String dsUf = null;
    private String dsCidade = null;


    // Getter Methods

    public int getIdResidencia() {
        return idResidencia;
    }

    public String getNrCep() {
        return nrCep;
    }

    public String getDsEndereco() {
        return dsEndereco;
    }

    public String getNrResidencia() {
        return nrResidencia;
    }

    public String getDsComplemento() {
        return dsComplemento;
    }

    public String getDsBairro() {
        return dsBairro;
    }

    public String getDsUf() {
        return dsUf;
    }

    public String getDsCidade() {
        return dsCidade;
    }

    // Setter Methods

    public void setIdResidencia(int idResidencia) {
        this.idResidencia = idResidencia;
    }

    public void setNrCep(String nrCep) {
        this.nrCep = nrCep;
    }

    public void setDsEndereco(String dsEndereco) {
        this.dsEndereco = dsEndereco;
    }

    public void setNrResidencia(String nrResidencia) {
        this.nrResidencia = nrResidencia;
    }

    public void setDsComplemento(String dsComplemento) {
        this.dsComplemento = dsComplemento;
    }

    public void setDsBairro(String dsBairro) {
        this.dsBairro = dsBairro;
    }

    public void setDsUf(String dsUf) {
        this.dsUf = dsUf;
    }

    public void setDsCidade(String dsCidade) {
        this.dsCidade = dsCidade;
    }
}