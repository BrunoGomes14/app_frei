package com.nsf.appfreiinscricoes.ultil.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.requestsAPI.AgendamentoRequest;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.interfaces.OnDialogCloseListener;
import com.nsf.appfreiinscricoes.model.AgendamentoModel;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.ultil.Ultil;

public class DialogAgendamento extends Dialog implements View.OnClickListener
{
    Context context;
    DatePickerDialog dtp;
    private TextView lblSim;
    private TextView lblNao;
    private TextView lblPergunta;
    private TextView lblVerificando;
    private ImageView img;
    private RelativeLayout lnlConfirmacao;
    private LinearLayout lnlVerificando;
    private LinearLayout lnlseparacao;
    private RelativeLayout lnlResultado;
    private ImageView imgResultado;
    private TextView lblCancelar;
    private TextView lblResultado;
    private TextView lblMsgResultado;
    private LinearLayout lnlSeparacaoResult;
    String data = "";
    String hora = "";
    boolean resultado;
    AgendamentoModel model = null;
    OnDialogCloseListener listener;
    Boolean isAlterando;
    String sMensagem;

    public DialogAgendamento(Context contextParam, DatePickerDialog dtp , OnDialogCloseListener listener, String data, String hora, Boolean isAlterando)
    {
        super(contextParam);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_confirma_data);
        setCancelable(false);

        context = contextParam;
        this.data = data;
        this.hora = hora;
        this.dtp = dtp;
        this.listener = listener;
        this.isAlterando = isAlterando;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        recuperaControles();

        String sFrase = "";
        if (!isAlterando)
        {
            sFrase = "Deseja agendar para esta data?\n" + data + " às " + hora;
        }
        else
        {
            sFrase = "Deseja alterar seu agendamento para\n" + data + "às " + hora;
        }

        lblPergunta.setText(sFrase);
        lnlConfirmacao.setVisibility(View.VISIBLE);
        lnlResultado.setVisibility(View.GONE);
        lnlVerificando.setVisibility(View.GONE);
    }

    public void recuperaControles()
    {
        img = findViewById(R.id.imgLoad);
        lblSim = findViewById(R.id.lblSim);
        lblNao = findViewById(R.id.lblNao);
        lblPergunta = findViewById(R.id.lblPergunta);
        lblVerificando = findViewById(R.id.lblVerificando);
        lnlConfirmacao = findViewById(R.id.lnlConfirmacao);
        lnlVerificando = findViewById(R.id.lnlVerificando);
        lnlseparacao = findViewById(R.id.lnlseparacao);
        lnlResultado = findViewById(R.id.lnlResultado);
        imgResultado = findViewById(R.id.imgResutado);
        lblResultado = findViewById(R.id.lblResultado);
        lblCancelar = findViewById(R.id.lblCancelar);
        lblMsgResultado = findViewById(R.id.lblMsgResultado);
        lnlSeparacaoResult = findViewById(R.id.lnlSeparacaoResult);

        lblResultado.setOnClickListener(this);
        lblCancelar.setOnClickListener(this);
        lblNao.setOnClickListener(this);
        lblSim.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == lblSim.getId())
        {
            lnlConfirmacao.setVisibility(View.GONE);
            lnlResultado.setVisibility(View.GONE);
            lnlVerificando.setVisibility(View.VISIBLE);
            Glide.with(context).load(R.drawable.load_principal).into(img);

            enviaDados();
        }
        else if(v.getId() == lblResultado.getId())
        {
            if (resultado)
            {
                listener.onDialogClose(sMensagem);
                dismiss();
            }
            else
            {
                dtp.show();
                dismiss();
            }
        }
        else if(v.getId() == lblNao.getId())
        {
            dismiss();
        }
        else if(v.getId() == lblCancelar.getId())
        {
            dismiss();
        }
    }

    public void enviaDados()
    {
        model = new AgendamentoModel();
        model.setDsCompareceu(0);
        model.setDtAgendamento(Ultil.formataDataConvencionalParaSQL(data));
        model.setHrAgendamento(hora);
        model.setIdUsuario(Ultil.recuperaDadosUsuario(context).getIdUsuario());

        RetornaStringCallback callback = new RetornaStringCallback() {
            @Override
            public void onSucess(String sucesso)
            {
                Ultil.salvaAgendamento(context, model);

                sMensagem = sucesso;
                resultado = true;
                lnlVerificando.setVisibility(View.GONE);
                lblCancelar.setVisibility(View.GONE);
                lnlseparacao.setVisibility(View.GONE);
                lnlSeparacaoResult.setVisibility(View.GONE);
                lblMsgResultado.setText("Tá marcado!\n O seu agendamento é para:\n" + data + " às " + hora);
                lblResultado.setText("FECHAR");

                Glide.with(context).load(R.drawable.gif_check_inscricao).into(imgResultado);

                lnlResultado.setVisibility(View.VISIBLE);
            }

            @Override
            public void OnError(ErrorModel erro)
            {
                resultado = false;
                lnlVerificando.setVisibility(View.GONE);
                lblCancelar.setVisibility(View.VISIBLE);
                lblMsgResultado.setText(erro.getMensagemErro());
                lblResultado.setText("ALTERAR");

                Glide.with(context).load(R.drawable.gif_error).into(imgResultado);
                lnlResultado.setVisibility(View.VISIBLE);
            }
        };

        AgendamentoRequest agendamento = new AgendamentoRequest();
        String sToken = Ultil.retornaTokenAuth(getContext());

        if (!isAlterando)
            agendamento.AgendaPagamento(callback, sToken, model);
        else
            agendamento.AlteraAgendamento(callback, sToken, model);
    }
}
