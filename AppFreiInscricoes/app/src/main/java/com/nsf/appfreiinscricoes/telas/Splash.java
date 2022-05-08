package com.nsf.appfreiinscricoes.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.requestsAPI.LoginRequests;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogAviso;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogUpdate;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                RetornaStringCallback callback = new RetornaStringCallback() {
                    @Override
                    public void onSucess(String sucesso) {

                        int iLastVersion = Integer.valueOf(sucesso);
                        int versioApp = Ultil.InfoApp(Splash.this).versionCode;

                        if (versioApp >= iLastVersion)
                        {
                            abreProximaEtapa();
                        }
                        else
                        {
                            DialogUpdate dlg = new DialogUpdate(Splash.this);
                            dlg.setCancelable(false);
                            dlg.show();
                        }
                    }

                    @Override
                    public void OnError(ErrorModel erro)
                    {
                        DialogAviso aviso = new DialogAviso(Splash.this, "Não foi possível estabelecer conexão com nossos sistema, tente novamente mais tarde!", Splash.this);
                        aviso.setCancelable(false);
                        aviso.show();
                    }
                };

                LoginRequests requests = new LoginRequests();

                if (Ultil.isNetworkAvailable(Splash.this))
                {
                    requests.RetornaVersaoApp(callback);
                }
                else
                {
                    abreProximaEtapa();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    public void abreProximaEtapa()
    {
        Intent mainIntent;

        if(Ultil.recuperaDadosUsuario(Splash.this).getDsCpf().length() > 0)
        {

            if (getIntent().hasExtra("pushActivity"))
            {
                Bundle bExtra = getIntent().getExtras();
                String extra = bExtra.getString("pushActivity", "");

                if(extra.length() > 0 && extra.equals("pushInscricao"))
                {
                    mainIntent = new Intent(Splash.this, FrmInformacoesInscricao.class);

                }
                else
                {
                    mainIntent = new Intent(Splash.this, FrmNotificacoes.class);
                }
            }
            else
            {
                mainIntent = new Intent(Splash.this, MainActivity.class);
            }
        }
        else
        {
            mainIntent = new Intent(Splash.this, FrmLogin.class);
        }

        startActivity(mainIntent);
        finish();
    }
}
