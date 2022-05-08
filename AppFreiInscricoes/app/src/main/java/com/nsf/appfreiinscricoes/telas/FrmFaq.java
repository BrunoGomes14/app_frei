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
import com.nsf.appfreiinscricoes.requestsAPI.FaqRequest;
import com.nsf.appfreiinscricoes.adapters.FaqAdapter;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaFaqCallback;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbFaq;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class FrmFaq extends AppCompatActivity
{
    RecyclerView rvFaq;
    ArrayList<TbFaq> faqs;
    Toolbar toolbar;
    ActionBar actionbar;
    ShimmerFrameLayout mShimmerViewContainer;
    LinearLayout errorConnection;
    LinearLayout lnlTenteNovamente;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_faq);
        recuperaControles();
        ConfiguraToolbar();
        configuraRecyclerView();
        tentarNovamente();
    }

    public void recuperaControles()
    {
        rvFaq = (RecyclerView) findViewById(R.id.rvFaq);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        errorConnection = findViewById(R.id.errorConection);
        lnlTenteNovamente = findViewById(R.id.lnlTentarNovamente);
    }

    public void configuraRecyclerView()
    {
        try
        {
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mShimmerViewContainer.startShimmerAnimation();

            RetornaFaqCallback faqCallback = new RetornaFaqCallback() {
                @Override
                public void onSuccess(ArrayList<TbFaq> faqs)
                {
                    FaqAdapter adapter = new FaqAdapter(faqs, FrmFaq.this);
                    rvFaq.setAdapter(adapter);

                    LinearLayoutManager manager = new LinearLayoutManager(FrmFaq.this, RecyclerView.VERTICAL, false);
                    rvFaq.setLayoutManager(manager);

                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }

                @Override
                public void onError(ErrorModel error)
                {
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    errorConnection.setVisibility(View.VISIBLE);
                }
            };

            FaqRequest faqRequest = new FaqRequest();
            faqRequest.ListaFaqs(faqCallback, Ultil.retornaTokenAuth(this));
        }
        catch (Exception err)
        {
            Ultil.ExibeSnack("Erro inesperado, tente novamente mais tarde", rvFaq);
        }
    }

    public void ConfiguraToolbar()
    {
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Branco));
        toolbar.setTitle("FAQ");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId)
        {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_in);
                return true;
        }
        return true;
    }

    public void tentarNovamente()
    {
        lnlTenteNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorConnection.setVisibility(View.GONE);
                configuraRecyclerView();

            }
        });
    }
}
