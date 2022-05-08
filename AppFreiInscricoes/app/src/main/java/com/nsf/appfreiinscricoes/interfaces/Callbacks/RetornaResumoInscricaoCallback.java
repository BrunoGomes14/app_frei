package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.ResumoInscricaoModel;

public interface RetornaResumoInscricaoCallback
{
    void onSucess(ResumoInscricaoModel inscricao);

    void OnError(ErrorModel erro);
}
