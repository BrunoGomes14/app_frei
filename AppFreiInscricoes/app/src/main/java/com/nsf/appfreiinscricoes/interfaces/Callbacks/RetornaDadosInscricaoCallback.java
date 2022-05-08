package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.Requests.InscricaoRequestModel;

public interface RetornaDadosInscricaoCallback
{
    void onSuccess(InscricaoRequestModel inscricao);

    void onError(ErrorModel error);
}
