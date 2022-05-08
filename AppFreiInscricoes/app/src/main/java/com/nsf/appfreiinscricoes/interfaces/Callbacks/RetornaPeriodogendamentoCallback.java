package com.nsf.appfreiinscricoes.interfaces.Callbacks;

import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.PeriodoAgendamentoModel;

public interface RetornaPeriodogendamentoCallback
{
    void onSucess(PeriodoAgendamentoModel periodoAgendamento);

    void onError(ErrorModel erro);
}
