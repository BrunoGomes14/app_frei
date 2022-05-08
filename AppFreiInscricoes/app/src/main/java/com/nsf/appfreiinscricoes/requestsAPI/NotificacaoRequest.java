package com.nsf.appfreiinscricoes.requestsAPI;

import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaNotificacaoCallback;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbNotificacao;
import com.nsf.appfreiinscricoes.ultil.RetrofitConfig;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacaoRequest
{
    RotasAPI service;

    public void retornaNotificacoes(int idUsuario, RetornaNotificacaoCallback callback, String sToken)
    {
        service = RetrofitConfig.acao();

        service.RetornaNotificacoesUsuario(idUsuario, sToken).enqueue(new Callback<ArrayList<TbNotificacao>>() {
            @Override
            public void onResponse(Call<ArrayList<TbNotificacao>> call, Response<ArrayList<TbNotificacao>> response)
            {
                try
                {
                    if(response.isSuccessful())
                    {
                        callback.onSucess(response.body());
                    }
                    else
                    {
                        ErrorModel erro = new ErrorModel();
                        JSONObject jErro = new JSONObject(response.errorBody().string());

                        erro.setCodigoErro(jErro.getInt("codigoErro"));
                        erro.setMensagemErro(jErro.getString("mensagemErro"));

                        callback.onError(erro);
                    }
                }
                catch (Exception err)
                {
                    ErrorModel erro = new ErrorModel();
                    erro.setCodigoErro(135);
                    erro.setMensagemErro("Erro inesperado, tente novamente");

                    callback.onError(erro);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TbNotificacao>> call, Throwable t)
            {
                ErrorModel erro = new ErrorModel();
                erro.setCodigoErro(500);
                erro.setMensagemErro("Erro de conexão, tente novamente");

                callback.onError(erro);
            }
        });
    }
}
