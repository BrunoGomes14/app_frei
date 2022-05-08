package com.nsf.appfreiinscricoes.model.Requests;

import com.nsf.appfreiinscricoes.model.TbEscolaridade;
import com.nsf.appfreiinscricoes.model.TbInformacoesExtra;
import com.nsf.appfreiinscricoes.model.TbNascimento;
import com.nsf.appfreiinscricoes.model.TbOpcaoCurso;
import com.nsf.appfreiinscricoes.model.TbResidencia;
import com.nsf.appfreiinscricoes.model.TbResponsavel;
import com.nsf.appfreiinscricoes.model.TbUsuario;

public class InscricaoRequestModel
{
    private TbResidencia residencia;
    private TbResponsavel responsavel;
    private TbResponsavel responsavelSecundario;
    private TbNascimento nascimento;
    private TbOpcaoCurso opcaoCurso;
    private TbEscolaridade escolaridade;
    private TbInformacoesExtra informacoesExtra;
    private TbUsuario usuario;
    public boolean isAlterando;

    public TbResponsavel getResponsavelSecundario() {
        return responsavelSecundario;
    }

    public void setResponsavelSecundario(TbResponsavel responsavelSecundario) {
        this.responsavelSecundario = responsavelSecundario;
    }

    public TbResidencia getResidencia() {
        return residencia;
    }

    public void setResidencia(TbResidencia residencia) {
        this.residencia = residencia;
    }

    public TbResponsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(TbResponsavel responsavel) {
        this.responsavel = responsavel;
    }

    public TbNascimento getNascimento() {
        return nascimento;
    }

    public void setNascimento(TbNascimento nascimento) {
        this.nascimento = nascimento;
    }

    public TbOpcaoCurso getOpcaoCurso() {
        return opcaoCurso;
    }

    public void setOpcaoCurso(TbOpcaoCurso opcaoCurso) {
        this.opcaoCurso = opcaoCurso;
    }

    public TbEscolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(TbEscolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }

    public TbInformacoesExtra getInformacoesExtra() {
        return informacoesExtra;
    }

    public void setInformacoesExtra(TbInformacoesExtra informacoesExtra) {
        this.informacoesExtra = informacoesExtra;
    }

    public TbUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(TbUsuario usuario) {
        this.usuario = usuario;
    }
}
