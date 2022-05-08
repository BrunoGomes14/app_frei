package com.nsf.appfreiinscricoes.ultil.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.firebase.FirebaseIdManeger;
import com.nsf.appfreiinscricoes.telas.FrmLogin;
import com.nsf.appfreiinscricoes.ultil.Ultil;

public class DialogSair extends Dialog implements android.view.View.OnClickListener {

    private Activity context;
    private TextView lblSim;
    private TextView lblNao;
    View view;

    public DialogSair()
    {
        super(null);
    }

    public DialogSair(@NonNull Context context, Activity activity, View view) {
        super(context);
        this.context = activity;
        this.view = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_sair);

        lblSim = findViewById(R.id.lblSim);
        lblNao = findViewById(R.id.lblNao);

        lblSim.setOnClickListener(this);
        lblNao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.lblSim:
                Ultil.excluiPrefs(context);
                Intent intent = new Intent(getContext(), FrmLogin.class);
                context.startActivity(intent);
                context.finish();
                break;
            case R.id.lblNao:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
