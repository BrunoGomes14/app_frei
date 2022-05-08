package com.nsf.appfreiinscricoes.model.Requests;

import com.nsf.appfreiinscricoes.model.TbCursos;
import com.nsf.appfreiinscricoes.model.TbPeriodos;

import java.util.ArrayList;

public class CursoPeriodoRequest
{
    TbCursos curso;
    ArrayList<TbPeriodos> periodos;
    public String linkVideoApresentacao;

    public TbCursos getCurso() {
        return curso;
    }

    public void setCurso(TbCursos curso) {
        this.curso = curso;
    }

    public ArrayList<TbPeriodos> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(ArrayList<TbPeriodos> periodos) {
        this.periodos = periodos;
    }
}
