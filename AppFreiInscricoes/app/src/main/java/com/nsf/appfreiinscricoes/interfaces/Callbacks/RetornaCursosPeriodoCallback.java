package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.Requests.CursoPeriodoRequest;

import java.util.ArrayList;

public interface RetornaCursosPeriodoCallback
{
    void onSucess(ArrayList<CursoPeriodoRequest> cursoPeriodoRequests);

    void onError(ErrorModel erro);
}
