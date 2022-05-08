package com.nsf.appfreiinscricoes.telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.requestsAPI.NotificacaoRequest;
import com.nsf.appfreiinscricoes.adapters.AdapterNotificacoes;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaNotificacaoCallback;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbNotificacao;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class FrmNotificacoes extends AppCompatActivity {

    RecyclerView rvNotificacoes;
    Toolbar toolbar;
    ActionBar actionbar;
    ShimmerFrameLayout mShimmerViewContainer;
    LinearLayout lnlPrincipal;
    LinearLayout errorConection;
    LinearLayout lnlTentarNovamente;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_notificacoes);

        RecuperaControles();
        configuraToolbar();
        configuraRv();
        tentarNovamente();
    }

    public void RecuperaControles()
    {
        toolbar = findViewById(R.id.toolbar);
        rvNotificacoes = findViewById(R.id.rvNotificacoes);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        lnlPrincipal = findViewById(R.id.lnlPrincipal);
        errorConection = findViewById(R.id.errorConection);
        lnlTentarNovamente = findViewById(R.id.lnlTentarNovamente);
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
        toolbar.setTitle("Notificações");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId)
        {
            case android.R.id.home:
                finish();
                return true;
        }

        return true;
    }

    public void configuraRv()
    {
        try
        {
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mShimmerViewContainer.startShimmerAnimation();
            rvNotificacoes.setVisibility(View.GONE);

            RetornaNotificacaoCallback callback = new RetornaNotificacaoCallback() {
                @Override
                public void onSucess(ArrayList<TbNotificacao> notificacoes)
                {
                    AdapterNotificacoes adapterNotificacoes = new AdapterNotificacoes(FrmNotificacoes.this, notificacoes);
                    LinearLayoutManager manager = new LinearLayoutManager(FrmNotificacoes.this, RecyclerView.VERTICAL, false);

                    rvNotificacoes.setAdapter(adapterNotificacoes);
                    rvNotificacoes.setLayoutManager(manager);

                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    rvNotificacoes.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(ErrorModel erro)
                {
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    rvNotificacoes.setVisibility(View.GONE);
                    errorConection.setVisibility(View.VISIBLE);
                }
            };

            NotificacaoRequest notificacaoRequest = new NotificacaoRequest();
            notificacaoRequest.retornaNotificacoes(Ultil.recuperaDadosUsuario(this).getIdUsuario(), callback, Ultil.retornaTokenAuth(this));
        }
        catch (Exception err)
        {
            Ultil.ExibeSnack("Erro inesperado, tente novamente mais tarde", lnlPrincipal);
        }
    }

    public void tentarNovamente()
    {
        lnlTentarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorConection.setVisibility(View.GONE);
                configuraRv();
            }
        });
    }
}
