package com.nsf.appfreiinscricoes.requestsAPI;

import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaPeriodogendamentoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.AgendamentoModel;
import com.nsf.appfreiinscricoes.model.AgendamentoResponse;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.PeriodoAgendamentoModel;
import com.nsf.appfreiinscricoes.ultil.RetrofitConfig;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendamentoRequest
{
    RotasAPI service;
    ErrorModel errorModel;

    public void RetornaPeriodoAgendamento(String sToken, RetornaPeriodogendamentoCallback callback)
    {
        service = RetrofitConfig.acao();
        service.RetornaPeriodoAgendamento(sToken).enqueue(new Callback<PeriodoAgendamentoModel>() {
            @Override
            public void onResponse(Call<PeriodoAgendamentoModel> call, Response<PeriodoAgendamentoModel> response)
            {
                try
                {
                    if (response.isSuccessful())
                    {
                        callback.onSucess(response.body());
                    }
                    else
                    {
                        errorModel = new ErrorModel();
                        JSONObject jErro = new JSONObject(response.errorBody().string());

                        errorModel.setCodigoErro(jErro.getInt("codigoErro"));
                        errorModel.setMensagemErro(jErro.getString("mensagemErro"));

                        callback.onError(errorModel);
                    }
                }
                catch (Exception ex)
                {
                    errorModel = new ErrorModel();
                    errorModel.setMensagemErro("Erro inesperado, tente novamente.");
                    errorModel.setCodigoErro(500);
                }
            }

            @Override
            public void onFailure(Call<PeriodoAgendamentoModel> call, Throwable t)
            {
                errorModel = new ErrorModel();
                errorModel.setMensagemErro("Erro de conexão, verifique sua internet.");
                errorModel.setCodigoErro(500);
            }
        });
    }

    public void AgendaPagamento(RetornaStringCallback callback, String sToken, AgendamentoModel agendamento)
    {
        GsonBuilder gson = new GsonBuilder();
        JsonElement jEnvio = gson.create().toJsonTree(agendamento);

        service = RetrofitConfig.acao();
        service.AgendaPagamento(jEnvio, sToken).enqueue(new Callback<AgendamentoResponse>() {
            @Override
            public void onResponse(Call<AgendamentoResponse> call, Response<AgendamentoResponse> response)
            {
                try
                {
                    if (response.isSuccessful())
                    {
                        callback.onSucess(response.body().mensagem);
                    }
                    else
                    {
                        errorModel = new ErrorModel();
                        JSONObject jErro = new JSONObject(response.errorBody().string());

                        errorModel.setCodigoErro(jErro.getInt("codigoErro"));
                        errorModel.setMensagemErro(jErro.getString("mensagemErro"));

                        callback.OnError(errorModel);
                    }
                }
                catch (Exception ex)
                {
                    errorModel = new ErrorModel();
                    errorModel.setCodigoErro(500);
                    errorModel.setMensagemErro("Erro inesperado, tente novamente");

                    callback.OnError(errorModel);
                }
            }

            @Override
            public void onFailure(Call<AgendamentoResponse> call, Throwable t)
            {
                errorModel = new ErrorModel();
                errorModel.setCodigoErro(500);
                errorModel.setMensagemErro("Erro de conexão, tente novamente");

                callback.OnError(errorModel);
            }
        });
    }

    public void AlteraAgendamento(RetornaStringCallback callback, String sToken, AgendamentoModel agendamento)
    {
        GsonBuilder gson = new GsonBuilder();
        JsonElement jEnvio = gson.create().toJsonTree(agendamento);

        service = RetrofitConfig.acao();
        service.AlteraAgendamento(jEnvio, sToken).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                try
                {
                    if (response.isSuccessful())
                    {
                        callback.onSucess("ok");
                    }
                    else
                    {
                        errorModel = new ErrorModel();
                        JSONObject jErro = new JSONObject(response.errorBody().string());

                        errorModel.setCodigoErro(jErro.getInt("codigoErro"));
                        errorModel.setMensagemErro(jErro.getString("mensagemErro"));

                        callback.OnError(errorModel);
                    }
                }
                catch (Exception err)
                {
                    errorModel = new ErrorModel();
                    errorModel.setCodigoErro(500);
                    errorModel.setMensagemErro("Erro inesperado, tente novamente");

                    callback.OnError(errorModel);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                errorModel = new ErrorModel();
                errorModel.setCodigoErro(500);
                errorModel.setMensagemErro("Erro de conexão, tente novamente");

                callback.OnError(errorModel);
            }
        });
    }
}
