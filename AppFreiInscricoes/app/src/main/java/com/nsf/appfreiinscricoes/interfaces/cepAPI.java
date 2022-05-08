package com.nsf.appfreiinscricoes.interfaces;

import com.nsf.appfreiinscricoes.model.EnderecoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface cepAPI
{
    String URL_BASE = "https://viacep.com.br/ws/";

    @GET("{cep}/json/")
    Call<EnderecoModel> getEndereco(@Path("cep") String cep);

}
