package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import androidx.annotation.NonNull;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.Requests.TbCursoRequest;

import java.util.ArrayList;

public interface RetornaCursoCallbacks
{
    void onSuccess(@NonNull ArrayList<TbCursoRequest> cursos);

    void onError(ErrorModel error);
}
