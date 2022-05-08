package com.nsf.appfreiinscricoes.ultil.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.nsf.appfreiinscricoes.R;

public class DialogLoad extends Dialog {

    public DialogLoad(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_load);
        setCancelable(false);

        ImageView img = findViewById(R.id.imgLoad);
        Glide.with(getContext()).load(R.drawable.load_principal ).into(img);
    }
}
