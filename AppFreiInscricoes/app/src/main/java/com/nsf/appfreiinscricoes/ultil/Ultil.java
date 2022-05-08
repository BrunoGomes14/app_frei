package com.nsf.appfreiinscricoes.ultil;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.model.AgendamentoModel;
import com.nsf.appfreiinscricoes.model.ResumoInscricaoModel;
import com.nsf.appfreiinscricoes.model.TbCodigo;
import com.nsf.appfreiinscricoes.model.TbNotificacao;
import com.nsf.appfreiinscricoes.model.TbUsuario;
import com.google.android.material.snackbar.Snackbar;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class Ultil implements InputFilter
{
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_WIFI = 1;
    public static final int NETWORK_STATUS_MOBILE = 2;

    public static String GerarMD5(String sTexto)
    {
        try
        {
            MessageDigest md =MessageDigest.getInstance("MD5");

            return new String(hexCodes(md.digest(sTexto.getBytes())));
        }
        catch (Exception ex)
        {
            Log.d("Criptografia: ", ex.getMessage());
        }

        return null;
    }

    private static char[] hexCodes(byte[] text) {
        char[] hexOutput = new char[text.length * 2];
        String hexString;

        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 2,
                    hexString.length(), hexOutput, i * 2);
        }
        return hexOutput;
    }

    public static TbUsuario recuperaDadosUsuario(Context context)
    {
        SharedPreferences preferences =  context.getSharedPreferences(context.getString(R.string.dados_usuario), MODE_PRIVATE);

        TbUsuario usuario = new TbUsuario();
        usuario.setNmUsuario(preferences.getString(context.getString(R.string.dados_usuario_nome), ""));
        usuario.setDsCpf(preferences.getString(context.getString(R.string.dados_usuario_cpf), ""));
        usuario.setDtNascimento(preferences.getString(context.getString(R.string.dados_usuario_data_nascimento), ""));
        usuario.setIdCodigo(preferences.getInt(context.getString(R.string.dados_usuario_id_codigo), 0));
        usuario.setIdUsuario(preferences.getInt(context.getString(R.string.dados_usuario_id), 0));
        usuario.setDsEmail(preferences.getString(context.getString(R.string.dados_usuario_email), ""));

        return usuario;
    }

    public static TbCodigo recuperaInfoInscricao(Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyCod = context.getString(R.string.dados_usuario_cod_insc);
        String keyAno = context.getString(R.string.dados_usuario_cod_insc_ano);
        String keyCurso1 = context.getString(R.string.dados_usuario_curso_primeiro);
        String keyCurso2 = context.getString(R.string.dados_usuario_curso_segundo);
        String keyPeriodo1 = context.getString(R.string.dados_usuario_periodo_primeiro);
        String keyPeriodo2 = context.getString(R.string.dados_usuario_periodo_segundo);

        SharedPreferences preferences = ((Activity) context).getSharedPreferences(context.getString(R.string.dados_usuario), MODE_PRIVATE);
        TbCodigo codigo = new TbCodigo();
        codigo.setDsAno(preferences.getInt(keyAno, 0));
        codigo.setNrCod(preferences.getInt(keyCod, 0));

        return codigo;
    }

    public static ResumoInscricaoModel recuperaDadosInscricao(Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyCurso1 = context.getString(R.string.dados_usuario_curso_primeiro);
        String keyCurso2 = context.getString(R.string.dados_usuario_curso_segundo);
        String keyPeriodo1 = context.getString(R.string.dados_usuario_periodo_primeiro);
        String keyPeriodo2 = context.getString(R.string.dados_usuario_periodo_segundo);
        String keyConfirma = context.getString(R.string.dados_usuario_confirmacao_insc);
        String keyAgendamentoData = context.getResources().getString(R.string.dados_usuario_agendamento_data);
        String keyAgendamentoHora = context.getResources().getString(R.string.dados_usuario_agendamento_hora);
        String keyComparaceuAgend = context.getResources().getString(R.string.dados_usuario_agendamento_compareceu);
        String keyPossuiAgendamento = context.getResources().getString(R.string.dados_usuario_agendamento);
        String keyMensagem = context.getResources().getString(R.string.dados_usuario_mensagem);

        SharedPreferences preferences = ((Activity) context).getSharedPreferences(context.getString(R.string.dados_usuario), MODE_PRIVATE);
        ResumoInscricaoModel resumo = new ResumoInscricaoModel();

        resumo.dsPossuiAgendamento = preferences.getInt(keyPossuiAgendamento, 0);
        resumo.dsCompareceuAgendamento = preferences.getInt(keyComparaceuAgend, 0);
        resumo.dsAgendamentoData = preferences.getString(keyAgendamentoData, "");
        resumo.dsAgendamentoHora = preferences.getString(keyAgendamentoHora, "");
        resumo.dsCursoPrimeiro = preferences.getString(keyCurso1, "");
        resumo.dsTurnoPrimeiro = preferences.getString(keyPeriodo1, "");
        resumo.dsCursoSegundo = preferences.getString(keyCurso2, "");
        resumo.dsTurnoSegundo = preferences.getString(keyPeriodo2, "");
        resumo.dsConfirmaPgto = preferences.getInt(keyConfirma, -1);
        resumo.mensagem = preferences.getString(keyMensagem, "");
        resumo.dsCodigoInscricao = recuperaInfoInscricao(context).getNrCod();
        resumo.nmUsuario = recuperaDadosUsuario(context).getNmUsuario();
        resumo.dsCpf = recuperaDadosUsuario(context).getDsCpf();

        return resumo;
    }

    public static String formataDataSQLParaConvencional(String data)
    {
        if(!data.contains("-"))
        {
            return "00/00/0000";
        }

        String[] arrData = data.split("-");

        if(arrData.length < 3)
        {
            return "00/00/0000";
        }

        String ret = String.format(Locale.CANADA, "%s/%s/%s", arrData[2].substring(0,2), arrData[1], arrData[0]);

        return ret;
    }

    public static String formataDtpConvencional(int year, int month, int dayOfMonth) {
        String sMonth;
        String sDay;

        if (month < 10) {
            sMonth = "0" + month;
        } else {
            sMonth = String.valueOf(month);
        }

        if (dayOfMonth < 10) {
            sDay = "0" + dayOfMonth;
        } else {
            sDay = String.valueOf(dayOfMonth);
        }

        return String.format(Locale.CANADA, "%s/%s/%d", sDay, sMonth, year);
    }

    public static String formataDataConvencionalParaSQL(String data)
    {
        if(!data.contains("/"))
        {
            return "0000-00-00";
        }

        String[] arrData = data.split("/");

        if(arrData.length < 3)
        {
            return "0000-00-00";
        }

        return String.format(Locale.CANADA, "%s-%s-%s", arrData[2], arrData[1], arrData[0]);
    }

    public static String mascaraCPF(String sCpf)
    {
        int index = 0;
        String sAux = "";

        if (sCpf.contains("-"))
        {
            return sCpf;
        }

        for (char caracter: sCpf.toCharArray())
        {
            sAux += caracter;
            index += 1;

            if (index == 3 || index == 6)
            {
                sAux += ".";
            }
            else if (index == 9)
            {
                sAux += "-";
            }
        }

        return sAux;
    }

    public static int retornaHoraSql(String hora)
    {
        String hra = hora.substring(0,2);
        return Integer.valueOf(hra);
    }

    public static String retornaHoraSemSegundos(String hora)
    {
        return hora.substring(0,5);
    }

    public static boolean validaData(String data)
    {
        if(!data.contains("/"))
        {
            return false;
        }

        if(data.replace("/", "").length() != 8)
        {
            return false;
        }

        String[] split = data.split("/");
        int dia = Integer.parseInt(split[0]);
        int mes = Integer.parseInt(split[1]);
        int ano = Integer.parseInt(split[2]);

        if(dia > 31 || mes > 12 || ano > 3000 || ano < 1900)
        {
            return false;
        }

        if(mes == 2 && dia > 29)
        {
            return false;
        }

        return true;
    }

    public static void abaixaTeclado(Activity context)
    {
        View view = context.getCurrentFocus();

        if(view != null)
        {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void mostraTeclado(View view, AppCompatActivity activity)
    {
        InputMethodManager imm = (InputMethodManager)  activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void moveScroll(NestedScrollView scroll, View controle)
    {
//        scroll.post(new Runnable() {
//            @Override
//            public void run() {
//                scroll.fling(0);
//                int vTop = controle.getTop();
//                int vBottom = controle.getBottom();
//                int sHeight = scroll.getBottom();
//                scroll.smoothScrollTo(((vTop + vBottom - sHeight) / 2), 0);
//            }
//        });

        int scrollTo = ((View) controle.getParent().getParent()).getTop() + controle.getTop();
        scroll.smoothScrollTo(0, scrollTo);
    }

    public static void salvaLogin(TbUsuario usuario, Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String usuarioKey = context.getString(R.string.dados_usuario_nome);
        String nascimentoKey = context.getString(R.string.dados_usuario_data_nascimento);
        String cpfKey = context.getString(R.string.dados_usuario_cpf);
        String idKey = context.getString(R.string.dados_usuario_id);
        String idCodKey = context.getString(R.string.dados_usuario_id_codigo);
        String acessoKey = context.getResources().getString(R.string.dados_usuario_primeiro_acesso);
        String tokenKey = context.getResources().getString(R.string.dados_usuario_token_autentic);
        String emailKey = context.getResources().getString(R.string.dados_usuario_email);

        SharedPreferences.Editor editor = context.getSharedPreferences(key, context.MODE_PRIVATE).edit();
        editor.putString(usuarioKey, usuario.getNmUsuario());
        editor.putString(nascimentoKey, usuario.getDtNascimento());
        editor.putString(cpfKey, usuario.getDsCpf());
        editor.putInt(idKey, usuario.getIdUsuario());
        editor.putInt(idCodKey, usuario.getIdCodigo());
        editor.putBoolean(acessoKey, false);
        editor.putString(tokenKey, usuario.getDsToken());
        editor.putString(emailKey, usuario.getDsEmail());

        editor.commit();
    }

    public static void alteraInfoLogin(TbUsuario usuario, Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String usuarioKey = context.getString(R.string.dados_usuario_nome);
        String nascimentoKey = context.getString(R.string.dados_usuario_data_nascimento);
        String cpfKey = context.getString(R.string.dados_usuario_cpf);

        SharedPreferences login = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editLogin = login.edit();
        editLogin.putString(usuarioKey, usuario.getNmUsuario());
        editLogin.putString(nascimentoKey, usuario.getDtNascimento());
        editLogin.putString(cpfKey, usuario.getDsCpf());
        editLogin.commit();
    }

    public static void salvaInfoIncricoes(Context context, int cd, int ano)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyCod = context.getString(R.string.dados_usuario_cod_insc);
        String keyAno = context.getString(R.string.dados_usuario_cod_insc_ano);

        SharedPreferences login = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editLogin = login.edit();
        editLogin.putInt(keyCod, cd);
        editLogin.putInt(keyAno, ano);
        editLogin.commit();
    }

    public static boolean isFirstAcess(Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String acessoKey = context.getResources().getString(R.string.dados_usuario_primeiro_acesso);

        SharedPreferences login = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return login.getBoolean(acessoKey, false);
    }

    public static void primeiroAcessoFinalizado(Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String acessoKey = context.getResources().getString(R.string.dados_usuario_primeiro_acesso);

        SharedPreferences login = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editLogin = login.edit();
        editLogin.putBoolean(acessoKey, true);
        editLogin.commit();
    }

    public static void alteraInfoInscricoes(Context context, ResumoInscricaoModel inscricaoModel)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyCurso1 = context.getString(R.string.dados_usuario_curso_primeiro);
        String keyCurso2 = context.getString(R.string.dados_usuario_curso_segundo);
        String keyPeriodo1 = context.getString(R.string.dados_usuario_periodo_primeiro);
        String keyPeriodo2 = context.getString(R.string.dados_usuario_periodo_segundo);
        String keyConfirma = context.getString(R.string.dados_usuario_confirmacao_insc);
        String keyAgendamentoData = context.getResources().getString(R.string.dados_usuario_agendamento_data);
        String keyAgendamentoHora = context.getResources().getString(R.string.dados_usuario_agendamento_hora);
        String keyComparaceuAgend = context.getResources().getString(R.string.dados_usuario_agendamento_compareceu);
        String keyPossuiAgendamento = context.getResources().getString(R.string.dados_usuario_agendamento);
        String keyMensagem = context.getResources().getString(R.string.dados_usuario_mensagem);

        SharedPreferences login = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editLogin = login.edit();

        editLogin.putString(keyCurso1, inscricaoModel.dsCursoPrimeiro);
        editLogin.putString(keyPeriodo1, inscricaoModel.dsTurnoPrimeiro);
        editLogin.putInt(keyConfirma, inscricaoModel.dsConfirmaPgto);

        if (!inscricaoModel.dsCursoSegundo.isEmpty())
        {
            editLogin.putString(keyCurso2, inscricaoModel.dsCursoSegundo);
            editLogin.putString(keyPeriodo2, inscricaoModel.dsTurnoSegundo);
        }

        if (inscricaoModel.dsAgendamentoData != null)
        {
            editLogin.putInt(keyPossuiAgendamento, inscricaoModel.dsPossuiAgendamento);
            editLogin.putString(keyAgendamentoData, inscricaoModel.dsAgendamentoData);
            editLogin.putString(keyAgendamentoHora, inscricaoModel.dsAgendamentoHora);
            editLogin.putInt(keyComparaceuAgend, inscricaoModel.dsCompareceuAgendamento);
            editLogin.putString(keyMensagem, inscricaoModel.mensagem);
        }

        editLogin.commit();
    }

    public static void salvaAgendamento(Context context, AgendamentoModel agendamento)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyAgendamentoData = context.getResources().getString(R.string.dados_usuario_agendamento_data);
        String keyAgendamentoHora = context.getResources().getString(R.string.dados_usuario_agendamento_hora);
        String keyComparaceuAgend = context.getResources().getString(R.string.dados_usuario_agendamento_compareceu);
        String keyPossuiAgendamento = context.getResources().getString(R.string.dados_usuario_agendamento);

        SharedPreferences login = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editLogin = login.edit();

        editLogin.putInt(keyPossuiAgendamento, 1);
        editLogin.putString(keyAgendamentoData, agendamento.getDtAgendamento());
        editLogin.putString(keyAgendamentoHora, agendamento.getHrAgendamento());
        editLogin.putInt(keyComparaceuAgend, agendamento.getDsCompareceu());

        editLogin.commit();
    }


    public static int retornaIDInscPref(Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyCod = context.getString(R.string.dados_usuario_cod_insc);

        SharedPreferences dados = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return dados.getInt(keyCod, 0);
    }

    public static void salvaToken(Context context, String token)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyToken = context.getString(R.string.dados_usuario_token);

        SharedPreferences login = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editLogin = login.edit();

        editLogin.putString(keyToken, token);
        editLogin.commit();
    }

    public static String retornaTokenFirebase(Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyToken = context.getString(R.string.dados_usuario_token);

        SharedPreferences dados = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return dados.getString(keyToken, "");
    }

    public static String retornaTokenAuth(Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyToken = context.getString(R.string.dados_usuario_token_autentic);

        SharedPreferences dados = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return dados.getString(keyToken, "");
    }

    public static void excluiPrefs(Context context)
    {
        SharedPreferences login = context.getSharedPreferences(context.getString(R.string.dados_usuario), Context.MODE_PRIVATE);
        SharedPreferences.Editor editLogin = login.edit();
        editLogin.clear();
        editLogin.commit();

        if(retornaIDInscPref(context) > 0) {
            SharedPreferences insc = context.getSharedPreferences(context.getString(R.string.dados_usuario), Context.MODE_PRIVATE);
            SharedPreferences.Editor editInsc = insc.edit();
            editInsc.clear();
            editInsc.commit();
        }
    }

    Pattern mPattern = Pattern.compile("(0|[1-9]+[0-9]*)?(\\.[0-9]{0,2})?");

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
    {

        String result =
                dest.subSequence(0, dstart)
                        + source.toString()
                        + dest.subSequence(dend, dest.length());

        Matcher matcher = mPattern.matcher(result);

        if (!matcher.matches()) return dest.subSequence(dstart, dend);
        return null;
    }

    public static String retiraPontuacao(String sTexto)
    {
        return sTexto.replace(" ", "")
                    .replace("-", "")
                    .replace("(", "")
                    .replace(")", "")
                    .replace(".", "");

    }

    public static String criaFraseHorario(String inicio, String fim)
    {
        String horaIni = inicio.substring(0, 5);
        String horaFim = fim.substring(0, 5);

        return horaIni + " às " + horaFim;
    }

    public static void ExibeSnack(String Menssagem, View view)
    {
        Snackbar snackbar = Snackbar
                .make(view, Menssagem, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(view.getResources().getColor(R.color.vermelho));
        snackbar.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static int retornaIdNotificacao(Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyIdNotf = context.getString(R.string.dados_usuario_id_notificacao);

        SharedPreferences dados = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return dados.getInt(keyIdNotf, 0);
    }

    public static void exibiuNotificacao(Context context)
    {
        String key = context.getString(R.string.dados_usuario);
        String keyIdNotf = context.getString(R.string.dados_usuario_id_notificacao);

        SharedPreferences dados = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        int id = dados.getInt(keyIdNotf, 0);

        SharedPreferences.Editor edit = dados.edit();
        edit.putInt(keyIdNotf, id + 1);
        edit.commit();
    }

    public static PackageInfo InfoApp(Context context)
    {
        try
        {
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pinfo;
        }
        catch (Exception ex)
        {
            return new PackageInfo();
        }
    }

    public static String ConverteEscolaridade(String sEntrada, boolean bConverte)
    {
        if (bConverte)
        {
            if (sEntrada.contains("º ano do Ensino Médio"))
            {
                return sEntrada.substring(0, 1)  + "º EM";
            }
            else
            {
                return sEntrada;
            }
        }
        else
        {
            if (sEntrada.contains("EM"))
            {
                return sEntrada.substring(0,1) + "º ano do Ensino Médio";
            }
            else
            {
                return sEntrada;
            }
        }
    }

}
