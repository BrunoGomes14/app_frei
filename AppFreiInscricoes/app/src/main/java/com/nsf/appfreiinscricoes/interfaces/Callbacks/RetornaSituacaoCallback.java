package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;

public interface RetornaSituacaoCallback
{
    void onSucess(int situacao);

    void onError(ErrorModel erro);
}
