package com.nsf.appfreiinscricoes.requestsAPI;

import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaFaqCallback;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbFaq;
import com.nsf.appfreiinscricoes.ultil.RetrofitConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqRequest
{
    RotasAPI service;
    ErrorModel errorModel;

    public void ListaFaqs(RetornaFaqCallback callback, String sToken)
    {
        service = RetrofitConfig.acao();
        service.RetornaFaqs(sToken).enqueue(new Callback<ArrayList<TbFaq>>() {
            @Override
            public void onResponse(Call<ArrayList<TbFaq>> call, Response<ArrayList<TbFaq>> response)
            {
                try
                {
                    if (response.isSuccessful())
                    {
                        callback.onSuccess(response.body());
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
            public void onFailure(Call<ArrayList<TbFaq>> call, Throwable t)
            {
                errorModel = new ErrorModel();
                errorModel.setMensagemErro("Erro de conexão, tente novamente");
                errorModel.setCodigoErro(500);

                callback.onError(errorModel);
            }
        });
    }
}
