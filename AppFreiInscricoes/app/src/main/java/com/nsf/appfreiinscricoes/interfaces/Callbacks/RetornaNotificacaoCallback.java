package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbNotificacao;

import java.util.ArrayList;

public interface RetornaNotificacaoCallback
{
    void onSucess(ArrayList<TbNotificacao> notificacao);

    void onError(ErrorModel erro);
}
