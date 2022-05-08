package com.nsf.appfreiinscricoes.model;

public class TbUsuario
{
    private int idUsuario;
    private String nmUsuario;
    private String dtNascimento;
    private String dsCpf;
    private String nrRg;
    private String dtEmissaoRg;
    private String dsOrgaoRg;
    private String dsSexo;
    private String dsTelefone = "";
    private String dsEmail = "";
    private int idCodigo;
    private String dsToken;

    public String getDsTelefone() {
        return dsTelefone;
    }

    public void setDsTelefone(String dsTelefone) {
        this.dsTelefone = dsTelefone;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public String getDsToken() {
        return dsToken;
    }

    public void setDsToken(String dsToken) {
        this.dsToken = dsToken;
    }

    public int getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(int idCodigo) {
        this.idCodigo = idCodigo;
    }

    public void setIdUsuario(int IdUsuario) { idUsuario = IdUsuario;}

    public int getIdUsuario() {return idUsuario;}

    public String getDsSexo() {
        return dsSexo;
    }

    public void setDsSexo(String dsSexo) {
        this.dsSexo = dsSexo;
    }

    public String getNrRg() {
        return nrRg;
    }

    public void setNrRg(String nrRg) {
        this.nrRg = nrRg;
    }

    public String getDtEmissaoRg() {
        return dtEmissaoRg;
    }

    public void setDtEmissaoRg(String DtEmissaoRg) {
        this.dtEmissaoRg = DtEmissaoRg;
    }

    public String getDsOrgaoRg() {
        return dsOrgaoRg;
    }

    public void setDsOrgaoRg(String dsOrgaoRg) {
        this.dsOrgaoRg = dsOrgaoRg;
    }
// Getter Methods

    public String getNmUsuario() {
        return nmUsuario;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public String getDsCpf() {
        return dsCpf;
    }

    // Setter Methods

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public void setDsCpf(String dsCpf) {
        this.dsCpf = dsCpf;
    }
}
