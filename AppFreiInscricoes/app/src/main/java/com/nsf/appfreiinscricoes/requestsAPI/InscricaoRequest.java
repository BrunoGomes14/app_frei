package com.nsf.appfreiinscricoes.requestsAPI;

import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaDadosInscricaoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaInscricaoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaResumoInscricaoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaSituacaoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.Requests.InscricaoRequestModel;
import com.nsf.appfreiinscricoes.model.ResumoInscricaoModel;
import com.nsf.appfreiinscricoes.model.TbCodigo;
import com.nsf.appfreiinscricoes.ultil.RetrofitConfig;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscricaoRequest
{
    RotasAPI service;

    public void EnviaInscricao(RetornaInscricaoCallback callback, InscricaoRequestModel inscricao, String sToken)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonElement jsonInscricao = gsonBuilder.create().toJsonTree(inscricao);

        service = RetrofitConfig.acao();
        service.EnviaInscricao(jsonInscricao, sToken).enqueue(new Callback<TbCodigo>() {
            @Override
            public void onResponse(Call<TbCodigo> call, Response<TbCodigo> response)
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
            public void onFailure(Call<TbCodigo> call, Throwable t)
            {
                ErrorModel erro = new ErrorModel();
                erro.setCodigoErro(135);
                erro.setMensagemErro("Erro de conexão, tente novamente");

                callback.onError(erro);
            }
        });
    }

    public void ResumoInscricao(RetornaResumoInscricaoCallback callback, String sToken, int codInscricao)
    {
        service = RetrofitConfig.acao();
        service.RetornaResumoInscricao(codInscricao, sToken).enqueue(new Callback<ResumoInscricaoModel>() {
            @Override
            public void onResponse(Call<ResumoInscricaoModel> call, Response<ResumoInscricaoModel> response)
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

                        callback.OnError(erro);
                    }
                }
                catch (Exception err)
                {
                    ErrorModel erro = new ErrorModel();
                    erro.setCodigoErro(500);
                    erro.setMensagemErro(err.getMessage());
                    callback.OnError(erro);
                }

            }

            @Override
            public void onFailure(Call<ResumoInscricaoModel> call, Throwable t)
            {
                ErrorModel erro = new ErrorModel();
                erro.setCodigoErro(500);
                erro.setMensagemErro("Erro de conexão, tente novamente");
                callback.OnError(erro);
            }
        });
    }

    public void retornaCodigoInscricao(int cod, RetornaInscricaoCallback callback, String sToken)
    {
        service = RetrofitConfig.acao();
        service.RetornaInscricao(cod, sToken).enqueue(new Callback<TbCodigo>() {
            @Override
            public void onResponse(Call<TbCodigo> call, Response<TbCodigo> response) {
                try
                {
                    if (response.isSuccessful())
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
                    erro.setCodigoErro(500);
                    erro.setMensagemErro("Erro inesperado, tente novamente");
                    callback.onError(erro);
                }
            }

            @Override
            public void onFailure(Call<TbCodigo> call, Throwable t) {
                ErrorModel erro = new ErrorModel();
                erro.setCodigoErro(500);
                erro.setMensagemErro("Erro de conexão, tente novamente");
                callback.onError(erro);
            }
        });
    }

    public void retornaDadosInscricao(int idUsuario, RetornaDadosInscricaoCallback callback, String sToken)
    {
        service = RetrofitConfig.acao();
        service.RetornaDadosInscricao(idUsuario, sToken).enqueue(new Callback<InscricaoRequestModel>() {
            @Override
            public void onResponse(Call<InscricaoRequestModel> call, Response<InscricaoRequestModel> response) {
                try
                {
                    if (response.isSuccessful())
                    {
                        callback.onSuccess(response.body());
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
                catch (Exception ex)
                {
                    ErrorModel erro = new ErrorModel();
                    erro.setCodigoErro(500);
                    erro.setMensagemErro("Erro inesperado, tente novamente");
                    callback.onError(erro);
                }
            }

            @Override
            public void onFailure(Call<InscricaoRequestModel> call, Throwable t)
            {
                ErrorModel erro = new ErrorModel();
                erro.setCodigoErro(500);
                erro.setMensagemErro("Erro de conexão, tente novamente");
                callback.onError(erro);
            }
        });
    }

    public void retornaSituacaoUsuario(int idUsuario, RetornaSituacaoCallback callback, String sToken)
    {
        service = RetrofitConfig.acao();
        service.RetornaSituacaoUsario(idUsuario, sToken).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
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
                    erro.setCodigoErro(500);
                    erro.setMensagemErro("Erro inesperado, tente novamente");
                    callback.onError(erro);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                ErrorModel erro = new ErrorModel();
                erro.setCodigoErro(500);
                erro.setMensagemErro("Erro de conexão, tente novamente");
                callback.onError(erro);
            }
        });
    }

    public void retornaIdInsc(RetornaStringCallback callback, String sToken, int idUsuario)
    {
        service = RetrofitConfig.acao();
        service.RetornaIdInsc(idUsuario,sToken).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                try
                {
                    if (response.isSuccessful())
                    {
                        callback.onSucess(response.body().toString());
                    }
                    else
                    {
                        ErrorModel erro = new ErrorModel();
                        JSONObject jErro = new JSONObject(response.errorBody().string());

                        erro.setCodigoErro(jErro.getInt("codigoErro"));
                        erro.setMensagemErro(jErro.getString("mensagemErro"));
                        callback.OnError(erro);
                    }
                }
                catch (Exception ex)
                {
                    ErrorModel erro = new ErrorModel();
                    erro.setCodigoErro(500);
                    erro.setMensagemErro("Erro inesperado, tente novamente");
                    callback.OnError(erro);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {
                ErrorModel erro = new ErrorModel();
                erro.setCodigoErro(500);
                erro.setMensagemErro("Erro de conexão, tente novamente");
                callback.OnError(erro);
            }
        });
    }
}
