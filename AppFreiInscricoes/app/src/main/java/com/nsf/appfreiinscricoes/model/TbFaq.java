package com.nsf.appfreiinscricoes.model;

public class TbFaq
{
    public String dsPergunta;

    public String getDsPergunta() {
        return dsPergunta;
    }

    public void setDsPergunta(String dsPergunta) {
        this.dsPergunta = dsPergunta;
    }

    public String dsResposta;
    boolean isExpanded = false;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getDsResposta() {
        return dsResposta;
    }

    public void setDsResposta(String dsResposta) {
        this.dsResposta = dsResposta;
    }


}
