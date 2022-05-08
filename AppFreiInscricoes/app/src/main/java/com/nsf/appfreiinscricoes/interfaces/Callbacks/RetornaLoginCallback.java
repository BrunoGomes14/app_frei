package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbUsuario;

public interface RetornaLoginCallback
{
    void onSucess(TbUsuario codigo);

    void OnError(ErrorModel erro);
}
