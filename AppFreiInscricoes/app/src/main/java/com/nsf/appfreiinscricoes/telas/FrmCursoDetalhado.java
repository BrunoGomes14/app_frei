package com.nsf.appfreiinscricoes.telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.adapters.PeriodosAdapter;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.Requests.TbCursoRequest;
import com.nsf.appfreiinscricoes.model.TbMaterias;
import com.nsf.appfreiinscricoes.model.TbPeriodos;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class FrmCursoDetalhado extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionbar;
    private ImageView imgCurso;
    private TextView lblTextoCurso;
    private CollapsingToolbarLayout collapsingToolbar;
    private BottomNavigationView bottomNavigationView;
    private LinearLayout layoutVisaoGeral;
    private LinearLayout layoutMaisInfo;
    private TextView lblConteudo;
    private TextView lblCargaHoraria;
    private TextView lblEscolaridadeMinima;
    private TextView lblTpCurso;
    private TextView lblMercado;
    private TextView lblIdadeMinima;
    private LinearLayout lnlVideoApresentacao;
    private LinearLayout lnlMaterias;
    private LinearLayout lnlPeriodos;
    private LinearLayout lnlMercado;
    private FloatingActionButton fabVideo;
    private AppBarLayout appBarLayout;
    private TextView lblContribuicao;
    private RecyclerView rvPeriodos;
    private TextView lblIdadeMaxima;
    private LinearLayout lnlTituloMercado;
    private LinearLayout lnlTituloDisciplina;
    boolean appBarExpanded;
    boolean temVideo;
    Menu collapsedMenu;
    String url;
    String nmCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_curso_detalhado);

        recuperaControles();
        configuraToolbar();
        configuraBottonNav();
        exibeInfoCurso();
        escondeFab();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_in);
    }

    public void recuperaControles()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lblTextoCurso = (TextView) findViewById(R.id.lblTextoCurso);
        imgCurso = (ImageView) findViewById(R.id.imgCurso);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        layoutMaisInfo = (LinearLayout) findViewById(R.id.layoutMaisInfo);
        layoutVisaoGeral = (LinearLayout) findViewById(R.id.layoutVisaoGeral);
        lblConteudo = (TextView) findViewById(R.id.lblConteudo);
        lblCargaHoraria = (TextView) findViewById(R.id.lblCargaHoraria);
        lblTpCurso = (TextView) findViewById(R.id.lblTpCurso);
        lblIdadeMinima = (TextView) findViewById(R.id.lblIdadeMinima);
        lblMercado = findViewById(R.id.lblMercado);
        lnlMaterias = findViewById(R.id.lnlMateria);
        lnlPeriodos = findViewById(R.id.lnlPeriodo);
        lnlMercado = findViewById(R.id.lnlMercado);
        fabVideo = findViewById(R.id.fabVideo);
        appBarLayout = findViewById(R.id.appBarLayout);
        lblEscolaridadeMinima = findViewById(R.id.lblEscolaridadeMinima);
        lblContribuicao = findViewById(R.id.lblContribuicao);
        rvPeriodos = findViewById(R.id.rvPeriodos);
        lblIdadeMaxima = findViewById(R.id.lblIdadeMaxima);
        lnlTituloDisciplina = findViewById(R.id.lnlTituloDisciplina);
        lnlTituloMercado = findViewById(R.id.lnlTituloMercado);
    }

    public void configuraToolbar()
    {
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.Branco));
        //collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
    }

    public void configuraBottonNav()
    {
        layoutMaisInfo.setVisibility(View.GONE);
        layoutVisaoGeral.setVisibility(View.VISIBLE);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.visaoGeral:
                        layoutMaisInfo.setVisibility(View.GONE);
                        layoutVisaoGeral.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.maisInfo:
                        layoutMaisInfo.setVisibility(View.VISIBLE);
                        layoutVisaoGeral.setVisibility(View.GONE);
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_in);
                return true;
        }

        if(item.getTitle().equals("youtube"))
        {
            AbrirVideo();
        }
        return true;
    }

    @SuppressLint("RestrictedApi")
    public void exibeInfoCurso()
    {
        TbCursoRequest curso = (TbCursoRequest) getIntent().getSerializableExtra("curso");

        nmCurso = curso.curso.nmCurso;
        collapsingToolbar.setTitle(curso.curso.nmCurso);
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.Branco));
        lblTextoCurso.setText(curso.curso.getNmCurso());

        if(curso.materiasCurso.size() > 0)
        {
            String materias = "";
            for (TbMaterias materia : curso.materiasCurso)
            {
                materias += "• " + materia.nmMateria + "<br>";
            }

            lblConteudo.setText(Html.fromHtml(materias));
        }
        else
        {
            lnlTituloDisciplina.setVisibility(View.GONE);
            lnlMaterias.setVisibility(View.GONE);
        }

        if(curso.periodosCurso.size() > 0)
        {
            PeriodosAdapter adapter = new PeriodosAdapter(curso.periodosCurso, this);
            LinearLayoutManager manager = new LinearLayoutManager(this);

            rvPeriodos.setAdapter(adapter);
            rvPeriodos.setLayoutManager(manager);
        }
        else
        {
            lnlPeriodos.setVisibility(View.GONE);
        }

        if(curso.detalhesCurso.dsMercado.trim().length() > 0)
        {
            lblMercado.setText(Html.fromHtml(curso.detalhesCurso.dsMercado));
        }
        else
        {
            lnlTituloMercado.setVisibility(View.GONE);
            lnlMercado.setVisibility(View.GONE);
        }

        lblEscolaridadeMinima.setText(Html.fromHtml(curso.detalhesCurso.dsEscolaridadeMinima));
        lblIdadeMinima.setText(Html.fromHtml(curso.detalhesCurso.nrIdadeMinima));
        lblIdadeMaxima.setText(Html.fromHtml(curso.detalhesCurso.nrIdadeMaxima));
        lblCargaHoraria.setText(Html.fromHtml(curso.detalhesCurso.dsCargaHoraria));
        lblTextoCurso.setText(Html.fromHtml(curso.detalhesCurso.dsVisaoGeral));
        lblTpCurso.setText("Curso " + curso.tpCurso.tpCurso);
        lblContribuicao.setText(curso.detalhesCurso.dsContribuicaoMensal);

        url = curso.detalhesCurso.linkVideoApresentacao;

        PointF pf = new PointF(0.5f, 0.5f);
        float[] cor = new float[]{0.0f, 0.0f, 0.0f};
        float inicio = 0.0f;
        float fim = 0.9f;

        VignetteFilterTransformation tfEffect = new VignetteFilterTransformation(pf, cor, inicio, fim);

        String url = RotasAPI.URL_BASE + "Cursos/getImgCurso/" + curso.curso.idImagem;

        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(bitmapTransform(tfEffect))
                .timeout(300000)
                .into(imgCurso);

        if (curso.detalhesCurso.linkVideoApresentacao != null)
        {
            temVideo = true;
            fabVideo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AbrirVideo();
                }
            });
        }
        else
        {
            fabVideo.setVisibility(View.GONE);
            temVideo = false;
        }
    }

    public void AbrirVideo()
    {
        Intent intent = new Intent(FrmCursoDetalhado.this, FrmNavegador.class);
        intent.putExtra("url", url);
        intent.putExtra("title", nmCurso);
        intent.putExtra("isNav", false);
        intent.putExtra("frase", "Vídeo sobre nosso curso de " + nmCurso);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(temVideo)
        {
            getMenuInflater().inflate(R.menu.mnu_curso, menu);
            collapsedMenu = menu;
            menu.getItem(0).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(temVideo)
        {
            if (collapsedMenu != null
                    && (!appBarExpanded || collapsedMenu.size() != 1)) {
                //collapsed
                collapsedMenu.add("youtube")
                        .setIcon(R.drawable.ic_youtube)
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            }

            return super.onPrepareOptionsMenu(collapsedMenu);
        }
        return true;
    }

    public void escondeFab()
    {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i)
            {
                if (Math.abs(i) > 200) {
                    fabVideo.setVisibility(View.GONE);
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    exibeYoutube();
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });
    }

    @SuppressLint("RestrictedApi")
    public void exibeYoutube()
    {
        if(temVideo)
        {
            fabVideo.setVisibility(View.VISIBLE);
        }
    }
}
