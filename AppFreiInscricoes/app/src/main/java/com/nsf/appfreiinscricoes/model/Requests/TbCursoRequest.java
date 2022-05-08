package com.nsf.appfreiinscricoes.model.Requests;

import com.nsf.appfreiinscricoes.model.TbCursos;
import com.nsf.appfreiinscricoes.model.TbDetalhesCurso;
import com.nsf.appfreiinscricoes.model.TbMaterias;
import com.nsf.appfreiinscricoes.model.TbPeriodos;
import com.nsf.appfreiinscricoes.model.TbTipoCurso;

import java.io.Serializable;
import java.util.ArrayList;

public class TbCursoRequest implements Serializable
{
    public TbCursos curso;
    public TbTipoCurso tpCurso;
    public ArrayList<TbPeriodos> periodosCurso;
    public TbDetalhesCurso detalhesCurso;
    public ArrayList<TbMaterias> materiasCurso;

    public TbCursos getCurso() {
        return curso;
    }

    public void setCurso(TbCursos curso) {
        this.curso = curso;
    }

    public TbTipoCurso getTpCurso() {
        return tpCurso;
    }

    public void setTpCurso(TbTipoCurso tpCurso) {
        this.tpCurso = tpCurso;
    }

    public ArrayList<TbPeriodos> getPeriodosCurso() {
        return periodosCurso;
    }

    public void setPeriodosCurso(ArrayList<TbPeriodos> periodosCurso) {
        this.periodosCurso = periodosCurso;
    }

    public TbDetalhesCurso getDetalhesCurso() {
        return detalhesCurso;
    }

    public void setDetalhesCurso(TbDetalhesCurso detalhesCurso) {
        this.detalhesCurso = detalhesCurso;
    }

    public ArrayList<TbMaterias> getMateriasCurso() {
        return materiasCurso;
    }

    public void setMateriasCurso(ArrayList<TbMaterias> materiasCurso)
    {
        this.materiasCurso = materiasCurso;
    }
}
