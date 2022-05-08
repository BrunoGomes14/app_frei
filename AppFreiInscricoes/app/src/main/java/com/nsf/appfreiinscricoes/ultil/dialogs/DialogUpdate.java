package com.nsf.appfreiinscricoes.ultil.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.ultil.Ultil;

public class DialogUpdate extends Dialog implements View.OnClickListener {

    Activity context;
    private TextView lblAtualizar;
    private TextView lblCancelar;

    public DialogUpdate(@NonNull Activity context)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_aviso_update);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.context = context;

        recuperaControle();
    }

    public void recuperaControle()
    {
        lblAtualizar = findViewById(R.id.lblAtualizar);
        lblCancelar = findViewById(R.id.lblCancelar);

        lblAtualizar.setOnClickListener(this);
        lblCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == lblAtualizar.getId())
        {
            String packageNm = Ultil.InfoApp(context).packageName;

            try
            {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageNm)));
            }
            catch (android.content.ActivityNotFoundException anfe)
            {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageNm)));
            }
        }
        else if (v.getId() == lblCancelar.getId())
        {
            context.finish();
        }
    }
}
