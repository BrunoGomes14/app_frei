package com.nsf.appfreiinscricoes.model.Requests;

import com.nsf.appfreiinscricoes.model.TbLogin;
import com.nsf.appfreiinscricoes.model.UsuarioCadastro;

import java.io.Serializable;

public class LoginRequestModel implements Serializable
{
    private TbLogin Login;
    private UsuarioCadastro Usuario;

    public TbLogin getLogin() { return Login; }
    public UsuarioCadastro getUsuario() { return Usuario; }

    public void setLogin(TbLogin login) { this.Login = login; }
    public void setUsuario(UsuarioCadastro usuario) { this.Usuario = usuario; }

}
