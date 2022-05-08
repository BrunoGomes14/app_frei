package com.nsf.appfreiinscricoes.telas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.requestsAPI.AgendamentoRequest;
import com.nsf.appfreiinscricoes.requestsAPI.InscricaoRequest;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaPeriodogendamentoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaResumoInscricaoCallback;
import com.nsf.appfreiinscricoes.interfaces.OnDialogCloseListener;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.PeriodoAgendamentoModel;
import com.nsf.appfreiinscricoes.model.ResumoInscricaoModel;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogAgendamento;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogBottomInformacoes;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogBottomQRCode;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogBottomVestibular;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.Calendar;

public class FrmInformacoesInscricao extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;
    private Toolbar toolbar;
    private ActionBar actionbar;
    private TextView lblCPF;
    private TextView lblCurso1;
    private TextView lblNomeCandidato;
    private TextView lblNumeroInscricao;
    private SwipeRefreshLayout swpLayout;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout lnlTenteNovamente;
    private LinearLayout errorConnection;
    private CoordinatorLayout cLayout;
    private TextView lblTextoAgendamento;
    private LinearLayout lnlAgendar;
    private LinearLayout lnlVestibular;
    Snackbar snackbar;
    DialogBottomVestibular dlgVestibular;
    int CdInscricao;
    Boolean isAgendamentoAlternado = false;
    private TextView lblTextoBotaoAgendamento;
    ScrollView srclInfo;
    LinearLayout lnlInfo;
    LinearLayout lnlInfoInsc;
    ResumoInscricaoModel dlgInscricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_informacoes_inscricao);

        CarregaControles();
        configuraToolbar();
        configuraSwippe();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(Ultil.isNetworkAvailable(this))
        {
            carregaValores();
        }
        else
        {
            preencheInfoOff();
        }
    }

    public void CarregaControles()
    {
        img = findViewById(R.id.img_status);
        toolbar = findViewById(R.id.toolbar);
        swpLayout = findViewById(R.id.swpLayout);
        lnlAgendar = findViewById(R.id.lnlAgendar);
        cLayout = findViewById(R.id.snackbarlocation);
        lblCPF = (TextView) findViewById(R.id.lblCPF);
        lnlVestibular = findViewById(R.id.lnlVestibular);
        lblCurso1 = (TextView) findViewById(R.id.lblCurso1);
        errorConnection = findViewById(R.id.errorConection);
        lnlTenteNovamente = findViewById(R.id.lnlTentarNovamente);
        lblTextoAgendamento = findViewById(R.id.lblTextoAgendamento);
        lblNomeCandidato = (TextView) findViewById(R.id.lblNomeCandidato);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        lblNumeroInscricao = (TextView) findViewById(R.id.lblNumeroInscricao);
        lblTextoBotaoAgendamento = findViewById(R.id.lblTextoBotaoAgendamento);
        dlgVestibular = new DialogBottomVestibular(this);
        lnlInfo = findViewById(R.id.lnlInfo);
        srclInfo = findViewById(R.id.scrlInfo);
        lnlInfoInsc = findViewById(R.id.lnlInfoInsc);
        lnlInfoInsc.setOnClickListener(this);

        lnlTenteNovamente.setOnClickListener(this);
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
        toolbar.setTitle("Pré-inscrição");
    }

    public void carregaValores()
    {
        try
        {
            if (snackbar != null)
            {
                snackbar.dismiss();
            }

            RetornaResumoInscricaoCallback callback;
            String keyCod = getString(R.string.dados_usuario_cod_insc);

            SharedPreferences preferences = getSharedPreferences(getString(R.string.dados_usuario), MODE_PRIVATE);
            int cod = preferences.getInt(keyCod, 0);

            lblTextoAgendamento.setOnClickListener(this);
            swpLayout.setVisibility(View.VISIBLE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mShimmerViewContainer.startShimmerAnimation();

            if(cod > 0)
            {
                callback = new RetornaResumoInscricaoCallback()
                {
                    @Override
                    public void onSucess(ResumoInscricaoModel inscricao)
                    {
                        Ultil.alteraInfoInscricoes(FrmInformacoesInscricao.this, inscricao);
                        PreencheDados(inscricao);

                        if (inscricao.dsPossuiAgendamento == 0)
                        {
                            lblTextoAgendamento.setText(Html.fromHtml(inscricao.mensagem));
                            iniciaAgendamento();
                        }
                        else
                        {
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            swpLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void OnError(ErrorModel erro)
                    {
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);

                        if(Ultil.recuperaDadosInscricao(FrmInformacoesInscricao.this).getDsCodigoInscricao() > 0)
                        {
                            preencheInfoOff();
                            swpLayout.setVisibility(View.VISIBLE);
                            errorConnection.setVisibility(View.GONE);
                        }
                        else
                        {
                            swpLayout.setVisibility(View.VISIBLE);
                            errorConnection.setVisibility(View.GONE);
                        }

                        swpLayout.setRefreshing(false);
                    }
                };

                InscricaoRequest request = new InscricaoRequest();
                request.ResumoInscricao(callback, Ultil.retornaTokenAuth(this), cod);
            }
        }
        catch (Exception ex)
        {
            Ultil.ExibeSnack("Erro inesperado, tente novamente mais tarde", swpLayout);
        }
    }

    public void PreencheDados(ResumoInscricaoModel inscricao)
    {
        int visibilityVestibular = View.GONE;
        int idGif = 0;
        int iColor = R.color.colorPrimary;
        String sFrase = "";

        dlgInscricao = inscricao;

        if (lnlInfo.getHeight() < 400)
        {
            lnlInfoInsc.setVisibility(View.VISIBLE);
            srclInfo.setVisibility(View.GONE);
        }
        else
        {
            lnlInfoInsc.setVisibility(View.GONE);
            srclInfo.setVisibility(View.VISIBLE);
        }

        if (inscricao.getDsConfirmaPgto() == 1)
        {
            lnlVestibular.setOnClickListener(this);

            visibilityVestibular = View.VISIBLE;
            idGif = R.drawable.gif_check_inscricao;
            sFrase = "";
            iColor = R.color.colorPrimary;
        }
        else if (inscricao.getDsConfirmaPgto() == 0)
        {
            visibilityVestibular = View.GONE;
            idGif = R.drawable.gif_clock;
            sFrase = "";
            iColor = R.color.cinza;
        }
        else if (inscricao.getDsConfirmaPgto() == 2)
        {
            visibilityVestibular = View.GONE;
            idGif = R.drawable.gif_error;
            sFrase = "";
            iColor = R.color.vermelho_claro;
        }

        String cursos = inscricao.getDsCursoPrimeiro().replace("\n", "") + " - " + inscricao.getDsTurnoPrimeiro();

        if (!inscricao.getDsCursoSegundo().isEmpty())
        {
            cursos += "\n" + inscricao.getDsCursoSegundo() + " - " + inscricao.getDsTurnoSegundo();
        }

        lnlVestibular.setVisibility(visibilityVestibular);
        lblCurso1.setText(cursos);
        CdInscricao = inscricao.getDsCodigoInscricao();
        lblNumeroInscricao.setText(String.valueOf(inscricao.getDsCodigoInscricao()));
        lblNomeCandidato.setText(inscricao.getNmUsuario() + "\n" + Ultil.mascaraCPF(inscricao.getDsCpf())) ;
        img.setVisibility(View.VISIBLE);


        Glide.with(getApplicationContext()).asGif().load(idGif).listener(new RequestListener<GifDrawable>() {
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

        if (inscricao.dsPossuiAgendamento == 1)
        {
            if (!inscricao.dsAgendamentoData.isEmpty() && inscricao.dsCompareceuAgendamento == 0)
            {
                lnlAgendar.setVisibility(View.GONE);
                lblTextoBotaoAgendamento.setText("Alterar agendamento");
                lblTextoAgendamento.setVisibility(View.VISIBLE);
                lblTextoAgendamento.setText(Html.fromHtml(inscricao.mensagem));
            }
            else if (inscricao.dsCompareceuAgendamento == 1 && inscricao.dsConfirmaPgto == 1)
            {
                lblTextoAgendamento.setText(Html.fromHtml(inscricao.mensagem));
                lblTextoAgendamento.setVisibility(View.VISIBLE);
                lnlAgendar.setVisibility(View.GONE);
            }
            else if (inscricao.dsConfirmaPgto == 2)
            {
                lblTextoAgendamento.setVisibility(View.GONE);
                lnlAgendar.setVisibility(View.GONE);
            }
            else if (inscricao.dsConfirmaPgto == 0)
            {
                lblTextoAgendamento.setText(Html.fromHtml(inscricao.mensagem));
                lblTextoAgendamento.setVisibility(View.VISIBLE);
                lnlAgendar.setVisibility(View.GONE);
            }
        }
        else
        {
            lblTextoAgendamento.setVisibility(View.GONE);
            lnlAgendar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_qrcode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_in);
        }

        if(item.getItemId() == R.id.itemQR)
        {
            DialogBottomQRCode dlgqr = new DialogBottomQRCode(this, Ultil.recuperaInfoInscricao(FrmInformacoesInscricao.this).getNrCod());
            dlgqr.show();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.left_in);
    }

    public void configuraSwippe()
    {
        swpLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swpLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregaValores();
            }
        });
    }

    public void preencheInfoOff()
    {
        ResumoInscricaoModel resumo = Ultil.recuperaDadosInscricao(this);
        PreencheDados(resumo);
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        swpLayout.setRefreshing(false);

        snackbar = Snackbar.make(cLayout, "Sem conexão\nInformações última consulta:", Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.Preto));

        snackbar.show();
    }

    String sData = "";
    String sHora = "";
    DatePickerDialog dlgDate = null;

    public void agendaDataPagamento(PeriodoAgendamentoModel periodo)
    {
        OnDialogCloseListener listener;
        DatePickerDialog.OnDateSetListener mDtSetListener;
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog dlgTime;
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener mTimeSetListener;
        Calendar dataFinal = Calendar.getInstance();
        Calendar dataInicio = Calendar.getInstance();

        listener = new OnDialogCloseListener() {
            @Override
            public void onDialogClose(String sFrase)
            {
                if (!sFrase.isEmpty())
                {
                    img.setVisibility(View.VISIBLE);
                    lnlAgendar.setVisibility(View.GONE);
                    lblTextoAgendamento.setText(Html.fromHtml(sFrase));
                    lblTextoAgendamento.setVisibility(View.VISIBLE);
                }
            }
        };


        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                if (minute < 10)
                {
                    sHora = hourOfDay + ":" + minute + "0";
                }
                else {
                    sHora = hourOfDay + ":" + minute;
                }
                DialogAgendamento dlgAgend = new DialogAgendamento(FrmInformacoesInscricao.this, dlgDate, listener,sData, sHora, isAgendamentoAlternado);
                dlgAgend.show();
            }
        };

        int horaInicio = Ultil.retornaHoraSql(periodo.getHrInicio());
        int horaFinal = Ultil.retornaHoraSql(periodo.getHrFinal());

        dlgTime = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(mTimeSetListener, true);
        dlgTime.setMinTime(horaInicio, 0,0);
        dlgTime.setMaxTime(horaFinal, 0,0);
        dlgTime.setCancelable(false);
        dlgTime.setAccentColor(getResources().getColor(R.color.colorPrimary));

        periodo.setDtInicio(Ultil.formataDataSQLParaConvencional(periodo.getDtInicio()));
        String[] arrData = periodo.getDtInicio().split("/");

        periodo.setDtFinal(Ultil.formataDataSQLParaConvencional(periodo.getDtFinal()));
        String[] sDataFinal = periodo.getDtFinal().split("/");

        int dia = Integer.valueOf(arrData[0]);
        int mes = Integer.valueOf(arrData[1]);
        int ano = Integer.valueOf(arrData[2]);
        int diaFinal = Integer.valueOf(sDataFinal[0]);
        int mesFinal = Integer.valueOf(sDataFinal[1]);
        int anoFinal = Integer.valueOf(sDataFinal[2]);

        dataInicio = Calendar.getInstance();
        dataInicio.set(ano, mes - 1, dia);

        dataFinal = Calendar.getInstance();
        dataFinal.set(anoFinal, mesFinal, diaFinal);

        mDtSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                sData = Ultil.formataDtpConvencional(year, month + 1, dayOfMonth);
                dlgTime.show(getSupportFragmentManager(), "Timepickerdialog");
            }
        };

        dlgDate = new DatePickerDialog(
                this,
                R.style.CalendarioDialogTheme, mDtSetListener,
                dia, mes - 1, ano);

        dlgDate.getDatePicker().setMinDate(dataInicio.getTimeInMillis());
        dlgDate.getDatePicker().setMaxDate(dataFinal.getTimeInMillis());
        dlgDate.getDatePicker().init(ano, mes - 1, dia, null);
        dlgDate.setCancelable(false);

