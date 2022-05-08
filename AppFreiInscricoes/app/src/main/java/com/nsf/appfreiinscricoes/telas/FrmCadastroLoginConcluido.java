package com.nsf.appfreiinscricoes.telas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.firebase.FirebaseIdManeger;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaLoginCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.Requests.LoginRequestModel;
import com.nsf.appfreiinscricoes.model.TbLogin;
import com.nsf.appfreiinscricoes.model.TbUsuario;
import com.nsf.appfreiinscricoes.requestsAPI.LoginRequests;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogAviso;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogLoad;

public class FrmCadastroLoginConcluido extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_cadastro_login_concluido);

        ImageView img = (ImageView) findViewById(R.id.img_concluido);

        Glide.with(this).asGif().load(R.drawable.gif_finished_cadastro).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);
                return false;
            }
        }).into(img);

        LinearLayout btnVoltar = (LinearLayout) findViewById(R.id.btnVoltarInicio);
        btnVoltar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        LoginRequestModel loginRequestModel = (LoginRequestModel)getIntent().getSerializableExtra("login");

        TbLogin login = new TbLogin();
        login.setDsEmail(loginRequestModel.getLogin().getDsEmail());
        login.setDsSenha(Ultil.GerarMD5(loginRequestModel.getLogin().getDsSenha()).toLowerCase());

        DialogLoad dlgLoad = new DialogLoad(this);
        dlgLoad.show();

        RetornaLoginCallback loginCallback = new RetornaLoginCallback() {
            @Override
            public void onSucess(TbUsuario codigo)
            {
                Intent intent = new Intent(FrmCadastroLoginConcluido.this , MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);

                codigo.setDsEmail(login.getDsEmail());
                Ultil.salvaLogin(codigo, FrmCadastroLoginConcluido.this);
                dlgLoad.dismiss();
                finish();
            }

            @Override
            public void OnError(ErrorModel erro)
            {
                DialogAviso aviso = new DialogAviso(FrmCadastroLoginConcluido.this, erro.getMensagemErro());
                dlgLoad.dismiss();
                aviso.show();
            }
        };

        RetornaStringCallback callback = new RetornaStringCallback() {
            @Override
            public void onSucess(String token)
            {
                login.setToken(token);
                Ultil.salvaToken(FrmCadastroLoginConcluido.this, token);

                LoginRequests request = new LoginRequests();
                request.ValidaLogin(login, loginCallback);
            }

            @Override
            public void OnError(ErrorModel erro)
            {
                DialogAviso aviso = new DialogAviso(FrmCadastroLoginConcluido.this, erro.getMensagemErro());
                dlgLoad.dismiss();
                aviso.show();
            }
        };

        FirebaseIdManeger fbId = new FirebaseIdManeger();
        fbId.retornaToken(callback);
    }
}
