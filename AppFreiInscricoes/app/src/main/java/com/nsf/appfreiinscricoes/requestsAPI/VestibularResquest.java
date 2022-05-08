package com.nsf.appfreiinscricoes.requestsAPI;

import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaVestibularCallback;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.VestibularModel;
import com.nsf.appfreiinscricoes.ultil.RetrofitConfig;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VestibularResquest
{
    RotasAPI service;
    ErrorModel erro = new ErrorModel();

    public void retornaVestibular(RetornaVestibularCallback callback, String sToken,int idUsuario)
    {
        service = RetrofitConfig.acao();
        service.RetornaVestibular(idUsuario, sToken).enqueue(new Callback<VestibularModel>() {
            @Override
            public void onResponse(Call<VestibularModel> call, Response<VestibularModel> response)
            {
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
                catch (Exception ex)
                {
                    erro = new ErrorModel();
                    erro.setCodigoErro(500);
                    erro.setMensagemErro("Um erro inesperado ocorreu, tente novamente");

                    callback.onError(erro);
                }
            }

            @Override
            public void onFailure(Call<VestibularModel> call, Throwable t)
            {
                erro = new ErrorModel();
                erro.setCodigoErro(500);
                erro.setMensagemErro("Falha de conexão, tente novamente");

                callback.onError(erro);
            }
        });
    }
}
