package com.nsf.appfreiinscricoes.telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogAviso;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class FrmNavegador extends AppCompatActivity
{
    private ActionBar actionbar;
    Toolbar toolbar;
    WebView wvPagina;
    ImageView imgLoad;
    LinearLayout lnlLoad;
    LinearLayout lnlSemConexao;
    LinearLayout lnlVideo;
    YouTubePlayerView yt;
    ImageView imgVoltar;
    boolean isVideo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_navegador);

        recuperaControles();
        configuraToolbar();
        carregaPagina();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_in);
        }

        return true;
    }

    public void configuraToolbar()
    {
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Branco));
        //toolbar.setTitle("Prova");
    }

    public void recuperaControles()
    {
        toolbar = findViewById(R.id.toolbar);
        wvPagina = findViewById(R.id.wvPagina);
        lnlLoad = findViewById(R.id.lnlImg);
        imgLoad = findViewById(R.id.imgLoad);
        yt = findViewById(R.id.youtube);
        lnlVideo = findViewById(R.id.lnlVideo);
        imgLoad = findViewById(R.id.imgLoad);
        imgVoltar = findViewById(R.id.imgVoltar);

        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void carregaPagina()
    {
        try
        {
            Bundle extras = getIntent().getExtras();
            String url = extras.getString("url", "");
            String titulo = extras.getString("title", "Navegador");
            String frase = extras.getString("frase", "Vídeo");
            boolean bIsNav = extras.getBoolean("isNav", true);

            toolbar.setTitle(titulo);

            if(url.length() > 0)
            {
                if (bIsNav)
                {
                    abreNavegador(url);
                }
                else
                {
                    isVideo = true;
                    abreVideo(url, frase);
                }
            }
            else {
                finish();
            }
        }
        catch (Exception err)
        {
            DialogAviso dlgAviso = new DialogAviso(this, "Um erro inesperado ocorreu, tente novamente mais tarde.", this);
            dlgAviso.show();
        }
    }

    public void abreNavegador(String url)
    {
        toolbar.setVisibility(View.VISIBLE);
        lnlVideo.setVisibility(View.GONE);

        Glide.with(this).load(R.drawable.load_principal).into(imgLoad);
        wvPagina.setVisibility(View.GONE);
        lnlLoad.setVisibility(View.VISIBLE);

        if (Ultil.isNetworkAvailable(this))
        {
            wvPagina.getSettings().setJavaScriptEnabled(true);
            wvPagina.getSettings().setLoadWithOverviewMode(true);
            wvPagina.getSettings().setUseWideViewPort(true);
            wvPagina.setWebViewClient(new WebViewClient()
            {
                @Override
                public void onPageFinished(WebView view, String url)
                {
                    lnlLoad.setVisibility(View.GONE);
                    wvPagina.setVisibility(View.VISIBLE);
                }
            });

            wvPagina.loadUrl(url);
            wvPagina.setVisibility(View.GONE);
        }
        else
        {
            wvPagina.setVisibility(View.GONE);
            lnlSemConexao.setVisibility(View.VISIBLE);
        }
    }

    String id;
    YouTubePlayer ytPlayer;

    public void abreVideo(String url, String frase)
    {
        toolbar.setVisibility(View.GONE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LOW_PROFILE);

        yt.enterFullScreen();

        lnlVideo.setVisibility(View.VISIBLE);
        wvPagina.setVisibility(View.GONE);
        lnlLoad.setVisibility(View.GONE);
        id = url.contains("=")? url.split("=")[1] : url.split("/")[3];

        getLifecycle().addObserver(yt);
        yt.addYouTubePlayerListener(new YouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(id, 0);

                ytPlayer = youTubePlayer;
            }

            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState playerState)
            {
                if (playerState == PlayerConstants.PlayerState.ENDED)
                {
                    finish();
                }
            }

            @Override
            public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {

            }

            @Override
            public void onPlaybackRateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {

            }

            @Override
            public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError playerError) {

            }

            @Override
            public void onCurrentSecond(YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoDuration(YouTubePlayer youTubePlayer, float v)
            {
            }

            @Override
            public void onVideoLoadedFraction(YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoId(YouTubePlayer youTubePlayer, String s) {

            }

            @Override
            public void onApiChange(YouTubePlayer youTubePlayer) {

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();

        if (ytPlayer != null)
        {
            ytPlayer.pause();
        }
    }

}
