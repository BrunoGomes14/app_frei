package com.nsf.appfreiinscricoes.model;

import java.io.Serializable;

public class UsuarioCadastro implements Serializable {

    private String nmUsuario;
    private String dtNascimento;
    private String dsCpf;

    public String getNmUsuario() {
        return nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getDsCpf() {
        return dsCpf;
    }

    public void setDsCpf(String dsCpf) {
        this.dsCpf = dsCpf;
    }
}
