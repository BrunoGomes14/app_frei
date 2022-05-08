package com.nsf.appfreiinscricoes.ultil.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.ultil.Ultil;

public class DialogAviso extends Dialog
{
    private TextView lblAvisoTexto;
    private LinearLayout btnOk;

    public DialogAviso(@NonNull Context contextParam, String sAvisoParam)
    {
        super(contextParam);

        definePadroes();
        recuperaControles();

        if(!sAvisoParam.isEmpty())
        {
            lblAvisoTexto.setText(sAvisoParam);
        }
        else
        {
            lblAvisoTexto.setText("Aconteceu algum erro durante o processo, tente novamente.");
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public DialogAviso(@NonNull Context contextParam, String sAvisoParam, Activity contextAc)
    {
        super(contextParam);

        definePadroes();
        recuperaControles();

        if(!sAvisoParam.isEmpty())
        {
            lblAvisoTexto.setText(sAvisoParam);
        }
        else
        {
            lblAvisoTexto.setText("Aconteceu algum erro durante o processo, tente novamente.");
        }

        btnOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                contextAc.finish();
                dismiss();
            }
        });
    }

    public void recuperaControles()
    {
        lblAvisoTexto = (TextView) findViewById(R.id.lblTextoAviso);
        btnOk =  (LinearLayout) findViewById(R.id.btnOk);
    }

    public void definePadroes()
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_aviso);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
