package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.InitResponse;
import com.nsf.appfreiinscricoes.model.TbFaq;

import java.util.ArrayList;

public interface RetornaInitCallback
{
    void onSuccess(InitResponse response);

    void onError(ErrorModel error);
}
