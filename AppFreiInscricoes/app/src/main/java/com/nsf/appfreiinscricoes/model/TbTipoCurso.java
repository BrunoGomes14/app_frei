package com.nsf.appfreiinscricoes.model;

import java.io.Serializable;

public class TbTipoCurso implements Serializable
{
    public int idTipoCurso;
    public String tpCurso;

    public int getIdTipoCurso() {
        return idTipoCurso;
    }

    public void setIdTipoCurso(int idTipoCurso) {
        this.idTipoCurso = idTipoCurso;
    }

    public String getTpCurso() {
        return tpCurso;
    }

    public void setTpCurso(String tpCurso) {
        this.tpCurso = tpCurso;
    }
}
