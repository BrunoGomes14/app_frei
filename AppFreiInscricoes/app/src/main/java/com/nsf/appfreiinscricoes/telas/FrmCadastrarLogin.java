package com.nsf.appfreiinscricoes.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.requestsAPI.LoginRequests;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.Requests.LoginRequestModel;
import com.nsf.appfreiinscricoes.model.TbLogin;
import com.nsf.appfreiinscricoes.model.UsuarioCadastro;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogAviso;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogLoad;
import com.nsf.appfreiinscricoes.ultil.Ultil;

import java.util.Calendar;
import java.util.Date;

public class FrmCadastrarLogin extends AppCompatActivity {


    private EditText txtCPF;
    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtSenha;
    private DialogLoad dlgLoad;
    private Button btnCadastrar;
    private EditText txtConfirmaSenha;
    private TextView txtDataNascimento;
    private LinearLayout lnlPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_cadastrar);

        recuperaControles();
        dlgLoad = new DialogLoad(this);
        EventosClick();
    }

    public void recuperaControles()
    {
        txtCPF = (EditText) findViewById(R.id.txtCPF);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        txtConfirmaSenha = (EditText) findViewById(R.id.txtConfirmaSenha);
        txtDataNascimento = (TextView) findViewById(R.id.txtDataNascimento);
        lnlPrincipal = findViewById(R.id.lnlPrincipal);
    }

    public void enviaDados()
    {
        dlgLoad.show();
        TbLogin login = new TbLogin();

        try
        {
            login.setDsEmail(txtEmail.getText().toString().trim());
            login.setDsSenha(txtSenha.getText().toString().trim());

            UsuarioCadastro usuario = new UsuarioCadastro();
            usuario.setNmUsuario(txtNome.getText().toString().trim());
            usuario.setDsCpf(txtCPF.getText().toString().trim());
            usuario.setDtNascimento(Ultil.formataDataConvencionalParaSQL(txtDataNascimento.getText().toString().trim()));

            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setLogin(login);
            loginRequestModel.setUsuario(usuario);

            RetornaStringCallback callback = new RetornaStringCallback() {
                @Override
                public void onSucess(String sucesso)
                {
                    dlgLoad.dismiss();
                    Intent intent = new Intent(FrmCadastrarLogin.this, FrmCadastroLoginConcluido.class);
                    intent.putExtra("login", loginRequestModel);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                }

                @Override
                public void OnError(ErrorModel erro)
                {
                    dlgLoad.dismiss();
                    DialogAviso aviso = new DialogAviso(FrmCadastrarLogin.this, erro.getMensagemErro());
                    aviso.show();
                }
            };

            LoginRequests loginRequests = new LoginRequests();
            loginRequests.InserirLogin(loginRequestModel, callback);
        }
        catch (Exception ex)
        {
            Ultil.ExibeSnack("Erro inesperado, tente novamente", lnlPrincipal);
        }
    }

    public void validacoes()
    {
        Context context = FrmCadastrarLogin.this;

        try
        {
            if(txtNome.getText().toString().trim().isEmpty())
            {
                throw new Exception("O nome não pode ser vazio!");
            }

            if(txtCPF.getText().toString().trim().isEmpty())
            {
                throw new Exception("O CPF não pode ser vazio!");
            }

            if(txtDataNascimento.getText().toString().trim().isEmpty())
            {
                throw new Exception("Selecione a sua data de nascimento!");
            }

            if(!Ultil.validaData(txtDataNascimento.getText().toString().trim()))
            {
                throw new Exception("Data de nascimento inválida");
            }

            if(txtEmail.getText().toString().trim().isEmpty()) {
                throw new Exception("O E-mail não pode ser vazio!");
            }

            if (txtSenha.getText().toString().trim().isEmpty() || txtConfirmaSenha.getText().toString().isEmpty())
            {
                throw new Exception("Escolha uma senha!");
            }

            if(!txtSenha.getText().toString().trim().equals(txtConfirmaSenha.getText().toString()))
            {
                throw new Exception("As senhas não conferem!");
            }

            if (txtSenha.getText().toString().contains("&") || txtSenha.getText().toString().contains("&"))
            {
                throw new Exception("A senha possui caracteres inválidos");
            }

            enviaDados();
        }
        catch (Exception ex)
        {
            Dialog dlg = new DialogAviso(context, ex.getMessage());
            dlg.show();
        }
    }

    public void EventosClick()
    {
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validacoes();
            }
        });
    }
}
