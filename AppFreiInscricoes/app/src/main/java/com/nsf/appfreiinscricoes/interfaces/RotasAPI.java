package com.nsf.appfreiinscricoes.interfaces;

import com.nsf.appfreiinscricoes.model.AgendamentoResponse;
import com.nsf.appfreiinscricoes.model.InitResponse;
import com.nsf.appfreiinscricoes.model.PeriodoAgendamentoModel;
import com.nsf.appfreiinscricoes.model.Requests.CursoPeriodoRequest;
import com.nsf.appfreiinscricoes.model.Requests.InscricaoRequestModel;
import com.nsf.appfreiinscricoes.model.Requests.TbCursoRequest;
import com.nsf.appfreiinscricoes.model.ResumoInscricaoModel;
import com.nsf.appfreiinscricoes.model.TbCodigo;
import com.nsf.appfreiinscricoes.model.TbCursos;
import com.nsf.appfreiinscricoes.model.TbFaq;
import com.nsf.appfreiinscricoes.model.TbNotificacao;
import com.nsf.appfreiinscricoes.model.TbPeridoInscricao;
import com.nsf.appfreiinscricoes.model.TbUsuario;
import com.nsf.appfreiinscricoes.model.VestibularModel;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RotasAPI
{
    //TODO: Trocar para endereco fixo
    static String URL_BASE = "http://nsfapp.southcentralus.cloudapp.azure.com:5000/";

    @POST("Login/postLogin")
    Call<String> InsereLogin(@Body JsonElement login);

    @GET("Login/ValidaLogin/{login}/{token}")
    Call<TbUsuario> ValidaLogin(@Path("login") String login, @Path("token") String tokenFirebase);

    @GET("PeriodoInscricoes/getPeriodoMobile")
    Call<TbPeridoInscricao> ConsultaPeriodoInscricoes(@Header("auth_token") String token);

    @GET("Cursos/getCursoMobile")
    Call<List<TbCursos>> ListaCursos(@Header("auth_token") String token);

    @POST("Inscricao/postInscricao")
    Call<TbCodigo> EnviaInscricao(@Body JsonElement inscricao, @Header("auth_token") String token);

    @GET("Inscricao/getResumoInscricao/{codInsc}")
    Call<ResumoInscricaoModel> RetornaResumoInscricao(@Path("codInsc") int codInsc, @Header("auth_token") String token);

    @GET("Codigo/getInscricaoCandidatoAno/{idInsc}")
    Call<TbCodigo> RetornaInscricao(@Path("idInsc") int idInsc, @Header("auth_token") String token);

    @GET("Login/SolicitaRecuperacaoSenha/{email}")
    Call<Integer> SolicitaRecuperacaoSenha(@Path("email") String email, @Header("auth_token") String token);

    @GET("Login/ConfereCodRec/{cod}/{idUsuario}")
    Call<String> ConfereCodRec(@Path("cod") String cod, @Path("idUsuario") int idUsuario, @Header("auth_token") String token);

    @PUT("Login/AlteraSenhaRec")
    Call<String> AlteraSenha(@Body JsonElement json, @Header("auth_token") String token);

    @GET("Cursos/getCursos")
    Call<ArrayList<TbCursoRequest>> RetornaCursos(@Header("auth_token") String token);

    @GET("Faq/getFaqs")
    Call<ArrayList<TbFaq>> RetornaFaqs(@Header("auth_token") String token);

    @GET("Cursos/getCursosPeriodo")
    Call<ArrayList<CursoPeriodoRequest>> RetornaCursosPeriodo(@Header("auth_token") String token);

    @GET("Inscricao/getInfoAlterar/{idUsuario}")
    Call<InscricaoRequestModel> RetornaDadosInscricao(@Path("idUsuario") int idUsuario, @Header("auth_token") String token);

    @GET("Inscricao/VerificaSituacao/{idUsuario}")
    Call<Integer> RetornaSituacaoUsario(@Path("idUsuario") int idUsuario, @Header("auth_token") String token);

    @GET("Notificacao/getNotificacoesUsuario/{idUsuario}")
    Call<ArrayList<TbNotificacao>> RetornaNotificacoesUsuario(@Path("idUsuario") int idUsuario, @Header("auth_token") String token);

    @DELETE("Login/RealizaLogout")
    Call<String> RealizaLogout(@Body JsonElement delete, @Header("auth_token") String token);

    @PUT("Usuario/AlteraToken")
    Call<String> AlteraToken(@Body JsonElement json, @Header("auth_token") String token);

    @GET("Agendamento/getPeriodoAgendamento")
    Call<PeriodoAgendamentoModel> RetornaPeriodoAgendamento(@Header("auth_token") String token);

    @POST("Agendamento/postAgendamento")
    Call<AgendamentoResponse> AgendaPagamento(@Body JsonElement json, @Header("auth_token") String token);

    @GET("Vestibular/RetornaVestibular/{idUsuario}")
    Call<VestibularModel> RetornaVestibular(@Path("idUsuario") int idUsuario, @Header("auth_token") String token);

    @GET("Usuario/VerificaInsc/{idUsuario}")
    Call<Integer> RetornaIdInsc(@Path("idUsuario") int idUsuario, @Header("auth_token") String token);

    @GET("InfoDb/InfoApp")
    Call<Integer> RetornaAppMin();

    @GET("App/Init/{id}")
    Call<InitResponse> Init(@Path("id") int idUsuario, @Header("auth_token") String token);

    @PUT("Agendamento/putAgendamento")
    Call<String> AlteraAgendamento(@Body JsonElement json, @Header("auth_token") String token);
}
