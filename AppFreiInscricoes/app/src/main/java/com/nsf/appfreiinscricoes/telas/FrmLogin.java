package com.nsf.appfreiinscricoes.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.requestsAPI.LoginRequests;
import com.nsf.appfreiinscricoes.firebase.FirebaseIdManeger;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaLoginCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbLogin;
import com.nsf.appfreiinscricoes.model.TbUsuario;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogAviso;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogLoad;

public class FrmLogin extends AppCompatActivity {

    private LinearLayout btnEntrar;
    private EditText txtSenha;
    private EditText txtUsuario;
    private TextView lblRecSenha;
    private LinearLayout lnlCadastrar;
    private DialogLoad dialogLoad;
    private DialogAviso dlgAviso;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_login);

        RecuperarControles();
        configuraDialog();
        clicks();
    }

    private void RecuperarControles()
    {
        txtSenha = findViewById(R.id.txtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        txtUsuario = findViewById(R.id.txtUsuario);
        lblRecSenha = findViewById(R.id.lblRecSenha);
        lnlCadastrar = findViewById(R.id.lnlCadastrar);
        lblRecSenha = findViewById(R.id.lblRecSenha);
        layout = findViewById(R.id.layout);
    }

    public void clicks()
    {
        lnlCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FrmLogin.this, FrmCadastrarLogin.class);
                startActivity(intent);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                enviaDados();
            }
        });

        lblRecSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrmLogin.this, FrmEsqueceuSenha.class);
                startActivity(intent);
            }
        });
    }

    public void configuraDialog()
    {
        dialogLoad = new DialogLoad(getApplicationContext());
        dialogLoad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLoad.setCancelable(false);
    }

    public boolean validacoes()
    {
        if(txtUsuario.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O campo usuário deve ser preenchido");
            return false;
        }

        if(txtSenha.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O campo senha deve ser preenchido");
            return false;
        }

        if(!txtUsuario.getText().toString().contains("@") || !txtUsuario.getText().toString().contains(".com"))
        {
            dlgAviso = new DialogAviso(this, "E-mail inválido");
            return false;
        }

        return true;
    }

    public void enviaDados()
    {
        try
        {
            if(validacoes())
            {
                DialogLoad dlgLoad = new DialogLoad(this);
                dlgLoad.show();

                TbLogin login = new TbLogin();

                login.setDsEmail(txtUsuario.getText().toString());
                login.setDsSenha(Ultil.GerarMD5(txtSenha.getText().toString()).toLowerCase());

                RetornaLoginCallback loginCallback = new RetornaLoginCallback() {
                    @Override
                    public void onSucess(TbUsuario codigo)
                    {
                        Intent intent = new Intent(FrmLogin.this , MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);

                        codigo.setDsEmail(txtUsuario.getText().toString().trim());
                        Ultil.salvaLogin(codigo, FrmLogin.this);
                        dlgLoad.dismiss();
                        finish();
                    }

                    @Override
                    public void OnError(ErrorModel erro)
                    {
                        DialogAviso aviso = new DialogAviso(FrmLogin.this, erro.getMensagemErro());
                        dlgLoad.dismiss();

                        aviso.show();
                    }
                };

                RetornaStringCallback callback = new RetornaStringCallback() {
                    @Override
                    public void onSucess(String token)
                    {
                        login.setToken(token);
                        Ultil.salvaToken(FrmLogin.this, token);

                        LoginRequests request = new LoginRequests();
                        request.ValidaLogin(login, loginCallback);
                    }

                    @Override
                    public void OnError(ErrorModel erro)
                    {
                        throw new IllegalArgumentException("erro");
                    }
                };

                FirebaseIdManeger fbId = new FirebaseIdManeger();
                fbId.retornaToken(callback);
            }
            else
            {
                dlgAviso.show();
            }
        }
        catch (Exception err)
        {
            Ultil.ExibeSnack("Erro inesperado, tente novamente mais tarde", layout);
        }
    }
}
