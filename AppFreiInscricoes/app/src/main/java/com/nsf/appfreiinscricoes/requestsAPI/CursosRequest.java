package com.nsf.appfreiinscricoes.requestsAPI;

import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaCursoCallbacks;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaCursosPeriodoCallback;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.Requests.CursoPeriodoRequest;
import com.nsf.appfreiinscricoes.model.Requests.TbCursoRequest;
import com.nsf.appfreiinscricoes.model.TbCursos;
import com.nsf.appfreiinscricoes.ultil.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CursosRequest
{
    RotasAPI service;
    List<TbCursos> listCursos;
    ErrorModel errorModel;

    public void ListaCursos(RetornaCursoCallbacks callbacks, String token)
    {
        service = RetrofitConfig.acao();
        service.RetornaCursos(token).enqueue(new Callback<ArrayList<TbCursoRequest>>() {
            @Override
            public void onResponse(Call<ArrayList<TbCursoRequest>> call, Response<ArrayList<TbCursoRequest>> response)
            {
                try
                {
                    if(response.isSuccessful())
                    {
                        callbacks.onSuccess(response.body());
                    }
                    else
                    {
                        errorModel = new ErrorModel();
                        errorModel.setMensagemErro("Erro inesperado");
                        errorModel.setCodigoErro(500);

                        callbacks.onError(errorModel);
                    }
                }
                catch (Exception err)
                {
                    errorModel = new ErrorModel();
                    errorModel.setMensagemErro("Erro inesperado");
                    errorModel.setCodigoErro(500);

                    callbacks.onError(errorModel);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TbCursoRequest>> call, Throwable t)
            {
                errorModel = new ErrorModel();
                errorModel.setMensagemErro("Erro de conexão, tente novamente");
                errorModel.setCodigoErro(500);

                callbacks.onError(errorModel);
            }
        });
    }

    public void ListaCursoPeriodo(RetornaCursosPeriodoCallback callback, String sToken)
    {
        service = RetrofitConfig.acao();
        service.RetornaCursosPeriodo(sToken).enqueue(new Callback<ArrayList<CursoPeriodoRequest>>() {
            @Override
            public void onResponse(Call<ArrayList<CursoPeriodoRequest>> call, Response<ArrayList<CursoPeriodoRequest>> response)
            {
                try
                {
                    if(response.isSuccessful())
                    {
                        callback.onSucess(response.body());
                    }
                    else
                    {
                        errorModel = new ErrorModel();
                        errorModel.setMensagemErro("Erro inesperado");
                        errorModel.setCodigoErro(500);

                        callback.onError(errorModel);
                    }
                }
                catch (Exception ex)
                {
                    errorModel = new ErrorModel();
                    errorModel.setMensagemErro("Erro inesperado");
                    errorModel.setCodigoErro(500);

                    callback.onError(errorModel);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CursoPeriodoRequest>> call, Throwable t)
            {
                errorModel = new ErrorModel();
                errorModel.setMensagemErro("Erro de conexão, tente novamente");
                errorModel.setCodigoErro(500);

                callback.onError(errorModel);
            }
        });
    }
}
