package com.nsf.appfreiinscricoes.model;

public class TbResponsavel
{
    private int idReponsavel;
    private String nmResponsavel = null;
    private String nrTelefone = null;
    private String nrTelefoneSec = null;
    private String dsEmail = null;
    private int blRespSecundario;
    private int idUsuario;
    private String dsParentesco;

    public int isBlRespSecundario() {
        return blRespSecundario;
    }

    public String getDsParentesco() {
        return dsParentesco;
    }

    public void setDsParentesco(String dsParentesco) {
        this.dsParentesco = dsParentesco;
    }
// Getter Methods

    public int getIdReponsavel() {
        return idReponsavel;
    }

    public String getNmResponsavel() {
        return nmResponsavel;
    }

    public String getNrTelefone() {
        return nrTelefone;
    }

    public String getNrTelefoneSec() {
        return nrTelefoneSec;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public int getBlRespSecundario() {
        return blRespSecundario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    // Setter Methods

    public void setIdReponsavel(int idReponsavel) {
        this.idReponsavel = idReponsavel;
    }

    public void setNmResponsavel(String nmResponsavel) {
        this.nmResponsavel = nmResponsavel;
    }

    public void setNrTelefone(String nrTelefone) {
        this.nrTelefone = nrTelefone;
    }

    public void setNrTelefoneSec(String nrTelefoneSec) {
        this.nrTelefoneSec = nrTelefoneSec;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public void setBlRespSecundario(int blRespSecundario) {
        this.blRespSecundario = blRespSecundario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
