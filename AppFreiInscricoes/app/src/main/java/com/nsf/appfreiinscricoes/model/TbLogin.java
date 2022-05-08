package com.nsf.appfreiinscricoes.model;

import java.io.Serializable;

public class TbLogin implements Serializable
{
    private String dsEmail;
    private String dsSenha;
    private String Token;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
// Getter Methods

    public String getDsEmail() {
        return dsEmail;
    }

    public String getDsSenha() {
        return dsSenha;
    }


    // Setter Methods

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }


}

