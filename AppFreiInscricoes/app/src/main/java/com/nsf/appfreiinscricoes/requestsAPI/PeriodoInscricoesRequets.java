package com.nsf.appfreiinscricoes.requestsAPI;

import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaPeriodoInscricaoCallback;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbPeridoInscricao;
import com.nsf.appfreiinscricoes.ultil.RetrofitConfig;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeriodoInscricoesRequets
{
    public void ConsultaPeriodoIncricoes(RetornaPeriodoInscricaoCallback callback, String sToken)
    {
        RotasAPI service = RetrofitConfig.acao();
        service.ConsultaPeriodoInscricoes(sToken).enqueue(new Callback<TbPeridoInscricao>() {
            @Override
            public void onResponse(Call<TbPeridoInscricao> call, Response<TbPeridoInscricao> response)
            {
                try
                {
                    if(response.isSuccessful())
                    {
                        callback.onSuccess(response.body());
                    }
                    else
                    {
                        JSONObject jErro = new JSONObject(response.errorBody().string());

                        ErrorModel errorModel = new ErrorModel();
                        errorModel.setMensagemErro(jErro.getString("mensagemErro"));
                        errorModel.setCodigoErro(jErro.getInt("codigoErro"));

                        callback.onError(errorModel);

                    }
                }
                catch (Exception ex)
                {
                    ErrorModel errorModel = new ErrorModel();
                    errorModel.setMensagemErro("Erro inesperado, tente novamente mais tarde");
                    errorModel.setCodigoErro(500);

                    callback.onError(errorModel);
                }
            }

            @Override
            public void onFailure(Call<TbPeridoInscricao> call, Throwable t)
            {
                ErrorModel errorModel = new ErrorModel();
                errorModel.setMensagemErro("Erro de rede, tente novamente mais tarde");
                errorModel.setCodigoErro(500);

                callback.onError(errorModel);
            }
        });
    }
}
