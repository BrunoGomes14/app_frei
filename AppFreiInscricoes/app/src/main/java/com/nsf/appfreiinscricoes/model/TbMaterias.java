package com.nsf.appfreiinscricoes.model;

import java.io.Serializable;

public class TbMaterias implements Serializable
{
    public int idMateria;
    public String nmMateria ;

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNmMateria() {
        return nmMateria;
    }

    public void setNmMateria(String nmMateria) {
        this.nmMateria = nmMateria;
    }
}
