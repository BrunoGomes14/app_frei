package com.nsf.appfreiinscricoes.ultil.dialogs;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.model.ResumoInscricaoModel;
import com.nsf.appfreiinscricoes.ultil.Ultil;

public class DialogBottomInformacoes extends BottomSheetDialog {

    private TextView lblCPF;
    private TextView lblCurso1;
    private TextView lblCurso2;
    private TextView lblNomeCandidato;
    private TextView lblNumeroInscricao;

    DialogBottomInformacoes context = this;

    public DialogBottomInformacoes(@NonNull Context context, ResumoInscricaoModel resumo) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_bottom_informacoes);

        recuperaControles();
        preencheDados(resumo);
    }

    public void recuperaControles()
    {
        lblCurso1 = findViewById(R.id.lblCurso1);
        lblNomeCandidato = findViewById(R.id.lblNomeCandidato);
        lblNumeroInscricao = findViewById(R.id.lblNumeroInscricao);
        findViewById(R.id.btnFechar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.dismiss();
            }
        });
    }

    public void preencheDados( ResumoInscricaoModel inscricao)
    {
        String cursos = inscricao.getDsCursoPrimeiro() + " - " + inscricao.getDsTurnoPrimeiro();

        if (!inscricao.getDsCursoSegundo().isEmpty())
        {
            cursos += "\n" + inscricao.getDsCursoSegundo() + " - " + inscricao.getDsTurnoSegundo();
        }

        lblCurso1.setText(cursos);
        lblNumeroInscricao.setText(String.valueOf(inscricao.getDsCodigoInscricao()));
        lblNomeCandidato.setText(inscricao.getNmUsuario() + "\n" + Ultil.mascaraCPF(inscricao.getDsCpf())) ;
    }
}