//        if (!isAgendamentoAlternado)
//        {
//            img.setVisibility(View.GONE);
//            lblTextoStatus.setVisibility(View.GONE);
//            lblTextoAgendamento.setVisibility(View.VISIBLE);
//            lblTextoAgendamento.setText("Para finalizar a inscrição, agende uma data para efetuar o pagamento da taxa no instituto.");
//        }


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(180,30);
        params.setMargins(0,30,0,0);
        lnlAgendar.setVisibility(View.VISIBLE);
        lnlAgendar.setOnClickListener(this);
        lblTextoAgendamento.setVisibility(View.VISIBLE);

    }

    public void iniciaAgendamento()
    {
        RetornaPeriodogendamentoCallback callback = new RetornaPeriodogendamentoCallback() {
            @Override
            public void onSucess(PeriodoAgendamentoModel periodoAgendamento)
            {
                agendaDataPagamento(periodoAgendamento);
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                swpLayout.setRefreshing(false);
            }

            @Override
            public void onError(ErrorModel erro)
            {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                swpLayout.setRefreshing(false);
                swpLayout.setVisibility(View.GONE);
                lnlTenteNovamente.setVisibility(View.VISIBLE);
            }
        };

        AgendamentoRequest agendamentoRequest = new AgendamentoRequest();
        agendamentoRequest.RetornaPeriodoAgendamento(Ultil.retornaTokenAuth(this), callback);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == lnlVestibular.getId())
        {
            dlgVestibular.show();
        }
        else if (v.getId() == lnlAgendar.getId())
        {
            dlgDate.show();
        }
        else if (v.getId() == lnlTenteNovamente.getId())
        {
            errorConnection.setVisibility(View.GONE);
            carregaValores();
        }
        else if (v.getId() == lnlInfoInsc.getId())
        {
            DialogBottomInformacoes dlg = new DialogBottomInformacoes(this, dlgInscricao);
            dlg.show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dlgVestibular.dismiss();
    }
}
