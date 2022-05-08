package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbCodigo;

public interface RetornaInscricaoCallback
{
    void onSucess(TbCodigo codigo);

    void onError(ErrorModel erro);
}
