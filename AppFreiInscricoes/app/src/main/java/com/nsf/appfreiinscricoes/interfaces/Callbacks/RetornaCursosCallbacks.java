package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import androidx.annotation.NonNull;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbCursos;

import java.util.List;

public interface RetornaCursosCallbacks
{
    void onSuccess(@NonNull List<TbCursos> cursos);

    void onError(ErrorModel error);
}
