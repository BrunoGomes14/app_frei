package com.nsf.appfreiinscricoes.requestsAPI;

import android.content.Context;
import android.content.Intent;

import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaInitCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaLoginCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.AlteraSenha;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.InitResponse;
import com.nsf.appfreiinscricoes.model.Requests.LoginRequestModel;
import com.nsf.appfreiinscricoes.model.Requests.LogoutRequest;
import com.nsf.appfreiinscricoes.model.TbFirebaseTokens;
import com.nsf.appfreiinscricoes.model.TbLogin;
import com.nsf.appfreiinscricoes.model.TbUsuario;
import com.nsf.appfreiinscricoes.ultil.RetrofitConfig;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRequests
{
    RotasAPI service;
    Intent intent;
    String msgErro;
    int cdErro;
    Gson gson = new Gson();

    public void InserirLogin(LoginRequestModel login, RetornaStringCallback callback) {
        GsonBuilder gsonLogin = new GsonBuilder();

        JsonElement json = gsonLogin.create().toJsonTree(login);
        service = RetrofitConfig.acao();
        service.InsereLogin(json).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                try
                {
                    if (response.isSuccessful())
                    {
                        callback.onSucess("Ok");
                    }
                    else
                    {
                        JSONObject jErro = new JSONObject(response.errorBody().string());
                        ErrorModel errorModel = new ErrorModel();
                        errorModel.setCodigoErro(500);
                        errorModel.setMensagemErro(jErro.getString("mensagemErro"));

                        callback.OnError(errorModel);
                    }
                }
                catch(Exception ex)
                {
                    ErrorModel errorModel = new ErrorModel();
                    errorModel.setCodigoErro(500);
                    errorModel.setMensagemErro("Erro inesperado, tente novamente");

                    callback.OnError(errorModel);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                ErrorModel errorModel = new ErrorModel();
                errorModel.setCodigoErro(500);
                errorModel.setMensagemErro("Erro de conexão, tente novamente");

                callback.OnError(errorModel);
            }
        });
    }

    public void ValidaLogin(TbLogin login, RetornaLoginCallback loginCallback) {
        String envio = login.getDsEmail() + "&" + login.getDsSenha();

        service = RetrofitConfig.acao();

        service.ValidaLogin(envio, login.getToken()).enqueue(new Callback<TbUsuario>() {
            @Override
            public void onResponse(Call<TbUsuario> call, Response<TbUsuario> response) {
                try
                {
                    if (response.isSuccessful())
                    {
                        loginCallback.onSucess(response.body());
                    }
                    else
                    {
                        ErrorModel error = new ErrorModel();
                        JSONObject jErro = new JSONObject(response.errorBody().string());
                        error.setMensagemErro(jErro.getString("mensagemErro"));

                        loginCallback.OnError(error);
                    }
                }
                catch (Exception ex)
                {
                    ErrorModel error = new ErrorModel();
                    error.setMensagemErro("Um erro inesperado aconteceu, tente novamente mais tarde");

                    loginCallback.OnError(error);
                }
            }

            @Override
            public void onFailure(Call<TbUsuario> call, Throwable t)
            {
                ErrorModel error = new ErrorModel();
                error.setMensagemErro("Falha de conexão, tente novamente mais tarde");

                loginCallback.OnError(error);
            }
        });
    }

    public void iniciaAlteracaoSenha(String email, String token,RetornaStringCallback callBack)
    {
        service = RetrofitConfig.acao();

        service.SolicitaRecuperacaoSenha(email, token).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                try
                {
                    if (response.isSuccessful())
                    {
                        callBack.onSucess(String.valueOf(response.body()));
                    }
                    else
                    {
                        ErrorModel erro = new ErrorModel();
                        JSONObject jErro = new JSONObject(response.errorBody().string());

                        erro.setCodigoErro(jErro.getInt("codigoErro"));
                        erro.setMensagemErro(jErro.getString("mensagemErro"));

                        callBack.OnError(erro);
                    }
                }
                catch (Exception err)
                {
                    ErrorModel errorModel = new ErrorModel();
                    errorModel.setCodigoErro(500);
                    errorModel.setMensagemErro("Erro inesperado, tente novamente");

                    callBack.OnError(errorModel);
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {
                ErrorModel errorModel = new ErrorModel();
                errorModel.setCodigoErro(500);
                errorModel.setMensagemErro("Falha de conexão, tente novamente");

                callBack.OnError(errorModel);
            }
        });
    }

    public void ConfereCod(String cod, int idUsuario, String token,RetornaStringCallback callBack)
    {
        service = RetrofitConfig.acao();

        service.ConfereCodRec(cod,idUsuario, token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                try
                {
                    if (response.isSuccessful())
                    {
                        callBack.onSucess("Ok");
                    }
                    else
                    {
                        ErrorModel erro = new ErrorModel();
                        JSONObject jErro = new JSONObject(response.errorBody().string());

                        erro.setCodigoErro(jErro.getInt("codigoErro"));
                        erro.setMensagemErro(jErro.getString("mensagemErro"));

                        callBack.OnError(erro);
                    }
                }
                catch (Exception err)
                {
                    ErrorModel errorModel = new ErrorModel();
                    errorModel.setCodigoErro(500);
                    errorModel.setMensagemErro("Erro inesperado, tente novamente");

                    callBack.OnError(errorModel);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                ErrorModel errorModel = new ErrorModel();
                errorModel.setCodigoErro(500);
                errorModel.setMensagemErro("Falha de conexão, tente novamente");

                callBack.OnError(errorModel);
            }
        });
    }


    public void AlteraSenha(AlteraSenha novaSenha, String token,RetornaStringCallback callBack)
    {
        service = RetrofitConfig.acao();
        GsonBuilder gsonBuild = new GsonBuilder();
        JsonElement json = gsonBuild.create().toJsonTree(novaSenha);

        service.AlteraSenha(json, token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                try
                {
                    if (response.isSuccessful())
                    {
                        callBack.onSucess("Ok");
                    }
                    else
                    {
                        ErrorModel erro = new ErrorModel();
                        JSONObject jErro = new JSONObject(response.errorBody().string());

                        erro.setCodigoErro(jErro.getInt("codigoErro"));
                        erro.setMensagemErro(jErro.getString("mensagemErro"));

                        callBack.OnError(erro);
                    }
                }
                catch (Exception err)
                {
                    ErrorModel errorModel = new ErrorModel();
                    errorModel.setCodigoErro(500);
                    errorModel.setMensagemErro("Erro inesperado, tente novamente");

                    callBack.OnError(errorModel);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                ErrorModel errorModel = new ErrorModel();
                errorModel.setCodigoErro(500);
                errorModel.setMensagemErro("Falha de conexão, tente novamente");

                callBack.OnError(errorModel);
            }
        });
    }

    public static void RealizaLogout(LogoutRequest logout, String token)
    {
        RotasAPI service;

        service = RetrofitConfig.acao();
        GsonBuilder gsonBuild = new GsonBuilder();
        JsonElement json = gsonBuild.create().toJsonTree(logout);

        service.RealizaLogout(json, token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if (response.isSuccessful())
                {
                    String a = response.body();
                    String b = a;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                String a = t.getMessage();
                String b = a;
            }
        });
    }

    public void AlteraToken(TbFirebaseTokens tokenFirebase, String tokenAut, Context context)
    {
        service = RetrofitConfig.acao();

        GsonBuilder gsonBuild = new GsonBuilder();
        JsonElement json = gsonBuild.create().toJsonTree(tokenAut);

        service.AlteraToken(json, tokenAut).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.isSuccessful())
                {
                    Ultil.salvaToken(context, tokenFirebase.newToken);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void RetornaVersaoApp(RetornaStringCallback callback)
    {
        service = RetrofitConfig.acao();
        service.RetornaAppMin().enqueue(new Callback<Integer>() {
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
                catch (Exception err)
                {
                    ErrorModel erro = new ErrorModel();
                    erro.setCodigoErro(500);
                    erro.setMensagemErro("Erro inesperado");

                    callback.OnError(erro);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {
                ErrorModel erro = new ErrorModel();
                erro.setCodigoErro(500);
                erro.setMensagemErro("Erro de conexão");

                callback.OnError(erro);
            }
        });
    }

    public void RetornaInit(RetornaInitCallback callback, int idUsuario, String sToken)
    {
        service = RetrofitConfig.acao();
        service.Init(idUsuario, sToken).enqueue(new Callback<InitResponse>() {
            @Override
            public void onResponse(Call<InitResponse> call, Response<InitResponse> response)
            {
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
                catch (Exception err)
                {
                    ErrorModel erro = new ErrorModel();
                    erro.setCodigoErro(500);
                    erro.setMensagemErro("Erro inesperado");

                    callback.onError(erro);
                }
            }

            @Override
            public void onFailure(Call<InitResponse> call, Throwable t)
            {
                ErrorModel erro = new ErrorModel();
                erro.setCodigoErro(500);
                erro.setMensagemErro("Erro de conexão");

                callback.onError(erro);
            }
        });
    }
}
