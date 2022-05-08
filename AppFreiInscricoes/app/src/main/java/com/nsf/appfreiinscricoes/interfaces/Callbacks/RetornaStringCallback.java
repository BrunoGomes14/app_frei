package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;

public interface RetornaStringCallback
{
    void onSucess(String sucesso);

    void OnError(ErrorModel erro);
}
