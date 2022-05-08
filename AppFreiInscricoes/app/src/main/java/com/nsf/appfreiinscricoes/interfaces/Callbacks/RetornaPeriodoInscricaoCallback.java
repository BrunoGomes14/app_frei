package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbPeridoInscricao;

public interface RetornaPeriodoInscricaoCallback
{
    void onSuccess(TbPeridoInscricao periodo);

    void onError(ErrorModel error);
}
