package com.nsf.appfreiinscricoes.model;

public class TbOpcaoCurso
{
    private int idCursoEscolhido;
    private int idCursoPrimeiraOpcao;
    private String dsTurnoPrimeiraOpcao = null;
    private int idCursoSegundaOpcao;
    private String dsTurnoSegundaOpcao = null;


    // Getter Methods

    public int getIdCursoEscolhido() {
        return idCursoEscolhido;
    }

    public int getIdCursoPrimeiraOpcao() {
        return idCursoPrimeiraOpcao;
    }

    public String getDsTurnoPrimeiraOpcao() {
        return dsTurnoPrimeiraOpcao;
    }

    public int getIdCursoSegundaOpcao() {
        return idCursoSegundaOpcao;
    }

    public String getDsTurnoSegundaOpcao() {
        return dsTurnoSegundaOpcao;
    }

    // Setter Methods

    public void setIdCursoEscolhido(int idCursoEscolhido) {
        this.idCursoEscolhido = idCursoEscolhido;
    }

    public void setIdCursoPrimeiraOpcao(int idCursoPrimeiraOpcao) {
        this.idCursoPrimeiraOpcao = idCursoPrimeiraOpcao;
    }

    public void setDsTurnoPrimeiraOpcao(String dsTurnoPrimeiraOpcao) {
        this.dsTurnoPrimeiraOpcao = dsTurnoPrimeiraOpcao;
    }

    public void setIdCursoSegundaOpcao(int idCursoSegundaOpcao) {
        this.idCursoSegundaOpcao = idCursoSegundaOpcao;
    }

    public void setDsTurnoSegundaOpcao(String dsTurnoSegundaOpcao) {
        this.dsTurnoSegundaOpcao = dsTurnoSegundaOpcao;
    }
}
