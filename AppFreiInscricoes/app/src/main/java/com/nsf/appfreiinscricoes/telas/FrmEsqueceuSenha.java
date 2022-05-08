package com.nsf.appfreiinscricoes.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.requestsAPI.LoginRequests;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.model.AlteraSenha;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogAviso;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogAvisoSucesso;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogLoad;
import com.nsf.appfreiinscricoes.ultil.Ultil;

public class FrmEsqueceuSenha extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout lnlAlterarSenha;
    private LinearLayout lnlInsereEmail;
    private LinearLayout lnlVerificaCodigo;
    private EditText txtSenha;
    private EditText txtSenhaRep;
    private EditText txtEmail;
    private EditText txtCodRec;
    private LinearLayout lnlSalvar;
    private LinearLayout lnlVerificar;
    private LinearLayout lnlVerificarEmail;
    private LinearLayout lnlPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_esqueceu_senha);

        recuperaControles();
        iniciaLayout(0);
    }

    public void recuperaControles()
    {
        lnlAlterarSenha = (LinearLayout) findViewById(R.id.lnlAlterarSenha);
        lnlInsereEmail = (LinearLayout) findViewById(R.id.lnlInsereEmail);
        lnlVerificaCodigo = (LinearLayout) findViewById(R.id.lnlVerificaCodigo);
        txtCodRec = (EditText) findViewById(R.id.txtCodRec);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtSenhaRep = (EditText) findViewById(R.id.txtSenhaRep);
        lnlSalvar = (LinearLayout) findViewById(R.id.lnlSalvar);
        lnlVerificarEmail = (LinearLayout) findViewById(R.id.lnlVerificarEmail);
        lnlVerificar = (LinearLayout) findViewById(R.id.lnlVerificar);
        lnlPrincipal = findViewById(R.id.lnlPrincipal);

        lnlVerificar.setOnClickListener(this);
        lnlVerificarEmail.setOnClickListener(this);
        lnlSalvar.setOnClickListener(this);
    }

    public void iniciaLayout(int pos)
    {
        if (pos == 0)
        {
            lnlInsereEmail.setVisibility(View.VISIBLE);
            lnlVerificaCodigo.setVisibility(View.GONE);
            lnlAlterarSenha.setVisibility(View.GONE);
        }
        else if (pos == 1)
        {
            lnlInsereEmail.setVisibility(View.GONE);
            lnlVerificaCodigo.setVisibility(View.VISIBLE);
            lnlAlterarSenha.setVisibility(View.GONE);
        }
        else if (pos == 2)
        {
            lnlInsereEmail.setVisibility(View.GONE);
            lnlVerificaCodigo.setVisibility(View.GONE);
            lnlAlterarSenha.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == lnlVerificarEmail.getId())
        {
            enviaDados(0);
        }

        if (v.getId() == lnlVerificar.getId())
        {
            enviaDados(1);
        }

        if (v.getId() == lnlSalvar.getId())
        {
            enviaDados(2);
        }
    }

    int cdUser = 0;
    String sCodRec = "";

    public void enviaDados(int pos)
    {
        String sSenha;
        String sSenhaRep;
        String sEmail;
        DialogLoad dlgLoad = new DialogLoad(this);
        RetornaStringCallback callBack;
        LoginRequests loginRequest = new LoginRequests();

        try
        {
            if(pos == 0)
            {
                if (validaCampos(0))
                {
                    dlgLoad.show();

                    sEmail = txtEmail.getText().toString().trim();

                    callBack = new RetornaStringCallback() {
                        @Override
                        public void onSucess(String sucesso)
                        {
                            cdUser = Integer.parseInt(sucesso);
                            dlgLoad.dismiss();
                            iniciaLayout(1);
                        }

                        @Override
                        public void OnError(ErrorModel erro)
                        {
                            DialogAviso dlgAviso = new DialogAviso(FrmEsqueceuSenha.this, erro.getMensagemErro());
                            dlgLoad.dismiss();
                            dlgAviso.show();
                        }
                    };

                    loginRequest.iniciaAlteracaoSenha(sEmail, Ultil.retornaTokenAuth(this), callBack);
                }
            }
            else if(pos ==  1)
            {
                if(validaCampos(1))
                {
                    dlgLoad.show();

                    sCodRec = txtCodRec.getText().toString().trim();

                    callBack = new RetornaStringCallback() {
                        @Override
                        public void onSucess(String sucesso)
                        {
                            dlgLoad.dismiss();
                            iniciaLayout(2);
                        }

                        @Override
                        public void OnError(ErrorModel erro)
                        {
                            DialogAviso dlgAviso = new DialogAviso(FrmEsqueceuSenha.this, erro.getMensagemErro());
                            dlgLoad.dismiss();
                            dlgAviso.show();
                        }
                    };

                    loginRequest.ConfereCod(sCodRec, cdUser, Ultil.retornaTokenAuth(this),callBack);
                }
            }
            else if(pos == 2)
            {
                if (validaCampos(2)) {
                    dlgLoad.show();

                    sSenha = txtSenha.getText().toString().trim();

                    callBack = new RetornaStringCallback() {
                        @Override
                        public void onSucess(String sucesso) {
                            dlgLoad.dismiss();
                            DialogAvisoSucesso dlgAviso = new DialogAvisoSucesso(FrmEsqueceuSenha.this, "Sua senha foi alterada com sucesso!", FrmEsqueceuSenha.this);
                            dlgAviso.show();
                        }

                        @Override
                        public void OnError(ErrorModel erro) {
                            DialogAviso dlgAviso = new DialogAviso(FrmEsqueceuSenha.this, erro.getMensagemErro());
                            dlgLoad.dismiss();
                            dlgAviso.show();
                        }
                    };

                    AlteraSenha novaSenha = new AlteraSenha();
                    novaSenha.setCdVerificador(sCodRec);
                    novaSenha.setDsNovaSenha(sSenha);
                    novaSenha.setIdUsuario(cdUser);

                    loginRequest.AlteraSenha(novaSenha, Ultil.retornaTokenAuth(this), callBack);
                }
            }
        }
        catch (Exception err)
        {
            Ultil.ExibeSnack("Erro inesperado, tente novamente mais tarde", lnlPrincipal);
        }
    }

    public boolean validaCampos(int pos)
    {
        DialogAviso dlgAviso;

        if (pos == 0)
        {
            if (txtEmail.getText().toString().trim().equals(""))
            {
                dlgAviso = new DialogAviso(this, "E-mail inválida" );
                dlgAviso.show();
                return false;
            }

            if(!txtEmail.getText().toString().contains("@")|| !txtEmail.getText().toString().contains(".com"))
            {
                dlgAviso = new DialogAviso(this, "E-mail inválida" );
                dlgAviso.show();
                return false;
            }
        }
        else if(pos == 1)
        {
            if (txtCodRec.getText().toString().trim().equals(""))
            {
                dlgAviso = new DialogAviso(this, "Nenhum código foi inserido");
                dlgAviso.show();
                return false;
            }
        }
        else if (pos == 2)
        {
            if (txtSenha.getText().toString().trim().length() < 8)
            {
                dlgAviso = new DialogAviso(this, "A senha precisa conter no mínimo\n 8 caracteres");
                dlgAviso.show();
                return false;
            }

            if(!txtSenha.getText().toString().trim().equals(txtSenhaRep.getText().toString().trim()))
            {
                dlgAviso = new DialogAviso(this, "As senhas não conferem");
                dlgAviso.show();
                return false;
            }
        }

        return true;
    }
}
