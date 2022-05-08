package com.nsf.appfreiinscricoes.ultil.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.telas.FrmSobreInstituto;

public class DialogInformacao extends Dialog
{
    String mensagem;
    DialogInformacao dlgInfo;

    public DialogInformacao(@NonNull Context context, String sMensagem)
    {
        super(context);
        mensagem = sMensagem;
        dlgInfo = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_informacao);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView lblFaleConosco = (TextView) findViewById(R.id.lblFaleConosco);
        TextView lblTextoInformacao = (TextView) findViewById(R.id.lblTextoInformacao);
        Button btnFechar = (Button) findViewById(R.id.btnFechar);
        LinearLayout lnlFechar = (LinearLayout) findViewById(R.id.lnlFechar);

        lblTextoInformacao.setText(mensagem);
        lblFaleConosco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), FrmSobreInstituto.class);
                getContext().startActivity(intent);
            }
        });

        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaDialog();
            }
        });

        lnlFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaDialog();
            }
        });
    }

    public void fechaDialog()
    {
        dlgInfo.dismiss();
    }
}
