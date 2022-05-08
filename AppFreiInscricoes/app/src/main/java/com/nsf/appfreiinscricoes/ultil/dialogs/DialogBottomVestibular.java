package com.nsf.appfreiinscricoes.ultil.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.requestsAPI.VestibularResquest;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaVestibularCallback;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.VestibularModel;
import com.nsf.appfreiinscricoes.telas.FrmNavegador;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nsf.appfreiinscricoes.ultil.Ultil;

public class DialogBottomVestibular extends BottomSheetDialog implements View.OnClickListener
{
    private ImageView imgResult;
    private TextView lblResult;
    private TextView lblData;
    private TextView lblHorario;
    private TextView lblProva;
    private LinearLayout lnlResultado;
    private LinearLayout lnlInfoVestibular;
    private Button btnFechar;
    private LinearLayout lnlSeparacaoResult;

    Context context;
    String linkProva;

    public DialogBottomVestibular(@NonNull Context context)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_bottom_vestibular);

        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recuperaControles();
    }

    @Override
    public void show() {
        super.show();
        carregaVestibular();
    }

    @Override
    protected void onStop() {
        super.onStop();
        linkProva = "";
    }

    public void recuperaControles()
    {
        btnFechar = findViewById(R.id.btnFechar);
        imgResult = findViewById(R.id.imgResult);
        lblResult = findViewById(R.id.lblResult);
        lblData = findViewById(R.id.lblData);
        lblHorario = findViewById(R.id.lblHorario);
        lblProva = findViewById(R.id.lblProva);
        lnlResultado = findViewById(R.id.lnlResultado);
        lnlInfoVestibular = findViewById(R.id.lnlInfoVestibular);
        btnFechar.setOnClickListener(this);
    }

    public void carregaVestibular()
    {
        lnlInfoVestibular.setVisibility(View.GONE);
        Glide.with(context).load(R.drawable.load_principal).into(imgResult);
        lblResult.setText("Obtendo dados de vestibular");
        lnlResultado.setVisibility(View.VISIBLE);

        RetornaVestibularCallback callback = new RetornaVestibularCallback()
        {
            @Override
            public void onSucess(VestibularModel vestibular)
            {
                String data = Ultil.formataDataSQLParaConvencional(vestibular.dtVestibular);
                String horario = Ultil.retornaHoraSemSegundos(vestibular.hrInicio);
                horario += " às ";
                horario += Ultil.retornaHoraSemSegundos(vestibular.hrFim);

                lblHorario.setText(horario);
                lblData.setText(data);
                lblProva.setText(vestibular.linkProva);

                if (vestibular.provaDisponivel == 1)
                {
                    linkProva = vestibular.linkProva;
                    lblProva.setText("Acessar prova");
                    lblProva.setOnClickListener(DialogBottomVestibular.this);
                    lblProva.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }
                else
                {
                    lblProva.setTextColor(context.getResources().getColor(R.color.Preto));
                }

                lnlResultado.setVisibility(View.GONE);
                lnlInfoVestibular.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(ErrorModel erro)
            {
                Glide.with(context).load(R.drawable.gif_error).into(imgResult);
                lblResult.setText(erro.getMensagemErro());
            }
        };

        VestibularResquest api = new VestibularResquest();
        api.retornaVestibular(callback, Ultil.retornaTokenAuth(getContext()) ,Ultil.recuperaDadosUsuario(context).getIdUsuario());
    }

    @Override
    public void onClick(View v)
    {
        try
        {
            if (v.getId() == lblProva.getId())
            {
                if (linkProva != null)
                {
                    Intent webIntent = new Intent(context, FrmNavegador.class);
                    webIntent.putExtra("url",linkProva);
                    webIntent.putExtra("title", "Vestibular");
                    webIntent.putExtra("isNav", true);
                    context.startActivity(webIntent);
                }
            }
            else if(v.getId() == btnFechar.getId())
            {
                dismiss();
            }
        }
        catch (Exception ex)
        {

        }
    }
}
