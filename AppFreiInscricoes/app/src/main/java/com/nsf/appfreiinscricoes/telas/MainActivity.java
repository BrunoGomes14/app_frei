package com.nsf.appfreiinscricoes.telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaInitCallback;
import com.nsf.appfreiinscricoes.model.InitResponse;
import com.nsf.appfreiinscricoes.model.TbUsuario;
import com.nsf.appfreiinscricoes.requestsAPI.InscricaoRequest;
import com.nsf.appfreiinscricoes.requestsAPI.LoginRequests;
import com.nsf.appfreiinscricoes.requestsAPI.PeriodoInscricoesRequets;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaCursosCallbacks;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaInscricaoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaPeriodoInscricaoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaSituacaoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.TbCodigo;
import com.nsf.appfreiinscricoes.model.TbPeridoInscricao;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogInformacao;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogLoad;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogSair;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private LinearLayout layoutInscricao;
    private DrawerLayout drawerLayout;
    private ActionBar actionbar;
    private NavigationView navView;
    private LinearLayout layout_cursos;
    private LinearLayout layout_instituto;
    private LinearLayout layout_status;
    private DialogLoad dialogLoad;
    private RetornaCursosCallbacks callbacks;
    private TextView lblNomeUsuario;
    private TextView lblCPFUsuario;
    private TextView lblBoasVindas;
    private LinearLayout layout_faq;
    private LinearLayout lnlInscricoesStart;
    private TextView lblInscricao;
    private TextView lblPeriodo;
    private ImageView imgInscricao;
    private ImageView imgStatusInscricao;
    private LinearLayout lnlAbertura;
    private TextView lblFraseInscricao;
    private LinearLayout layoutPrincipal;
    private ImageView imgLoad;
    private ImageView imgAbertura;
    private ImageView imgFaq;
    private ImageView imgCursos;
    LinearLayout lnlLoad;
    MenuItem menuItem;

    TextView textItemCount;

    // define se possui conexão no app
    boolean bConexao;

    // se pode clicar na inscricao
    boolean inscricaoClicavel;

    // se tem palestra
    boolean aberturaClicave;

    // se pode abrir a inscrição
    static boolean statusClicavel;
    static boolean realizouInsc = false;

    // qual motivo não pode abrir a inscricao
    String motivoInscricao;
    String linkPalestra;

    // qtd de requests processando
    int qtdRequests = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            recuperaControles();
            configureNavigationDrawer();
            configureToolbar();
            IniciaLayout();

            if (Ultil.isNetworkAvailable(this))
            {
                bConexao = true;
                VerificaExistenciaInscricao(false);
                VerificaSituacao();
            }
            else
            {
                bConexao = false;
                modoOffline();
            }
        }
        catch (Exception err)
        {
            Ultil.ExibeSnack("Erro inesperado tente novamente mais tarde", drawerLayout);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        botoesAtivos();
        VerificaExistenciaInscricao(true);

        if (realizouInsc)
        {
            FrmInscricao.isAlterando = true;
        }

        if (!bConexao)
        {
            SnackSemConexao();
        }
    }

    public void recuperaControles() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.navigation);
        layout_cursos = (LinearLayout) findViewById(R.id.layout_cursos);
        layoutInscricao = (LinearLayout) findViewById(R.id.layoutInscricao);
        layout_instituto = (LinearLayout) findViewById(R.id.layout_instituto);
        layout_status = (LinearLayout) findViewById(R.id.layout_status);
        layout_faq = (LinearLayout) findViewById(R.id.layout_faq);
        dialogLoad = new DialogLoad(this);
        lblBoasVindas = findViewById(R.id.lblBoasVindas);
        lblInscricao = findViewById(R.id.lblInscricao);
        lblPeriodo = findViewById(R.id.lblInscricoesAbertas);
        lnlInscricoesStart = findViewById(R.id.lnlInscricoesStart);
        imgInscricao = findViewById(R.id.imgInscricao);
        imgStatusInscricao = findViewById(R.id.imgStatusInscricao);
        lnlAbertura = findViewById(R.id.lnlAbertura);
        lblFraseInscricao = findViewById(R.id.lblFraseInscricao);
        layoutPrincipal = findViewById(R.id.layoutPrincipal);
        imgLoad = findViewById(R.id.imgLoad);
        lnlLoad = findViewById(R.id.layoutLoad);
        imgAbertura = findViewById(R.id.imgAbertura);
        imgFaq = findViewById(R.id.imgFaq);
        imgCursos = findViewById(R.id.imgCursos);

        lnlAbertura.setOnClickListener(this);
        layout_cursos.setOnClickListener(this);
        layoutInscricao.setOnClickListener(this);
        layout_instituto.setOnClickListener(this);
        layout_status.setOnClickListener(this);
        layout_faq.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mnu_home, menu);
        menuItem = menu.getItem(0).setVisible(false);

        final MenuItem menuItem = menu.findItem(R.id.notify);

        View actionView = menuItem.getActionView();
        actionView.setOnClickListener(this);
        textItemCount = (TextView) actionView.findViewById(R.id.notf_badge);

        return true;
    }

    private void configureToolbar() {

        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Branco));

        String nome = Ultil.recuperaDadosUsuario(this).getNmUsuario().split(" ")[0];
        lblBoasVindas.setText(nome);
    }

    private void configureNavigationDrawer()
    {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                switch (menuItem.getItemId()) {
                    case R.id.home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.sair:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        DialogSair dialog = new DialogSair(MainActivity.this, MainActivity.this, layoutPrincipal);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        break;
                }
                return true;
            }
        });

        View headerView = navView.getHeaderView(0);
        lblNomeUsuario = headerView.findViewById(R.id.lblNomeUsuario);
        lblNomeUsuario.setText(Ultil.recuperaDadosUsuario(this).getNmUsuario());
        lblCPFUsuario = headerView.findViewById(R.id.lblCPF);
        lblCPFUsuario.setText(Ultil.recuperaDadosUsuario(this).getDsEmail());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent intent = new Intent(MainActivity.this, FrmNotificacoes.class);

        switch (itemId)
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.notify:
                textItemCount.setVisibility(View.GONE);
                startActivity(intent);
                return true;
        }

        return true;
    }


    public boolean VerificaExistenciaInscricao(boolean verificaSomenteInterno)
    {
        if (Ultil.recuperaInfoInscricao(this).getNrCod() > 0 && bConexao)
        {
            statusClicavel = true;
            DefineClicavel(layout_status, imgStatusInscricao);
        }
        else if(!Ultil.recuperaDadosInscricao(this).getDsCursoPrimeiro().trim().isEmpty())
        {
            statusClicavel = true;
            DefineClicavel(layout_status, imgStatusInscricao);
        }
        else if (verificaSomenteInterno)
        {
            return false;
        }
        else if (Ultil.recuperaDadosUsuario(this).getIdCodigo() > 0)
        {
            carregaInscricao(Ultil.recuperaDadosUsuario(this).getIdCodigo());
        }
        else if (!Ultil.isFirstAcess(this))
        {
            carregaInscricao();
            DefineNaoClicavel(layout_status, imgStatusInscricao);
        }

        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialogLoad.dismiss();
        botoesAtivos();
    }

    public void VerificaSituacao()
    {
        DefineNaoClicavel(layoutInscricao, imgInscricao);

        Glide.with(this).load(R.drawable.load_principal).into(imgLoad);
        lnlLoad.setVisibility(View.VISIBLE);
        layoutPrincipal.setVisibility(View.GONE);

        qtdRequests += 1;
        RetornaInitCallback callback = new RetornaInitCallback() {
            @Override
            public void onSuccess(InitResponse response)
            {
                TrataRetorno(response);
                RequestFinalizou();
            }

            @Override
            public void onError(ErrorModel error)
            {
                RequestFinalizou();
                modoOffline();
            }
        };

        LoginRequests requests = new LoginRequests();
        TbUsuario usuario = Ultil.recuperaDadosUsuario(this);
        requests.RetornaInit(callback, usuario.getIdUsuario(), Ultil.retornaTokenAuth(this));
    }

    public void carregaInscricao(int idInscricao)
    {
        InscricaoRequest inscricaoRequest = new InscricaoRequest();
        qtdRequests++;

        RetornaInscricaoCallback callback = new RetornaInscricaoCallback() {
            @Override
            public void onSucess(TbCodigo codigo)
            {
                RequestFinalizou();
                if(codigo.getNrCod() > 0)
                {
                    RequestFinalizou();
                    statusClicavel = true;
                    DefineClicavel(layout_status, imgStatusInscricao);
                    Ultil.salvaInfoIncricoes(MainActivity.this, codigo.getNrCod(), codigo.getDsAno());
                }
                else
                {
                    RequestFinalizou();
                    statusClicavel = false;
                    DefineNaoClicavel(layout_status, imgStatusInscricao);
                }
            }

            @Override
            public void onError(ErrorModel erro)
            {
                RequestFinalizou();
                modoOffline();
            }
        };

        inscricaoRequest.retornaCodigoInscricao(idInscricao, callback, Ultil.retornaTokenAuth(this));
    }

    public void carregaInscricao()
    {
        qtdRequests += 1;

        RetornaStringCallback callback = new RetornaStringCallback() {
            @Override
            public void onSucess(String cod)
            {
                int iCod = Integer.parseInt(cod);

                if (iCod > 0)
                {
                    carregaInscricao(iCod);
                }
                else
                {
                    statusClicavel = false;
                    DefineNaoClicavel(layout_status, imgStatusInscricao);
                }

                RequestFinalizou();
            }

            @Override
            public void OnError(ErrorModel erro)
            {
                statusClicavel = false;
                DefineNaoClicavel(layout_status, imgStatusInscricao);
                RequestFinalizou();
            }
        };

        InscricaoRequest request = new InscricaoRequest();
        request.retornaIdInsc(callback, Ultil.retornaTokenAuth(this), Ultil.recuperaDadosUsuario(this).getIdUsuario());
    }

    private void ExibeSnack(String Menssagem)
    {
        Snackbar snackbar = Snackbar
                .make(drawerLayout, Menssagem, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.vermelho));
        snackbar.show();
    }

    private void DefineNaoClicavel(LinearLayout layout, ImageView img)
    {
        int cor = getResources().getColor(R.color.cinza_claro3);

        layout.getBackground().setTint(cor);
        img.setAlpha(0.4f);
    }

    private void DefineClicavel(LinearLayout layout, ImageView img)
    {
        int cor = getResources().getColor(R.color.Branco);

        layout.getBackground().setTint(cor);
        img.setAlpha(1f);
    }

    private void IniciaLayout()
    {
        drawerLayout.closeDrawer(GravityCompat.START);

        DefineNaoClicavel(layoutInscricao, imgInscricao);
        inscricaoClicavel = false;

        DefineNaoClicavel(layout_status, imgStatusInscricao);
        statusClicavel = false;

        lnlInscricoesStart.setVisibility(View.GONE);
    }

    int iContar = 0;

    public void RequestFinalizou()
    {
        iContar += 1;

        if (iContar == qtdRequests && bConexao)
        {
            lnlLoad.setVisibility(View.GONE);
            lnlInscricoesStart.setVisibility(View.VISIBLE);
            layoutPrincipal.setVisibility(View.VISIBLE);

            DefineClicavel(layout_cursos, imgCursos);
            DefineClicavel(lnlAbertura, imgAbertura);
            DefineClicavel(layout_faq, imgFaq);

            menuItem.setVisible(true);
            Tutorial();
        }
    }

    public void modoOffline()
    {
        DefineNaoClicavel(layoutInscricao, imgInscricao);
        DefineNaoClicavel(layout_cursos, imgCursos);
        DefineNaoClicavel(lnlAbertura, imgAbertura);
        DefineNaoClicavel(layout_faq, imgFaq);
        textItemCount.setVisibility(View.GONE);
        lblFraseInscricao.setVisibility(View.GONE);
        lblPeriodo.setVisibility(View.GONE);

        if (Ultil.recuperaInfoInscricao(this).getNrCod() <= 0)
        {
            DefineNaoClicavel(layout_status, imgStatusInscricao);
        }

        SnackSemConexao();
    }

    private void SnackSemConexao()
    {
        Snackbar snackbar = Snackbar.make(layout_instituto, "Sem conexão", Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Tentar novamente", new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {}
        });

        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));

        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onShown(Snackbar transientBottomBar) {
                super.onShown(transientBottomBar);

                transientBottomBar.getView().findViewById(R.id.snackbar_action)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                if (Ultil.isNetworkAvailable(MainActivity.this))
                                {
                                    bConexao = true;
                                    qtdRequests = 0;
                                    lnlLoad.setVisibility(View.VISIBLE);
                                    layoutPrincipal.setVisibility(View.GONE);

                                    VerificaExistenciaInscricao(false);
                                    VerificaSituacao();
                                    snackbar.dismiss();
                                }
                            }
                        });
            }});

        lnlLoad.setVisibility(View.GONE);
        layoutPrincipal.setVisibility(View.VISIBLE);
        snackbar.show();
    }

    private void Tutorial()
    {
        if (!Ultil.isFirstAcess(MainActivity.this))
        {
            TapTarget tvVideo = TapTarget.forView(imgAbertura, "Bem-vindo", "Para começar, assista ao vídeo sobre nós.");
            TapTarget tvCursos = TapTarget.forView(imgCursos, "Cursos", "Dá uma olhada nos nossos cursos ;)");

            TapTargetSequence seq = new TapTargetSequence(this);
            seq.targets(
                    tvVideo
                            .id(1)
                            .drawShadow(true)
                            .tintTarget(true)
                            .titleTextColor(R.color.Branco)
                            .descriptionTextColor(R.color.Branco)
                            .textColor(R.color.Branco)
                            .dimColor(R.color.Preto)
                            .outerCircleAlpha(0.96f)
                            .cancelable(false),
                    tvCursos
                            .id(2)
                            .drawShadow(true)
                            .tintTarget(true)
                            .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                            .targetCircleColor(R.color.Branco)   // Specify a color for the target circle
                            .titleTextColor(R.color.Branco)          // Specify the size (in sp) of the description text
                            .descriptionTextColor(R.color.Branco)  // Specify the color of the description text
                            .textColor(R.color.Branco)            // Specify a color for both the title and description text
                            .dimColor(R.color.Preto)            // If set, will dim behind the view with 30% opacity of the given color                 // Whether tapping outside the outer circle dismisses the view
                            .tintTarget(true)                   // Whether to tint the target view's color
                            .textColor(R.color.Branco)
            );

            seq.listener(new TapTargetSequence.Listener() {
                @Override
                public void onSequenceFinish() {
                    Ultil.primeiroAcessoFinalizado(MainActivity.this);
                }

                @Override
                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                    if (lastTarget.id() == 1) {

                        Intent intentGeral = new Intent(MainActivity.this, FrmNavegador.class);
                        intentGeral.putExtra("title", "Abertura");
                        intentGeral.putExtra("isNav", false);
                        intentGeral.putExtra("url", linkPalestra);

                        startActivity(intentGeral);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);

                    } else if (lastTarget.id() == 2) {
                        Intent intent = new Intent(MainActivity.this, FrmCursos.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    }
                }

                @Override
                public void onSequenceCanceled(TapTarget lastTarget) {

                }

            });

            drawerLayout.closeDrawer(GravityCompat.START);
            seq.start();
        }
    }

    public void botoesInativos()
    {
        layout_cursos.setEnabled(false);
        layout_status.setEnabled(false);
        layoutInscricao.setEnabled(false);
        lnlAbertura.setEnabled(false);
        layout_faq.setEnabled(false);
        layout_instituto.setEnabled(false);
    }

    public void botoesAtivos()
    {
        layout_cursos.setEnabled(true);
        layoutInscricao.setEnabled(true);
        lnlAbertura.setEnabled(true);
        layout_faq.setEnabled(true);
        layout_instituto.setEnabled(true);
        layout_status.setEnabled(true);
    }

    @Override
    public void onClick(View v)
    {
        Intent intentGeral = null;
        boolean bInternet = Ultil.isNetworkAvailable(this);

        try
        {
            if (v.getId() == lnlAbertura.getId() && bConexao)
            {
                if (aberturaClicave )
                {
                    botoesInativos();

                    intentGeral = new Intent(MainActivity.this, FrmNavegador.class);
                    intentGeral.putExtra("title", "Abertura");
                    intentGeral.putExtra("isNav", false);
                    intentGeral.putExtra("url", linkPalestra);

                    startActivity(intentGeral);
                }
                else if (bInternet)
                {
                    ExibeSnack("Infelizmente, a abertura não está disponível no momento.");
                }

            }
            else if (v.getId() == layout_cursos.getId() && bConexao)
            {
                botoesInativos();
                intentGeral = new Intent(MainActivity.this, FrmCursos.class);
                startActivity(intentGeral);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
            else if (v.getId() == layoutInscricao.getId() && bConexao)
            {
                if(inscricaoClicavel)
                {
                    intentGeral = new Intent(MainActivity.this, FrmInscricao.class);
                    startActivity(intentGeral);
                    botoesInativos();
                }
                else if (bInternet)
                {
                    ExibeSnack("Infelizmente, a inscrição não está disponível no momento.");
                }
            }
            else if (v.getId() == layout_status.getId())
            {
                if(statusClicavel)
                {
                    botoesInativos();
                    intentGeral = new Intent(MainActivity.this, FrmInformacoesInscricao.class);
                    startActivity(intentGeral);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                }
                else if (bInternet)
                {
                   ExibeSnack("Até o momento você não possui uma inscrição.");
                }
            }
            else if (v.getId() == layout_faq.getId() && bConexao)
            {
                botoesInativos();
                intentGeral = new Intent(MainActivity.this, FrmFaq.class);
                startActivity(intentGeral);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
            else if (v.getId() == layout_instituto.getId())
            {
                botoesInativos();
                intentGeral = new Intent(MainActivity.this, FrmSobreInstituto.class);
                startActivity(intentGeral);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
            else if (v.getId() == R.id.actionLayout)
            {
                intentGeral = new Intent(MainActivity.this, FrmNotificacoes.class);
                textItemCount.setVisibility(View.GONE);
                startActivity(intentGeral);
            }
        }
        catch (Exception err)
        {
            ExibeSnack("Erro inesperado! Reinicie o app e tente novamente.");
        }
    }

    public void TrataRetorno(InitResponse response)
    {
        int situacao = response.getSituacao();

        if(situacao == 100)
        {
            inscricaoClicavel = true;
            lblInscricao.setText("Alterar inscrição");
            FrmInscricao.isAlterando = true;
            DefineClicavel(layoutInscricao, imgInscricao);
        }
        else if(situacao == 200)
        {
            motivoInscricao = "Sua inscrição foi finalizada, não é possível altera-la";
            inscricaoClicavel = false;
            FrmInscricao.isAlterando = false;
            lblInscricao.setText("Inscrição finalizada");
            DefineNaoClicavel(layoutInscricao, imgInscricao);
        }
        else if(situacao == 300)
        {
            inscricaoClicavel = true;
            FrmInscricao.isAlterando = false;
            lblInscricao.setText("Realizar inscrição");
            DefineClicavel(layoutInscricao, imgInscricao);
        }
        else if(situacao == 400)
        {
            inscricaoClicavel = false;
            DefineNaoClicavel(layoutInscricao, imgStatusInscricao);
        }

        if (response.getPeriodo() != null)
        {
            lblPeriodo.setText(response.getPeriodo().getMessageB());
            lblFraseInscricao.setText(response.getPeriodo().getMessageA());
            lblFraseInscricao.setVisibility(View.VISIBLE);
            lblPeriodo.setVisibility(View.VISIBLE);
        }
        else
        {
            lblFraseInscricao.setVisibility(View.GONE);
            lblPeriodo.setVisibility(View.GONE);
        }

        if (response.getNotificacoes())
        {
            if (textItemCount != null)
            {
                textItemCount.setText("");
                textItemCount.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            textItemCount.setVisibility(View.GONE);
        }

        if (!response.linkPalesta.trim().isEmpty())
        {
            DefineClicavel(lnlAbertura, imgAbertura);
            aberturaClicave = true;
            linkPalestra = response.linkPalesta;
        }
        else
        {
            DefineNaoClicavel(lnlAbertura, imgAbertura);
            aberturaClicave = false;
            linkPalestra = "https://acaonsfatima.org.br/";
        }
    }
}