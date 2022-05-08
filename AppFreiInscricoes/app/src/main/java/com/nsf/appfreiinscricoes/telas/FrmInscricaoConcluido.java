package com.nsf.appfreiinscricoes.telas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nsf.appfreiinscricoes.R;

public class FrmInscricaoConcluido extends AppCompatActivity {

    private ImageView img;
    private Toolbar toolbar;
    private TextView lblCodInscricao;
    private LinearLayout btnAbrirInfo;
    ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_inscricao_concluido);

        RecuperaControle();
        configureToolbar();
        CarregaValores();
    }

    public void RecuperaControle()
    {
        img  = (ImageView) findViewById(R.id.img_concluido);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lblCodInscricao = (TextView) findViewById(R.id.lblCodInscricao);
        btnAbrirInfo = (LinearLayout) findViewById(R.id.btnAbrirInfo);
    }

    private void configureToolbar() {

        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Branco));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_in);
        }
        return true;
    }

    public void CarregaValores()
    {
        Glide.with(this).asGif().load(R.drawable.gif_blue_hourglass).listener(new RequestListener<GifDrawable>() {
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

        Bundle extra = getIntent().getExtras();
        int cd = extra.getInt("nrInscricao");

        lblCodInscricao.setText("Código de inscrição:\n" + cd);

        btnAbrirInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FrmInscricaoConcluido.this, FrmInformacoesInscricao.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
