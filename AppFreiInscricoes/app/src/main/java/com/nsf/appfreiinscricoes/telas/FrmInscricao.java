package com.nsf.appfreiinscricoes.telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.requestsAPI.CursosRequest;
import com.nsf.appfreiinscricoes.requestsAPI.InscricaoRequest;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaCursosPeriodoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaDadosInscricaoCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaInscricaoCallback;
import com.nsf.appfreiinscricoes.interfaces.cepAPI;
import com.nsf.appfreiinscricoes.model.EnderecoModel;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.Requests.CursoPeriodoRequest;
import com.nsf.appfreiinscricoes.model.Requests.InscricaoRequestModel;
import com.nsf.appfreiinscricoes.model.TbCodigo;
import com.nsf.appfreiinscricoes.model.TbEscolaridade;
import com.nsf.appfreiinscricoes.model.TbInformacoesExtra;
import com.nsf.appfreiinscricoes.model.TbNascimento;
import com.nsf.appfreiinscricoes.model.TbOpcaoCurso;
import com.nsf.appfreiinscricoes.model.TbPeriodos;
import com.nsf.appfreiinscricoes.model.TbResidencia;
import com.nsf.appfreiinscricoes.model.TbResponsavel;
import com.nsf.appfreiinscricoes.model.TbUsuario;
import com.nsf.appfreiinscricoes.ultil.FormataTelefone;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogAviso;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogAvisoSucesso;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogLoad;
import com.nsf.appfreiinscricoes.ultil.FormataNumero;
import com.nsf.appfreiinscricoes.ultil.RetrofitConfig;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.santalu.maskedittext.MaskEditText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrmInscricao extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener
{

    private CoordinatorLayout cdlPrincipal;
    private LinearLayout layout_principal;

    private Toolbar toolbar = null;
    private ActionBar actionbar = null;
    private LinearLayout layoutCurso = null;
    private LinearLayout layoutProximo = null;
    private NestedScrollView scrollView = null;
    private LinearLayout layoutCandidato = null;
    private LinearLayout layoutVoltarLtCurso = null;
    private LinearLayout layoutProximoLtCurso = null;
    private LinearLayout layoutProximoCursoCont = null;
    private LinearLayout layoutCursoContinuacao = null;
    private LinearLayout layoutInformacoesExtra = null;
    private LinearLayout layoutVoltarLtInfoExtra = null;
    private LinearLayout layoutVoltarLtCursoCont = null;
    private LinearLayout layoutCandidatoContinuacao = null;
    private LinearLayout layoutVoltarLtCandidatoCont = null;
    private LinearLayout layoutProximoLtCandidatoCont = null;
    private LinearLayout lnlCboPeriodoSegundo = null;
    private LinearLayout lnlCboPeriodoPrimeiro = null;
    private BottomNavigationView bottomNavigationView = null;

    //Candidato
    private EditText txtRGCandidato = null;
    private Spinner cboSexoCandidato = null;
    private EditText txtCPFCandidato = null;
    private EditText txtNomeCandidato = null;
    private EditText txtOrgaoRGCandidato = null;
    private EditText txtDataEmissaoRGCandidato = null;
    private EditText txtDataNascimentoCandidato = null;
    private EditText txtEmailCandidato = null;
    private EditText txtTelefoneCandidato = null;

    //Candidato continuacao
    private EditText txtCEP = null;
    private EditText txtBairro = null;
    private EditText txtEndereco = null;
    private EditText txtNumeroCasa = null;
    private EditText txtComplemento = null;
    private EditText txtUFNascimento = null;
    private EditText txtUFResidencida = null;
    private EditText txtNascionalidade = null;
    private EditText txtCidadeNascimento = null;
    private EditText txtCidadeResidencia = null;

    //Curso
    private Spinner cboParentesco = null;
    private Spinner cboParentescoSegundo = null;
    private EditText txtNomeSegundoResponsavel = null;
    private EditText txtNomePrimeiroResponsavel = null;
    private EditText txtEmailSegundoResponsavel = null;
    private EditText txtEmailPrimeiroResponsavel = null;
    private EditText txtTelefonePrincipalResponsavelSegundo = null;
    private EditText txtTelefonePrincipalResponsavelPrimeiro = null;
    private EditText txtTelefoneSecundarioSegundoResponsavel = null;
    private EditText txtTelefoneSecundarioPrimeiroResponsavel = null;

    //Curso continuacao
    private EditText txtEscola = null;
    private Spinner cboTipoEscola = null;
    private Spinner cboEscolaridade = null;
    private Spinner cboCursoSegundo = null;
    private Spinner cboCursoPrimeiro = null;
    private Spinner cboPeriodoSegundo = null;
    private Spinner cboPeriodoPrimeiro = null;

    //Info extra
    private Spinner cboComoConheceu = null;
    private EditText txtRendaFamiliar = null;
    private EditText txtQtdPessoasResidencia = null;
    private EditText txtQtdPessoasTrabalhando = null;
    private LinearLayout lnlEnviar = null;

    private LinearLayout errorConnection = null;
    private LinearLayout lnlTenteNovamnete = null;

    static boolean isAlterando;
    boolean possuiSegundaOpcao = false;
    boolean exibiuSugestao = false;

    DialogAviso dlgAviso = null;
    EnderecoModel enderecoModel = null;
    cepAPI service = null;
    MenuItem item = null;
    ShimmerFrameLayout shimmerEffect = null;

    ArrayList<CursoPeriodoRequest> listCursoPeriodos = null;

    List<String> nomesCursos = null;
    List<String> nomesCursos2 = null;
    List<String> periodosSegundo = null;
    List<String> periodosPrimeiro = null;

    List<String> listSexo = null;
    List<String> listParentesco = null;
    List<String> listCursoPrimeiro = null;
    List<String> listCursoSegundo = null;
    List<String> listEscolaridade = null;
    List<String> listTipoEscola = null;
    List<String> listComoConheceu = null;
    TbOpcaoCurso opcaoCursoAlterando;

    TbUsuario  usuario = null;
    TbNascimento  nascimento = null;
    TbResidencia   residencia = null;
    TbResponsavel  responsavelPrimeiro = null;
    TbResponsavel  responsavelSegundo = null;
    TbEscolaridade escolaridade = null;
    TbOpcaoCurso   curso = null;
    TbInformacoesExtra informacoesExtra = null;
    View viewAnterior;
    int idAbaValidando = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_inscricao);

        try
        {
            recuperarControles();
            configuraToolbar();
            criaInstancia();
            configuraBottonNav();
            configuraSpinners();
            inserirEndereco();
            preencheDadosExistentes();
        }
        catch (Exception err)
        {
            exibeSnack("Erro inesperado, tente novamente mais tarde.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Ultil.abaixaTeclado(this);
    }

    public void criaInstancia()
    {
        nomesCursos = new ArrayList<>();
        nomesCursos2 = new ArrayList<>();
        periodosSegundo = new ArrayList<>();
        periodosPrimeiro = new ArrayList<>();

        listSexo = new ArrayList<>(Arrays.asList("Sexo","Feminino","Masculino"));
        listParentesco = new ArrayList<>(Arrays.asList("Parentesco", "Mãe", "Pai", "Irmão", "Tio/Tia", "Avô/Avó", "Outro"));
        listCursoPrimeiro = new ArrayList<>(Arrays.asList("Primeira opção de curso", "Administração", "Comunicação Visual", "Informática", "Eletromêcanica", "Eletrotécnica", "Inglês inicial", "Inglês avançado"));
        listCursoSegundo = new ArrayList<>(Arrays.asList("Segunda opção de curso", "Administração", "Comunicação Visual", "Informática", "Eletromêcanica", "Eletrotécnica", "Inglês inicial", "Inglês avançado"));
        listEscolaridade = new ArrayList<>(Arrays.asList("Escolaridade atual", "9º ano", "1º ano do Ensino Médio","2º ano do Ensino Médio", "3º ano do Ensino Médio", "Ensino Médio Concluído", "Superior"));
        listTipoEscola = new ArrayList<>(Arrays.asList("Tipo de escola", "Particular", "Bolsista", "Pública", "Outro"));
        listComoConheceu = new ArrayList<>(Arrays.asList("Como nos conheceu?", "Amigos", "Internet", "Redes Sociais", "Outros", "Feira de profissões"));

        usuario = new TbUsuario();
        nascimento = new TbNascimento();
        residencia = new TbResidencia();
        responsavelPrimeiro = new TbResponsavel();
        responsavelSegundo = new TbResponsavel();
        escolaridade = new TbEscolaridade();
        curso = new TbOpcaoCurso();
        informacoesExtra = new TbInformacoesExtra();
    }

    public void recuperarControles()
    {
        layout_principal = findViewById(R.id.layout_principal);
        cdlPrincipal = findViewById(R.id.cdlPrincipal);
        shimmerEffect = findViewById(R.id.shimmerEffect);

        //tela geral
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        layoutCurso = (LinearLayout) findViewById(R.id.layoutCurso);
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        layoutProximo = (LinearLayout) findViewById(R.id.layoutProximo);
        layoutCandidato = (LinearLayout) findViewById(R.id.layoutCandidato);
        txtCidadeResidencia = (EditText) findViewById(R.id.txtCidadeResidencia);
        layoutVoltarLtCurso = (LinearLayout) findViewById(R.id.layoutVoltarLtCurso);
        layoutProximoLtCurso = (LinearLayout) findViewById(R.id.layoutProximoLtCurso);
        layoutCursoContinuacao = (LinearLayout) findViewById(R.id.layoutCursoContinuacao);
        layoutInformacoesExtra = (LinearLayout) findViewById(R.id.layoutInformacoesExtra);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        layoutProximoCursoCont = (LinearLayout) findViewById(R.id.layoutProximoLtCursoCont);
        layoutVoltarLtInfoExtra = (LinearLayout) findViewById(R.id.layoutVoltarLtInfoExtra);
        layoutVoltarLtCursoCont = (LinearLayout) findViewById(R.id.layoutVoltarLtCursoCont);
        layoutCandidatoContinuacao = (LinearLayout) findViewById(R.id.layoutCandidatoContinuacao);
        layoutVoltarLtCandidatoCont = (LinearLayout) findViewById(R.id.layoutVoltarLtCandidatoCont);
        layoutProximoLtCandidatoCont = (LinearLayout) findViewById(R.id.layoutProximoLtCandidatoCont);

        //Candidato
        txtEmailCandidato = (EditText) findViewById(R.id.txtEmail);
        txtRGCandidato = (EditText) findViewById(R.id.txtRGCandidato);
        txtCPFCandidato = (EditText) findViewById(R.id.txtCPFCandidato);
        cboSexoCandidato = (Spinner) findViewById(R.id.cboSexoCandidato);
        txtNomeCandidato = (EditText) findViewById(R.id.txtNomeCandidato);
        txtOrgaoRGCandidato = (EditText) findViewById(R.id.txtOrgaoRGCandidato);
        txtTelefoneCandidato = findViewById(R.id.txtTelefoneCandidato);
        txtDataEmissaoRGCandidato = (EditText) findViewById(R.id.txtDataEmissaoRGCandidato);
        txtDataNascimentoCandidato = (EditText) findViewById(R.id.txtDataNascimentoCandidato);

        //Candidato cont
        txtCEP = (EditText) findViewById(R.id.txtCEP);
        txtBairro = (EditText) findViewById(R.id.txtBairro);
        txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        txtNumeroCasa = (EditText) findViewById(R.id.txtNumeroCasa);
        txtComplemento = (EditText) findViewById(R.id.txtComplemento);
        txtUFNascimento = (EditText) findViewById(R.id.txtUFNascimento);
        txtUFResidencida = (EditText) findViewById(R.id.txtUFResidencia);
        txtNascionalidade = (EditText) findViewById(R.id.txtNascionalidade);
        txtCidadeNascimento = (EditText) findViewById(R.id.txtCidadeNascimento);
        txtCidadeResidencia = (EditText) findViewById(R.id.txtCidadeResidencia);

        //curso
        cboParentesco = (Spinner) findViewById(R.id.cboParentesco);
        cboParentescoSegundo = (Spinner) findViewById(R.id.cboParentescoSegundo);
        txtNomeSegundoResponsavel = (EditText) findViewById(R.id.txtNomeSegundoResponsavel);
        txtNomePrimeiroResponsavel = (EditText) findViewById(R.id.txtNomePrimeiroResponsavel);
        txtEmailSegundoResponsavel = (EditText) findViewById(R.id.txtEmailSegundoResponsavel);
        txtEmailPrimeiroResponsavel = (EditText) findViewById(R.id.txtEmailPrimeiroResponsavel);
        txtTelefonePrincipalResponsavelSegundo = (EditText) findViewById(R.id.txtTelefonePrincipalResponsavelSegundo);
        txtTelefonePrincipalResponsavelPrimeiro = (EditText) findViewById(R.id.txtTelefonePrincipalResponsavelPrimeiro);
        txtTelefoneSecundarioSegundoResponsavel = (EditText) findViewById(R.id.txtTelefoneSecundarioSegundoResponsavel);
        txtTelefoneSecundarioPrimeiroResponsavel = (EditText) findViewById(R.id.txtTelefoneSecundarioPrimeiroResponsavel);

        //Curso continuacao
        txtEscola = (EditText) findViewById(R.id.txtEscola);
        cboTipoEscola = (Spinner) findViewById(R.id.cboTipoEscola);
        cboEscolaridade = (Spinner) findViewById(R.id.cboEscolaridade);
        cboCursoSegundo = (Spinner) findViewById(R.id.cboCursoSegundo);
        cboCursoPrimeiro = (Spinner) findViewById(R.id.cboCursoPrimeiro);
        cboPeriodoSegundo = (Spinner) findViewById(R.id.cboPeriodoSegundo);
        cboPeriodoPrimeiro = (Spinner) findViewById(R.id.cboPeriodoPrimeiro);
        lnlCboPeriodoPrimeiro = (LinearLayout) findViewById(R.id.lnlCboPeriodoPrimeiro);
        lnlCboPeriodoSegundo = (LinearLayout) findViewById(R.id.lnlCboPeriodoSegundo);

        //info extra
        cboComoConheceu = (Spinner) findViewById(R.id.cboComoConheceu);
        txtRendaFamiliar = (EditText) findViewById(R.id.txtRendaFamiliar);
        txtRendaFamiliar.addTextChangedListener( new FormataNumero(txtRendaFamiliar));
        txtQtdPessoasResidencia = (EditText) findViewById(R.id.txtQtdPessoasResidencia);
        txtQtdPessoasTrabalhando = (EditText) findViewById(R.id.txtQtdPessoasTrabalhando);
        lnlEnviar = (LinearLayout) findViewById(R.id.lnlEnviar);

        errorConnection = findViewById(R.id.errorConection);
        lnlTenteNovamnete = findViewById(R.id.lnlTentarNovamente);

        layoutProximo.setOnClickListener(this);
        layoutProximoLtCandidatoCont.setOnClickListener(this);
        layoutProximoLtCurso.setOnClickListener(this);
        layoutProximoCursoCont.setOnClickListener(this);
        layoutVoltarLtInfoExtra.setOnClickListener(this);
        layoutVoltarLtCursoCont.setOnClickListener(this);
        layoutVoltarLtCurso.setOnClickListener(this);
        layoutVoltarLtCandidatoCont.setOnClickListener(this);
        lnlTenteNovamnete.setOnClickListener(this);
        lnlEnviar.setOnClickListener(this);

        txtTelefoneCandidato.addTextChangedListener(new FormataTelefone(txtTelefoneCandidato));
        txtTelefonePrincipalResponsavelPrimeiro.addTextChangedListener(new FormataTelefone(txtTelefonePrincipalResponsavelPrimeiro));
        txtTelefoneSecundarioPrimeiroResponsavel.addTextChangedListener(new FormataTelefone(txtTelefoneSecundarioPrimeiroResponsavel));
        txtTelefonePrincipalResponsavelSegundo.addTextChangedListener(new FormataTelefone(txtTelefonePrincipalResponsavelSegundo));
        txtTelefoneSecundarioSegundoResponsavel.addTextChangedListener(new FormataTelefone(txtTelefoneSecundarioSegundoResponsavel));

        viewAnterior = layoutCandidato;
    }

    public void exibeLoad()
    {
        shimmerEffect.setVisibility(View.VISIBLE);
        shimmerEffect.startShimmerAnimation();
        bottomNavigationView.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
    }

    public void acabaLoad()
    {
        scrollView.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        shimmerEffect.setVisibility(View.GONE);
        shimmerEffect.stopShimmerAnimation();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mnu_confirmar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_in);
                break;
            case R.id.itemCheck:
                enviaDados();
                break;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        item = menu.findItem(R.id.itemCheck);
        item.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    int isValidando = 1;

    public void configuraBottonNav()
    {
        layoutCandidato.setVisibility(View.VISIBLE);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fling(0);
                        scrollView.smoothScrollTo(0, 0);
                    }
                });

                switch (menuItem.getItemId())
                {
                    case R.id.candidato:
                        exibeViewsCadastro(layoutCandidato);
                        break;

                    case R.id.cadidatopt2:
                        exibeViewsCadastro(layoutCandidatoContinuacao);
                        viewAnterior = layoutCandidato;
                        break;

                    case R.id.curso:
                        exibeViewsCadastro(layoutCurso);
                        viewAnterior =layoutCandidatoContinuacao;
                        break;

                    case R.id.cursopt2:
                        exibeViewsCadastro(layoutCursoContinuacao);
                        viewAnterior = layoutCurso;
                        break;

                    case R.id.infoExtra:
                        item.setVisible(true);
                        exibeViewsCadastro(layoutInformacoesExtra);
                        viewAnterior = layoutCursoContinuacao;
                        break;
                }

