package com.nsf.appfreiinscricoes.model;

import java.io.Serializable;

public class TbPeriodos implements Serializable
{
    public int idPeriodo;
    public String dsPeriodo;
    public String hrEntrada;
    public String hrSaida;
    public boolean mostraHorario;

    public String getHrEntrada() {
        return hrEntrada;
    }

    public void setHrEntrada(String hrEntrada) {
        this.hrEntrada = hrEntrada;
    }

    public String getHrSaida() {
        return hrSaida;
    }

    public void setHrSaida(String hrSaida) {
        this.hrSaida = hrSaida;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getDsPeriodo() {
        return dsPeriodo;
    }

    public void setDsPeriodo(String dsPeriodo) {
        this.dsPeriodo = dsPeriodo;
    }
}
