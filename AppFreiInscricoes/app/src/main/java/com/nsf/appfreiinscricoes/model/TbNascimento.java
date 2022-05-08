package com.nsf.appfreiinscricoes.model;

public class TbNascimento
{
    private int idNascimento;
    private String dsCidade = null;
    private String dsUf = null;
    private String dsNascionalidade = null;


    // Getter Methods

    public int getIdNascimento() {
        return idNascimento;
    }

    public String getDsCidade() {
        return dsCidade;
    }

    public String getDsUf() {
        return dsUf;
    }

    public String getDsNascionalidade() {
        return dsNascionalidade;
    }

    // Setter Methods

    public void setIdNascimento(int idNascimento) {
        this.idNascimento = idNascimento;
    }

    public void setDsCidade(String dsCidade) {
        this.dsCidade = dsCidade;
    }

    public void setDsUf(String dsUf) {
        this.dsUf = dsUf;
    }

    public void setDsNascionalidade(String dsNascionalidade) {
        this.dsNascionalidade = dsNascionalidade;
    }
}
