package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.VestibularModel;

public interface RetornaVestibularCallback
{
    void onSucess(VestibularModel vestibular);

    void onError(ErrorModel erro);
}
