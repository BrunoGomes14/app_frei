package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbFaq;

import java.util.ArrayList;

public interface RetornaFaqCallback
{
    void onSuccess(ArrayList<TbFaq> faqs);

    void onError(ErrorModel error);
}