//                if (isValidando == 1)
//                {
//                    if (validaAba()) {
//
//                        isValidando = 1;
//                    }
//                    else
//                    {
//                        isValidando = 0;
//                        updateNavigationBarState(idAbaValidando);
//
//                        dlgAviso.show();
//                    }
//                }
//                else
//                {
//                    isValidando = 1;
//                }

                return true;
            }
        });
    }

    private void updateNavigationBarState(int actionId){
        Menu menu = bottomNavigationView.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == actionId);
        }
    }

    public void proximoLayout()
    {
        int selectedItemId = bottomNavigationView.getSelectedItemId();

        switch (selectedItemId)
        {
            case R.id.candidato:
                bottomNavigationView.setSelectedItemId(R.id.cadidatopt2);
                break;

            case R.id.cadidatopt2:
                bottomNavigationView.setSelectedItemId(R.id.curso);
                break;

            case R.id.curso:
                bottomNavigationView.setSelectedItemId(R.id.cursopt2);
                break;

            case R.id.cursopt2:
                bottomNavigationView.setSelectedItemId(R.id.infoExtra);
                break;
        }

        bottomNavigationView.animate().translationY(0f);
    }

    public void voltaLayout()
    {
        int selectedItemId = bottomNavigationView.getSelectedItemId();

        switch (selectedItemId)
        {
            case R.id.cadidatopt2:
                bottomNavigationView.setSelectedItemId(R.id.candidato);
                break;

            case R.id.curso:
                bottomNavigationView.setSelectedItemId(R.id.cadidatopt2);
                break;

            case R.id.cursopt2:
                bottomNavigationView.setSelectedItemId(R.id.curso);
                break;

            case R.id.infoExtra:
                bottomNavigationView.setSelectedItemId(R.id.cursopt2);
                break;
        }

        bottomNavigationView.animate().translationY(0f);
    }

    public void inserirEndereco()
    {
        DialogLoad dialogLoad = new DialogLoad(this);

        txtCEP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus && txtCEP.getText() != null && txtCidadeResidencia.hasFocus())
                {
                    try
                    {
                        Ultil.abaixaTeclado(FrmInscricao.this);
                        dialogLoad.show();

                        String cep = txtCEP.getText().toString();

                        if(cep.length() >= 8)
                        {
                            cep = cep.replace("-","");

                            enderecoModel = new EnderecoModel();
                            service = RetrofitConfig.get();
                            service.getEndereco(cep).enqueue(new Callback<EnderecoModel>() {
                                @Override
                                public void onResponse(Call<EnderecoModel> call, Response<EnderecoModel> response)
                                {
                                    if(response.isSuccessful() && response.body().getLogradouro() != null)
                                    {
                                        enderecoModel.setLogradouro(response.body().getLogradouro());
                                        enderecoModel.setBairro(response.body().getBairro());
                                        enderecoModel.setUf(response.body().getUf());
                                        enderecoModel.setLocalidade(response.body().getLocalidade());

                                        txtEndereco.setText("");
                                        txtEndereco.setText(enderecoModel.getLogradouro());

                                        txtBairro.setText("");
                                        txtBairro.setText(enderecoModel.getBairro());

                                        txtCidadeResidencia.setText("");
                                        txtCidadeResidencia.setText(enderecoModel.getLocalidade());

                                        txtUFResidencida.setText("");
                                        txtUFResidencida.setText(enderecoModel.getUf());

                                        dialogLoad.dismiss();
                                        scrollView.fullScroll(View.FOCUS_DOWN);
                                        txtCEP.clearFocus();
                                        txtCidadeNascimento.clearFocus();
                                        txtCidadeResidencia.clearFocus();
                                        txtNumeroCasa.requestFocus();
                                        Ultil.mostraTeclado(txtNumeroCasa, FrmInscricao.this);
                                    }
                                    else
                                    {
                                        dialogLoad.dismiss();
                                        DialogAviso dlgAviso = new DialogAviso(FrmInscricao.this, "CEP inválido");
                                        dlgAviso.show();

                                        txtEndereco.setText("");
                                        txtBairro.setText("");
                                        txtCidadeResidencia.setText("");
                                        txtUFResidencida.setText("");
                                        txtCidadeResidencia.clearFocus();
                                        txtCEP.requestFocus();
                                        Ultil.mostraTeclado(txtCEP, FrmInscricao.this);
                                    }
                                }

                                @Override
                                public void onFailure(Call<EnderecoModel> call, Throwable t)
                                {
                                    dialogLoad.dismiss();
                                    DialogAviso dlgAviso = new DialogAviso(FrmInscricao.this, "Não foi possível encontrar este CEP");
                                    dlgAviso.show();

                                    txtEndereco.setText("");
                                    txtBairro.setText("");
                                    txtCidadeResidencia.setText("");
                                    txtUFResidencida.setText("");

                                    apontaCampo(txtCEP, R.id.cadidatopt2);
                                }
                            });
                        }
                        else
                        {
                            DialogAviso dlgAviso = new DialogAviso(FrmInscricao.this, "Cep inválido");
                            dlgAviso.show();
                            dialogLoad.dismiss();
                        }
                    }
                    catch (Exception err)
                    {
                        dialogLoad.dismiss();
                        DialogAviso dlgAviso = new DialogAviso(FrmInscricao.this, "Não foi possível encontrar este CEP");
                        dlgAviso.show();
                    }
                }
                else
                {
                    dialogLoad.dismiss();
                    Ultil.abaixaTeclado(FrmInscricao.this);
                }
            }
        });
    }

    public void configuraSpinners()
    {
        CursosRequest cursosRequest = new CursosRequest();
        exibeLoad();
        Ultil.abaixaTeclado(this);

        RetornaCursosPeriodoCallback callbacks = new RetornaCursosPeriodoCallback() {
            @Override
            public void onSucess(ArrayList<CursoPeriodoRequest> cursos)
            {

                nomesCursos.add("Primeira opção de curso");
                nomesCursos2.add("Segunda opção (opcional)");

                for(CursoPeriodoRequest item : cursos)
                {
                    nomesCursos.add(item.getCurso().getNmCurso());
                    nomesCursos2.add(item.getCurso().getNmCurso());
                }

                listCursoPeriodos = cursos;

                ArrayAdapter<String> adatperCurso = new ArrayAdapter<String>(FrmInscricao.this, R.layout.spn_item, nomesCursos);
                adatperCurso.setDropDownViewResource(R.layout.spn_list);
                cboCursoPrimeiro.setAdapter(adatperCurso);

                ArrayAdapter<String> adatperCurso2 = new ArrayAdapter<String>(FrmInscricao.this, R.layout.spn_item, nomesCursos2);
                adatperCurso2.setDropDownViewResource(R.layout.spn_list);
                cboCursoSegundo.setAdapter(adatperCurso2);

                lnlCboPeriodoPrimeiro.setVisibility(View.GONE);
                lnlCboPeriodoSegundo.setVisibility(View.GONE);

                if (isAlterando)
                {
                    iniciaAlteracao();
                }
                else
                {
                    cboParentesco.setSelection(5);
                    acabaLoad();
                }
            }

            @Override
            public void onError(ErrorModel error)
            {
                acabaLoad();
                errorConnection.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                bottomNavigationView.setVisibility(View.GONE);
            }
        };

        cursosRequest.ListaCursoPeriodo(callbacks, Ultil.retornaTokenAuth(this));

        ArrayAdapter<String> adapterSexo = new ArrayAdapter<String>(this,R.layout.spn_item, listSexo );
        ArrayAdapter<String> adapterTipoEscola = new ArrayAdapter<String>(this, R.layout.spn_item, listTipoEscola);
        ArrayAdapter<String> adapterParentesco = new ArrayAdapter<String>(this, R.layout.spn_item, listParentesco);
        ArrayAdapter<String> adapterComoConheceu = new ArrayAdapter<String>(this, R.layout.spn_item, listComoConheceu);
        ArrayAdapter<String> adapterEscolaridade = new ArrayAdapter<String>(this, R.layout.spn_item, listEscolaridade);
        ArrayAdapter<String> adapterCursoSegundo = new ArrayAdapter<String>(this, R.layout.spn_item, listCursoSegundo);
        ArrayAdapter<String> adapterCursoPrimeiro = new ArrayAdapter<String>(this, R.layout.spn_item, listCursoPrimeiro);

        adapterSexo.setDropDownViewResource(R.layout.spn_list);
        adapterParentesco.setDropDownViewResource(R.layout.spn_list);
        adapterTipoEscola.setDropDownViewResource(R.layout.spn_list);
        adapterEscolaridade.setDropDownViewResource(R.layout.spn_list);
        adapterCursoSegundo.setDropDownViewResource(R.layout.spn_list);
        adapterComoConheceu.setDropDownViewResource(R.layout.spn_list);
        adapterCursoPrimeiro.setDropDownViewResource(R.layout.spn_list);

        cboSexoCandidato.setAdapter(adapterSexo);
        cboSexoCandidato.setOnItemSelectedListener(this);

        cboParentesco.setAdapter(adapterParentesco);
        cboParentesco.setOnItemSelectedListener(this);

        cboTipoEscola.setAdapter(adapterTipoEscola);
        cboTipoEscola.setOnItemSelectedListener(this);

        cboPeriodoSegundo.setOnItemSelectedListener(this);
        cboPeriodoPrimeiro.setOnItemSelectedListener(this);

        cboCursoSegundo.setAdapter(adapterCursoSegundo);
        cboCursoSegundo.setOnItemSelectedListener(this);

        cboEscolaridade.setAdapter(adapterEscolaridade);
        cboEscolaridade.setOnItemSelectedListener(this);

        cboComoConheceu.setAdapter(adapterComoConheceu);
        cboComoConheceu.setOnItemSelectedListener(this);

        cboCursoPrimeiro.setAdapter(adapterCursoPrimeiro);
        cboCursoPrimeiro.setOnItemSelectedListener(this);

        cboParentescoSegundo.setAdapter(adapterParentesco);
        cboParentescoSegundo.setOnItemSelectedListener(this);
    }

    public boolean validaCampos()
    {
        //candidato
        if (txtNomeCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O nome do cadidato é obrigatório!");
            apontaCampo(txtNomeCandidato, R.id.candidato);
            return false;
        }

        if (txtEmailCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O e-mail do cadidato é obrigatório!");
            apontaCampo(txtEmailCandidato, R.id.candidato);
            return false;
        }

        if (txtTelefoneCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O telefone do cadidato é obrigatório!");
            apontaCampo(txtEmailCandidato, R.id.candidato);
            return false;
        }

        if (txtDataNascimentoCandidato.getText().toString().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "A data de nascimento é obrigatória");
            apontaCampo(txtDataNascimentoCandidato, R.id.candidato);
            return false;
        }

        if(txtDataNascimentoCandidato.getText() != null)
        {
            if (!Ultil.validaData(txtDataNascimentoCandidato.getText().toString()))
            {
                dlgAviso = new DialogAviso(this, "Data de nascimento inválida");
                apontaCampo(txtDataNascimentoCandidato, R.id.candidato);
                return false;
            }
        }

        if (cboSexoCandidato.getSelectedItem().equals(cboSexoCandidato.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Nenhum sexo selecionado");
            apontaCampo(cboSexoCandidato, R.id.candidato);
            return false;
        }


        if (txtCPFCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "CPF é obrigatório");
            apontaCampo(txtCPFCandidato, R.id.candidato);
            return false;
        }

        if (txtRGCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O RG é obrigatório");
            apontaCampo(txtRGCandidato, R.id.candidato);
            return false;
        }

        if (txtOrgaoRGCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O orgão do RG é obrigatório");
            apontaCampo(txtOrgaoRGCandidato, R.id.candidato);
            return false;
        }

        if (txtDataEmissaoRGCandidato.getText().toString().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Data de emissão do RG é obrigatório");
            apontaCampo(txtDataEmissaoRGCandidato, R.id.candidato);
            return false;
        }

        if(txtDataEmissaoRGCandidato.getText() != null)
        {
            if(!Ultil.validaData(txtDataEmissaoRGCandidato.getText().toString()))
            {
                dlgAviso = new DialogAviso(this, "Data de emissão de RG inválida");
                apontaCampo(txtDataEmissaoRGCandidato, R.id.candidato);
                return false;
            }
        }

        //candidato conti
        if (txtCidadeNascimento.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "A cidade de nascimento é obrigatório");
            apontaCampo(txtCidadeNascimento, R.id.cadidatopt2);
            return false;
        }

        if (txtUFNascimento.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "UF da cidade de nascimento é obrigátório");
            apontaCampo(txtUFNascimento, R.id.cadidatopt2);
            return false;
        }

        if (txtNascionalidade.getText().toString().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Nacionalidade é obrigatória");
            apontaCampo(txtNascionalidade, R.id.cadidatopt2);
            return false;
        }

        if (txtCEP.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O cep é obrigatório");
            apontaCampo(txtCEP, R.id.cadidatopt2);
            return false;
        }

        if (txtCidadeResidencia.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "A cidade de residênicia é obrigatória");
            apontaCampo(txtCidadeResidencia, R.id.cadidatopt2);
            return false;
        }

        if (txtUFResidencida.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "A UF de residência é obrigatória");
            apontaCampo(txtUFResidencida, R.id.cadidatopt2);
            return false;
        }

        if (txtEndereco.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Logradouro é obrigatório!");
            apontaCampo(txtEndereco, R.id.cadidatopt2);
            return false;
        }

        if (txtBairro.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Bairro de residencia é obrigatório");
            apontaCampo(txtBairro, R.id.cadidatopt2);
            return false;
        }

        if (txtNumeroCasa.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Número da casa é obrigatório");
            apontaCampo(txtNumeroCasa, R.id.cadidatopt2);
            return false;
        }

        //curso
        if (txtNomePrimeiroResponsavel.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Nome do primeiro responsável é obrigatório");
            apontaCampo(txtNomePrimeiroResponsavel, R.id.curso);
            return false;
        }

        if (txtTelefonePrincipalResponsavelPrimeiro.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Telefone do primeiro responsável é obrigatório");
            apontaCampo(txtTelefonePrincipalResponsavelPrimeiro, R.id.curso);
            return false;
        }


        if (txtEmailPrimeiroResponsavel.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "E-mail do primeiro responsável é obrigatório");
            apontaCampo(txtEmailPrimeiroResponsavel, R.id.curso);
            return false;
        }

        if (cboParentesco.getSelectedItem().equals(cboParentesco.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Nenhum parentesco selecionado em 1° responsável");
            apontaCampo(cboParentesco, R.id.curso);
            return false;
        }

        if(txtNomeSegundoResponsavel.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Nome do segundo responsável inválido");
            apontaCampo(txtNomeSegundoResponsavel, R.id.curso);
            return false;
        }

        if (txtTelefonePrincipalResponsavelSegundo.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Telefone do segundo responsavel é obrigatório");
            apontaCampo(txtTelefonePrincipalResponsavelSegundo, R.id.curso);

            return false;
        }

        if (txtEmailSegundoResponsavel.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "E-mail do segundo responsável é obrigatório");
            apontaCampo(txtEmailSegundoResponsavel, R.id.curso);
            return false;
        }

        if (cboParentescoSegundo.getSelectedItem().equals(cboParentescoSegundo.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Nenhum parentesco selecionado em 2° responsável");
            apontaCampo(cboParentescoSegundo, R.id.curso);
            return false;
        }

        //curso continuacao

        if(cboEscolaridade.getSelectedItem().equals(cboEscolaridade.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "A escolaridade é obrigatória");
            apontaCampo(cboEscolaridade, R.id.cursopt2);
            return false;
        }

        if(cboTipoEscola.getSelectedItem().equals(cboTipoEscola.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "o tipo da escola é obrigatório.");
            apontaCampo(cboTipoEscola, R.id.cursopt2);
            return false;
        }

        if(txtEscola.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O nome de sua escola é obrigatório");
            apontaCampo(txtEscola, R.id.cursopt2);
            return false;
        }

        if (cboCursoPrimeiro.getSelectedItem().equals(cboCursoPrimeiro.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Selecione ao menos uma opção de curso");
            apontaCampo(cboCursoPrimeiro, R.id.cursopt2);
            return false;
        }

        if (cboPeriodoPrimeiro.getSelectedItem().equals(cboPeriodoPrimeiro.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Nenhum periodo de primeiro curso selecionado");
            apontaCampo(cboPeriodoPrimeiro, R.id.cursopt2);
            return false;
        }

        if (!cboCursoSegundo.getSelectedItem().equals(cboCursoSegundo.getItemAtPosition(0)))
        {
            if(cboCursoSegundo.getSelectedItem().toString().equals(cboCursoPrimeiro.getSelectedItem().toString()))
            {
                dlgAviso = new DialogAviso(this, "Os cursos selecionados não podem ser iguais");
                apontaCampo(cboCursoSegundo, R.id.cursopt2);
                return false;
            }

            if (cboPeriodoSegundo.getSelectedItem().equals(cboPeriodoSegundo.getItemAtPosition(0)))
            {
                dlgAviso = new DialogAviso(this, "Nenhum periodo do segundo curso selecionado");
                apontaCampo(cboPeriodoPrimeiro, R.id.cursopt2);
                return false;
            }
        }

        //info extra

        if (cboComoConheceu.getSelectedItem().equals(cboComoConheceu.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Selecione como conheceu o instituto");
            apontaCampo(cboComoConheceu, R.id.infoExtra);
            return false;
        }

        if(txtRendaFamiliar.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Informar a renda é obrigatório.");
            apontaCampo(txtRendaFamiliar, R.id.infoExtra);
            return false;
        }

        if(txtQtdPessoasResidencia.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "A quantidade de pessoas é obrigatório");
            apontaCampo(txtQtdPessoasResidencia, R.id.infoExtra);
            return false;
        }

        if(txtQtdPessoasTrabalhando.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "A quantidade de pessoas é obrigatória");
            apontaCampo(txtQtdPessoasTrabalhando, R.id.infoExtra);
            return false;
        }

        return true;

    }

    public void preencheDadosExistentes()
    {
        TbUsuario usuarioInfo = Ultil.recuperaDadosUsuario(this);

        txtNomeCandidato.setText(usuarioInfo.getNmUsuario());
        txtCPFCandidato.setText(usuarioInfo.getDsCpf());
        txtDataNascimentoCandidato.setText(Ultil.formataDataSQLParaConvencional(usuarioInfo.getDtNascimento()));
        txtEmailCandidato.setText(usuarioInfo.getDsEmail());
    }

    public void enviaDados()
    {
        String sAux = "";

        try
        {
            Ultil.abaixaTeclado(this);

            if(validaCampos())
            {
                DialogLoad dlgLoad = new DialogLoad(this);
                dlgLoad.show();

                //Aba usuario
                usuario.setIdUsuario(Ultil.recuperaDadosUsuario(this).getIdUsuario());
                usuario.setNrRg(txtRGCandidato.getText().toString().trim());
                usuario.setDsCpf(txtCPFCandidato.getText().toString().trim());
                usuario.setNmUsuario(txtNomeCandidato.getText().toString().trim());
                usuario.setDsOrgaoRg(txtOrgaoRGCandidato.getText().toString().trim());
                usuario.setDsSexo(cboSexoCandidato.getSelectedItem().toString().trim());
                usuario.setDsEmail(txtEmailCandidato.getText().toString().trim());
                usuario.setDsTelefone(txtTelefoneCandidato.getText().toString().trim());
                usuario.setDtEmissaoRg(Ultil.formataDataConvencionalParaSQL(txtDataEmissaoRGCandidato.getText().toString().trim()));
                usuario.setDtNascimento(Ultil.formataDataConvencionalParaSQL(txtDataNascimentoCandidato.getText().toString().trim()));

                //aba nascimento/residenci
                nascimento.setDsUf(txtUFNascimento.getText().toString().trim());
                nascimento.setDsCidade(txtCidadeNascimento.getText().toString().trim());
                nascimento.setDsNascionalidade(txtNascionalidade.getText().toString().trim());

                residencia.setNrCep(txtCEP.getText().toString().trim());
                residencia.setDsBairro(txtBairro.getText().toString().trim());
                residencia.setDsUf(txtUFResidencida.getText().toString().trim());
                residencia.setDsEndereco(txtEndereco.getText().toString().trim());
                residencia.setNrResidencia(txtNumeroCasa.getText().toString().trim());
                residencia.setDsCidade(txtCidadeResidencia.getText().toString().trim());

                sAux = txtComplemento.getText().toString().trim();

                if (sAux.length() > 0)
                    residencia.setDsComplemento(sAux);

                //aba curso responsavel
                responsavelPrimeiro.setDsParentesco(cboParentesco.getSelectedItem().toString().trim());
                responsavelPrimeiro.setDsEmail(txtEmailPrimeiroResponsavel.getText().toString().trim());
                responsavelPrimeiro.setNmResponsavel(txtNomePrimeiroResponsavel.getText().toString().trim());
                responsavelPrimeiro.setNrTelefone(Ultil.retiraPontuacao(Ultil.retiraPontuacao(txtTelefonePrincipalResponsavelPrimeiro.getText().toString().trim())));
                responsavelPrimeiro.setBlRespSecundario(0);

                sAux = txtTelefoneSecundarioPrimeiroResponsavel.getText().toString().trim();

                if (sAux.length() > 0)
                    responsavelPrimeiro.setNrTelefoneSec(Ultil.retiraPontuacao(sAux));

                //segundo responsavel
                responsavelSegundo.setDsEmail(txtEmailSegundoResponsavel.getText().toString().trim());
                responsavelSegundo.setDsParentesco(cboParentescoSegundo.getSelectedItem().toString().trim());
                responsavelSegundo.setNmResponsavel(txtNomeSegundoResponsavel.getText().toString().trim());
                responsavelSegundo.setNrTelefone(Ultil.retiraPontuacao(txtTelefonePrincipalResponsavelSegundo.getText().toString().trim()));
                responsavelSegundo.setBlRespSecundario(1);

                sAux = txtTelefoneSecundarioSegundoResponsavel.getText().toString().trim();

                if (sAux.length() > 0)
                    responsavelSegundo.setNrTelefoneSec(Ultil.retiraPontuacao(sAux));

                //aba curso

                //escolaridade
                escolaridade.setDsEscolaridade(Ultil.ConverteEscolaridade(cboEscolaridade.getSelectedItem().toString().trim(), true ));
                escolaridade.setTpEscola(cboTipoEscola.getSelectedItem().toString().trim());
                escolaridade.setDsEscola(txtEscola.getText().toString().trim());

                //curso
                curso.setIdCursoPrimeiraOpcao(retornaIDCurso(cboCursoPrimeiro.getSelectedItem().toString()));
                curso.setDsTurnoPrimeiraOpcao(cboPeriodoPrimeiro.getSelectedItem().toString().trim());

                if(cboCursoSegundo.getSelectedItem() != cboCursoSegundo.getItemAtPosition(0))
                {
                    curso.setIdCursoSegundaOpcao(retornaIDCurso(cboCursoSegundo.getSelectedItem().toString()));
                    curso.setDsTurnoSegundaOpcao(cboPeriodoSegundo.getSelectedItem().toString().trim());
                }
                else
                {
                    curso.setIdCursoSegundaOpcao(0);
                    curso.setDsTurnoSegundaOpcao("");
                }

                //aba info extra
                String sRenda = txtRendaFamiliar.getText().toString().trim().replaceAll("[$\\s+R.]", "");
                sRenda = sRenda.replace(",", ".");

                informacoesExtra.setDsComoConheceu(cboComoConheceu.getSelectedItem().toString().trim());
                informacoesExtra.setDsRenda(Float.parseFloat(sRenda));
                informacoesExtra.setQtdPessoasTrabalhando(Integer.parseInt(txtQtdPessoasTrabalhando.getText().toString().trim()));
                informacoesExtra.setQtdPessoasResidentesCasa(Integer.parseInt(txtQtdPessoasResidencia.getText().toString().trim()));

                //Junta tudo
                InscricaoRequestModel inscricao = new InscricaoRequestModel();
                inscricao.setUsuario(usuario);
                inscricao.setNascimento(nascimento);
                inscricao.setResidencia(residencia);
                inscricao.setResponsavel(responsavelPrimeiro);
                inscricao.setResponsavelSecundario(responsavelSegundo);
                inscricao.setEscolaridade(escolaridade);
                inscricao.setOpcaoCurso(curso);
                inscricao.setInformacoesExtra(informacoesExtra);
                inscricao.isAlterando = isAlterando;

                RetornaInscricaoCallback inscricaoCallback = new RetornaInscricaoCallback() {
                    @Override
                    public void onSucess(TbCodigo codigo)
                    {
                        if (!isAlterando)
                        {
                            dlgLoad.dismiss();

                            Ultil.alteraInfoLogin(usuario, FrmInscricao.this);
                            Ultil.salvaInfoIncricoes(FrmInscricao.this, codigo.getNrCod(), codigo.getDsAno());

                            Intent intent = new Intent(FrmInscricao.this, FrmInscricaoConcluido.class);
                            intent.putExtra("nrInscricao", codigo.getNrCod());
                            intent.putExtra("anoInscricao", codigo.getDsAno());

                            // informa ao menu que realizou inscricao
                            MainActivity.realizouInsc = true;

                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Activity context = FrmInscricao.this;
                            DialogAvisoSucesso aviso = new DialogAvisoSucesso(context, "Informações alteradas com sucesso !", context);
                            aviso.show();
                            dlgLoad.dismiss();
                        }
                    }

                    @Override
                    public void onError(ErrorModel erro)
                    {
                        DialogAviso dlgAviso = new DialogAviso(FrmInscricao.this, erro.getMensagemErro());
                        dlgAviso.show();
                        dlgLoad.dismiss();
                    }
                };

                InscricaoRequest inscricaoRequest = new InscricaoRequest();
                inscricaoRequest.EnviaInscricao(inscricaoCallback, inscricao, Ultil.retornaTokenAuth(this));
            }
            else
            {
                dlgAviso.show();
            }
        }
        catch (Exception ex)
        {
            exibeSnack("Erro inesperado, tente novamete mais tarde.");
        }
    }

    private void exibeSnack(String Menssagem)
    {
        Snackbar snackbar = Snackbar
                .make(cdlPrincipal, Menssagem, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.vermelho));
        snackbar.show();
    }

    private void exibeSnackVideo(String link)
    {
        Snackbar snackbar = Snackbar
                .make(layout_principal, "Já viu o video desse curso?", Snackbar.LENGTH_LONG);
        snackbar.setAction("VER", new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intentGeral = new Intent(FrmInscricao.this, FrmNavegador.class);
                intentGeral.putExtra("title", "");
                intentGeral.putExtra("isNav", false);
                intentGeral.putExtra("url", link);

                startActivity(intentGeral);
            }
        });

        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }

    public int retornaIDCurso(String sNomeCurso)
    {
        int idCurso = 0;

        for (CursoPeriodoRequest curso: listCursoPeriodos)
        {
            if (curso.getCurso().getNmCurso().equals(sNomeCurso))
            {
                idCurso = curso.getCurso().getIdCurso();
            }
        }

        return idCurso;
    }

    public void alteraCorSpinner(AdapterView<?> parent, View view)
    {
        TextView spinner = (TextView) view;

        if (!parent.getSelectedItem().toString().equals(parent.getItemAtPosition(0).toString()))
        {
            spinner.setTextColor(getResources().getColor(R.color.Preto));
        }
        else
        {
            spinner.setTextColor(getResources().getColor(R.color.cinza));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(view != null)
        {
            alteraCorSpinner(parent, view);
        }

        if (parent.getId() == R.id.cboCursoPrimeiro)
        {
            if (position > 0)
            {
                CursoPeriodoRequest curso = listCursoPeriodos.get(position - 1);

                if(!isAlterando && curso.linkVideoApresentacao != null && !exibiuSugestao)
                {
                    exibeSnackVideo(curso.linkVideoApresentacao);
                    exibiuSugestao = true;
                }

                configuraCboPeriodoPrimereiro(position - 1);
            }
            else
            {
                lnlCboPeriodoPrimeiro.setVisibility(View.GONE);
            }
        }

        if(parent.getId() == R.id.cboCursoSegundo)
        {
            if(position > 0)
            {
                configuraCboPeriodoSegundo(position - 1);
            }
            else
            {
                lnlCboPeriodoSegundo.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void configuraCboPeriodoPrimereiro(int position)
    {
        CursoPeriodoRequest curso = listCursoPeriodos.get(position);
        periodosPrimeiro.clear();
        periodosPrimeiro.add("Período");

        for(TbPeriodos itemPeriodo: curso.getPeriodos())
        {
            String sCurso = curso.getCurso().nmCurso;

            if (sCurso.toLowerCase().contains("inglês") && !itemPeriodo.dsPeriodo.contains("-"))
            {
                itemPeriodo.dsPeriodo += " - " + itemPeriodo.hrEntrada;
            }

            periodosPrimeiro.add(itemPeriodo.dsPeriodo);
        }

        ArrayAdapter<String> adatperCurso = new ArrayAdapter<String>(FrmInscricao.this, R.layout.spn_item, periodosPrimeiro);
        adatperCurso.setDropDownViewResource(R.layout.spn_list);
        cboPeriodoPrimeiro.setAdapter(adatperCurso);
        lnlCboPeriodoPrimeiro.setVisibility(View.VISIBLE);

        if(isAlterando)
        {
            int pos = posicaoComboPreDefinido(opcaoCursoAlterando.getDsTurnoPrimeiraOpcao(), periodosPrimeiro);
            cboPeriodoPrimeiro.setSelection(pos);
        }
    }

    public void configuraCboPeriodoSegundo(int position)
    {
        CursoPeriodoRequest curso = listCursoPeriodos.get(position);
        periodosSegundo.clear();
        periodosSegundo.add("Período (obrigatório)");

        for(TbPeriodos itemPeriodo: curso.getPeriodos())
        {
            String sCurso = curso.getCurso().nmCurso;

            if (sCurso.toLowerCase().contains("inglês") && !itemPeriodo.dsPeriodo.contains("-"))
            {
                itemPeriodo.dsPeriodo += " - " + itemPeriodo.hrEntrada;
            }

            periodosSegundo.add(itemPeriodo.dsPeriodo);
        }

        ArrayAdapter<String> adatperCurso = new ArrayAdapter<String>(FrmInscricao.this, R.layout.spn_item, periodosSegundo);
        adatperCurso.setDropDownViewResource(R.layout.spn_list);
        cboPeriodoSegundo.setAdapter(adatperCurso);
        lnlCboPeriodoSegundo.setVisibility(View.VISIBLE);

        if(isAlterando && opcaoCursoAlterando.getDsTurnoSegundaOpcao() != null)
        {
            int pos = posicaoComboPreDefinido(opcaoCursoAlterando.getDsTurnoSegundaOpcao(), periodosSegundo);
            cboPeriodoSegundo.setSelection(pos);
        }
    }

    public void iniciaAlteracao()
    {
        if(isAlterando)
        {
            RetornaDadosInscricaoCallback callback = new RetornaDadosInscricaoCallback()
            {
                @Override
                public void onSuccess(InscricaoRequestModel inscricao)
                {
                    preencheDados(inscricao);
                    acabaLoad();
                }

                @Override
                public void onError(ErrorModel error)
                {
                    acabaLoad();
                    errorConnection.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    bottomNavigationView.setVisibility(View.GONE);
                }
            };

            InscricaoRequest inscricaoRequest = new InscricaoRequest();
            inscricaoRequest.retornaDadosInscricao(Ultil.recuperaDadosUsuario(this).getIdUsuario(), callback, Ultil.retornaTokenAuth(this));
        }
    }

    public void preencheDados(InscricaoRequestModel inscricao)
    {
        try
        {
            //Aba usuario
            txtRGCandidato.setText(inscricao.getUsuario().getNrRg());
            txtCPFCandidato.setText(inscricao.getUsuario().getDsCpf());
            txtNomeCandidato.setText(inscricao.getUsuario().getNmUsuario());
            txtOrgaoRGCandidato.setText(inscricao.getUsuario().getDsOrgaoRg());
            cboSexoCandidato.setSelection(posicaoComboPreDefinido(inscricao.getUsuario().getDsSexo(), listSexo));
            txtDataEmissaoRGCandidato.setText(Ultil.formataDataSQLParaConvencional(inscricao.getUsuario().getDtEmissaoRg()));
            txtDataNascimentoCandidato.setText(Ultil.formataDataSQLParaConvencional(inscricao.getUsuario().getDtNascimento()));
            txtEmailCandidato.setText(inscricao.getUsuario().getDsEmail());
            String teste = inscricao.getUsuario().getDsTelefone().replaceAll("[()-]", "");
            txtTelefoneCandidato.setText(teste);

            //aba nascimento/residencia
            txtUFNascimento.setText(inscricao.getNascimento().getDsUf());
            txtCidadeNascimento.setText(inscricao.getNascimento().getDsCidade());
            txtNascionalidade.setText(inscricao.getNascimento().getDsNascionalidade());

            txtCEP.setText(inscricao.getResidencia().getNrCep());
            txtBairro.setText(inscricao.getResidencia().getDsBairro());
            txtUFResidencida.setText(inscricao.getResidencia().getDsUf());
            txtEndereco.setText(inscricao.getResidencia().getDsEndereco());
            txtNumeroCasa.setText(inscricao.getResidencia().getNrResidencia());
            txtCidadeResidencia.setText(inscricao.getResidencia().getDsCidade());
            txtComplemento.setText(inscricao.getResidencia().getDsComplemento());

            //aba curso responsavel
            cboParentesco.setSelection(posicaoComboPreDefinido(inscricao.getResponsavel().getDsParentesco(), listParentesco));
            txtEmailPrimeiroResponsavel.setText(inscricao.getResponsavel().getDsEmail());
            txtNomePrimeiroResponsavel.setText(inscricao.getResponsavel().getNmResponsavel());
            txtTelefonePrincipalResponsavelPrimeiro.setText(inscricao.getResponsavel().getNrTelefone());
            txtTelefoneSecundarioPrimeiroResponsavel.setText(inscricao.getResponsavel().getNrTelefoneSec());

            //segundo responsavel
            txtEmailSegundoResponsavel.setText(inscricao.getResponsavelSecundario().getDsEmail());
            cboParentescoSegundo.setSelection(posicaoComboPreDefinido(inscricao.getResponsavelSecundario().getDsParentesco(), listParentesco));
            txtNomeSegundoResponsavel.setText(inscricao.getResponsavelSecundario().getNmResponsavel());
            txtTelefonePrincipalResponsavelSegundo.setText(inscricao.getResponsavelSecundario().getNrTelefone());
            txtTelefoneSecundarioSegundoResponsavel.setText(inscricao.getResponsavelSecundario().getNrTelefoneSec());

            //aba curso

            //escolaridade
            cboEscolaridade.setSelection(posicaoComboPreDefinido(Ultil.ConverteEscolaridade(inscricao.getEscolaridade().getDsEscolaridade(), false), listEscolaridade));
            cboTipoEscola.setSelection(posicaoComboPreDefinido(inscricao.getEscolaridade().getTpEscola(), listTipoEscola));
            txtEscola.setText(inscricao.getEscolaridade().getDsEscola());

            String renda = String.valueOf(inscricao.getInformacoesExtra().getDsRenda());

            txtRendaFamiliar.setText(String.format("%.2f", inscricao.getInformacoesExtra().getDsRenda()));
            txtQtdPessoasTrabalhando.setText(String.valueOf(inscricao.getInformacoesExtra().getQtdPessoasTrabalhando()));
            txtQtdPessoasResidencia.setText(String.valueOf(inscricao.getInformacoesExtra().getQtdPessoasResidentesCasa()));
            cboComoConheceu.setSelection(posicaoComboPreDefinido(inscricao.getInformacoesExtra().getDsComoConheceu(), listComoConheceu));


            //define id's para alteração
            nascimento.setIdNascimento(inscricao.getNascimento().getIdNascimento());
            residencia.setIdResidencia(inscricao.getResidencia().getIdResidencia());
            responsavelPrimeiro.setIdReponsavel(inscricao.getResponsavel().getIdReponsavel());
            responsavelSegundo.setIdReponsavel(inscricao.getResponsavelSecundario().getIdReponsavel());
            escolaridade.setIdEscolaridade(inscricao.getEscolaridade().getIdEscolaridade());
            curso.setIdCursoEscolhido(inscricao.getOpcaoCurso().getIdCursoEscolhido());
            informacoesExtra.setIdInformacaoExtra(inscricao.getInformacoesExtra().getIdInformacaoExtra());
            responsavelPrimeiro.setIdUsuario(inscricao.getUsuario().getIdUsuario());
            responsavelSegundo.setIdUsuario(inscricao.getUsuario().getIdUsuario());

            opcaoCursoAlterando = new TbOpcaoCurso();
            opcaoCursoAlterando.setDsTurnoPrimeiraOpcao(inscricao.getOpcaoCurso().getDsTurnoPrimeiraOpcao());

            String nmCurso = retornaNomeCurso(inscricao.getOpcaoCurso().getIdCursoPrimeiraOpcao());
            int posicao = posicaoComboPreDefinido(nmCurso, nomesCursos);
            cboCursoPrimeiro.setSelection(posicao);
            configuraCboPeriodoPrimereiro(posicao - 1);

            if(inscricao.getOpcaoCurso().getIdCursoSegundaOpcao() > 0)
            {
                possuiSegundaOpcao = true;
                String nmCursoSegundo = retornaNomeCurso(inscricao.getOpcaoCurso().getIdCursoSegundaOpcao());
                int posicaoSegundo = posicaoComboPreDefinido(nmCursoSegundo, nomesCursos);

                cboCursoSegundo.setSelection(posicaoSegundo);
                opcaoCursoAlterando.setDsTurnoSegundaOpcao(inscricao.getOpcaoCurso().getDsTurnoSegundaOpcao());

                configuraCboPeriodoSegundo(posicaoSegundo - 1);
            }
        }
        catch (Exception err)
        {
            acabaLoad();
            exibeSnack("Erro inesperado, tente novamente mais tarde.");
        }
    }

    public int posicaoComboPreDefinido(String itemRec, List<String> comboPadrao)
    {
        int i = 0;

        for (String item: comboPadrao)
        {
            if (item.contains(itemRec))
            {
                return i;
            }

            i++;
        }

        return 0;
    }

    public String retornaNomeCurso(int id)
    {
        if(listCursoPeriodos != null)
        {
            for(CursoPeriodoRequest curso : listCursoPeriodos)
            {
                if(curso.getCurso().idCurso == id)
                {
                    return curso.getCurso().nmCurso;
                }
            }
        }

        return "";
    }

    public void apontaCampo(EditText txtPadrao, int id)
    {
        txtPadrao.requestFocus();
        Ultil.moveScroll(scrollView, txtPadrao);
        bottomNavigationView.setSelectedItemId(id);

//        dlgAviso.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                Ultil.moveScroll(scrollView, txtPadrao);
//                Ultil.mostraTeclado(txtPadrao, FrmInscricao.this);
//            }
//        });
    }

    public void apontaCampo(Spinner cboPadrao, int id)
    {
        cboPadrao.requestFocus();
        Ultil.moveScroll(scrollView, cboPadrao);
        bottomNavigationView.setSelectedItemId(id);
    }

    @Override
    public void onClick(View v)
    {
        int iView = v.getId();

        if (iView == layoutProximo.getId() || iView == layoutProximoLtCandidatoCont.getId()
           || iView ==layoutProximoLtCurso.getId() || iView == layoutProximoCursoCont.getId())
        {
            proximoLayout();
        }
        else if (iView == layoutVoltarLtInfoExtra.getId() || iView == layoutVoltarLtCursoCont.getId()
            || iView == layoutVoltarLtCurso.getId() || iView == layoutVoltarLtCandidatoCont.getId())
        {
            voltaLayout();
        }
        else if (iView == lnlTenteNovamnete.getId())
        {
            errorConnection.setVisibility(View.GONE);
            configuraSpinners();
        }
        else if (iView == lnlEnviar.getId())
        {
            enviaDados();
        }
    }

    public void exibeViewsCadastro(View view)
    {
        layoutCurso.setVisibility(View.GONE);
        layoutCandidato.setVisibility(View.GONE);
        layoutCursoContinuacao.setVisibility(View.GONE);
        layoutInformacoesExtra.setVisibility(View.GONE);
        layoutCandidatoContinuacao.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }

    public boolean validaAba()
    {
        switch (viewAnterior.getId())
        {
            case R.id.layoutCandidato:
                idAbaValidando = R.id.candidato;
                if(!validacaoAba1())
                {
                    isValidando = 1;
                    viewAnterior = layoutCandidato;
                    return false;
                }
                return true;

            case R.id.layoutCandidatoContinuacao:
                idAbaValidando = R.id.cadidatopt2;
                if(!validacaoAba2())
                {
                    isValidando = 1;
                    viewAnterior = layoutCandidatoContinuacao;
                    return false;
                }
                return true;

            case R.id.layoutCurso:
                idAbaValidando = R.id.curso;
                if(!validacaoAba3())
                {
                    isValidando = 1;
                    viewAnterior = layoutCandidatoContinuacao;
                    return false;
                }
                return true;
            case R.id.layoutCursoContinuacao:
                idAbaValidando = R.id.cursopt2;
                if(!validacaoAba4())
                {
                    isValidando = 1;
                    viewAnterior = layoutCursoContinuacao;
                    return false;
                }
                return true;
        }

        return true;
    }

    public boolean validacaoAba1()
    {
        //candidato
        if (txtNomeCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O nome do cadidato é obrigatório!");
            return false;
        }

        if (txtEmailCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O e-mail do cadidato é obrigatório!");
            return false;
        }

        if (txtTelefoneCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O telefone do cadidato é obrigatório!");
            return false;
        }

        if (txtDataNascimentoCandidato.getText().toString().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "A data de nascimento é obrigatória");
            return false;
        }

        if(txtDataNascimentoCandidato.getText() != null)
        {
            if (!Ultil.validaData(txtDataNascimentoCandidato.getText().toString()))
            {
                dlgAviso = new DialogAviso(this, "Data de nascimento inválida");
                return false;
            }
        }

        if (cboSexoCandidato.getSelectedItem().equals(cboSexoCandidato.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Nenhum sexo selecionado");
            return false;
        }


        if (txtCPFCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "CPF é obrigatório");
            return false;
        }

        if (txtRGCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O RG é obrigatório");
            return false;
        }

        if (txtOrgaoRGCandidato.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O orgão do RG é obrigatório");
            return false;
        }

        if (txtDataEmissaoRGCandidato.getText().toString().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Data de emissão do RG é obrigatório");
            return false;
        }

        if(txtDataEmissaoRGCandidato.getText() != null)
        {
            if(!Ultil.validaData(txtDataEmissaoRGCandidato.getText().toString()))
            {
                dlgAviso = new DialogAviso(this, "Data de emissão de RG inválida");
                return false;
            }
        }

        return true;
    }

    public boolean validacaoAba2()
    {
        //candidato conti
        if (txtCidadeNascimento.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "A cidade de nascimento é obrigatório");
            apontaCampo(txtCidadeNascimento, R.id.cadidatopt2);
            return false;
        }

        if (txtUFNascimento.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "UF da cidade de nascimento é obrigátório");
            apontaCampo(txtUFNascimento, R.id.cadidatopt2);
            return false;
        }

        if (txtNascionalidade.getText().toString().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Nacionalidade é obrigatória");
            apontaCampo(txtNascionalidade, R.id.cadidatopt2);
            return false;
        }

        if (txtCEP.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O cep é obrigatório");
            apontaCampo(txtCEP, R.id.cadidatopt2);
            return false;
        }

        if (txtCidadeResidencia.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "A cidade de residênicia é obrigatória");
            apontaCampo(txtCidadeResidencia, R.id.cadidatopt2);
            return false;
        }

        if (txtUFResidencida.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "A UF de residência é obrigatória");
            apontaCampo(txtUFResidencida, R.id.cadidatopt2);
            return false;
        }

        if (txtEndereco.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Logradouro é obrigatório!");
            apontaCampo(txtEndereco, R.id.cadidatopt2);
            return false;
        }

        if (txtBairro.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Bairro de residencia é obrigatório");
            apontaCampo(txtBairro, R.id.cadidatopt2);
            return false;
        }

        if (txtNumeroCasa.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Número da casa é obrigatório");
            apontaCampo(txtNumeroCasa, R.id.cadidatopt2);
            return false;
        }

        return true;
    }

    public boolean validacaoAba3()
    {
        //curso
        if (txtNomePrimeiroResponsavel.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Nome do primeiro responsável é obrigatório");
            apontaCampo(txtNomePrimeiroResponsavel, R.id.curso);
            return false;
        }

        if (txtTelefonePrincipalResponsavelPrimeiro.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Telefone do primeiro responsável é obrigatório");
            apontaCampo(txtTelefonePrincipalResponsavelPrimeiro, R.id.curso);
            return false;
        }


        if (txtEmailPrimeiroResponsavel.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "E-mail do primeiro responsável é obrigatório");
            apontaCampo(txtEmailPrimeiroResponsavel, R.id.curso);
            return false;
        }

        if (cboParentesco.getSelectedItem().equals(cboParentesco.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Nenhum parentesco selecionado em 1° responsável");
            apontaCampo(cboParentesco, R.id.curso);
            return false;
        }

        if(txtNomeSegundoResponsavel.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Nome do segundo responsável inválido");
            apontaCampo(txtNomeSegundoResponsavel, R.id.curso);
            return false;
        }

        if (txtTelefonePrincipalResponsavelSegundo.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "Telefone do segundo responsavel é obrigatório");
            apontaCampo(txtTelefonePrincipalResponsavelSegundo, R.id.curso);

            return false;
        }

        if (txtEmailSegundoResponsavel.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "E-mail do segundo responsável é obrigatório");
            apontaCampo(txtEmailSegundoResponsavel, R.id.curso);
            return false;
        }

        if (cboParentescoSegundo.getSelectedItem().equals(cboParentescoSegundo.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Nenhum parentesco selecionado em 2° responsável");
            apontaCampo(cboParentescoSegundo, R.id.curso);
            return false;
        }

        return true;
    }

    public boolean validacaoAba4()
    {
        if(cboEscolaridade.getSelectedItem().equals(cboEscolaridade.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "A escolaridade é obrigatória");
            apontaCampo(cboEscolaridade, R.id.cursopt2);
            return false;
        }

        if(cboTipoEscola.getSelectedItem().equals(cboTipoEscola.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "o tipo da escola é obrigatório.");
            apontaCampo(cboTipoEscola, R.id.cursopt2);
            return false;
        }

        if(txtEscola.getText().toString().trim().isEmpty())
        {
            dlgAviso = new DialogAviso(this, "O nome de sua escola é obrigatório");
            apontaCampo(txtEscola, R.id.cursopt2);
            return false;
        }

        if (cboCursoPrimeiro.getSelectedItem().equals(cboCursoPrimeiro.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Selecione ao menos uma opção de curso");
            apontaCampo(cboCursoPrimeiro, R.id.cursopt2);
            return false;
        }

        if (cboPeriodoPrimeiro.getSelectedItem().equals(cboPeriodoPrimeiro.getItemAtPosition(0)))
        {
            dlgAviso = new DialogAviso(this, "Nenhum periodo de primeiro curso selecionado");
            apontaCampo(cboPeriodoPrimeiro, R.id.cursopt2);
            return false;
        }

        if (!cboCursoSegundo.getSelectedItem().equals(cboCursoSegundo.getItemAtPosition(0)))
        {
            if(cboCursoSegundo.getSelectedItem().toString().equals(cboCursoPrimeiro.getSelectedItem().toString()))
            {
                dlgAviso = new DialogAviso(this, "Os cursos selecionados não podem ser iguais");
                apontaCampo(cboCursoSegundo, R.id.cursopt2);
                return false;
            }

            if (cboPeriodoSegundo.getSelectedItem().equals(cboPeriodoSegundo.getItemAtPosition(0)))
            {
                dlgAviso = new DialogAviso(this, "Nenhum periodo do segundo curso selecionado");
                apontaCampo(cboPeriodoPrimeiro, R.id.cursopt2);
                return false;
            }
        }

        return true;
    }
}
