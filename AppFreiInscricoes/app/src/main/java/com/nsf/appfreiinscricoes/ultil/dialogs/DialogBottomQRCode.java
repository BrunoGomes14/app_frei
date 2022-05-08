package com.nsf.appfreiinscricoes.ultil.dialogs;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nsf.appfreiinscricoes.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DialogBottomQRCode extends BottomSheetDialog {

    DialogBottomQRCode dialogBottomQRCode = this;

    public DialogBottomQRCode(@NonNull Context context, int iCodigo) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_bottom_qrcode);

        ImageView img = findViewById(R.id.imgQRCode);
        img.setVisibility(View.GONE);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.img_erro_carrega_qr);

        String url = "https://chart.apis.google.com/chart?cht=qr&chs=500x500&chl=" + iCodigo;
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .thumbnail(Glide.with(context).load(R.drawable.gif_load_circle))
                .into(img);
        img.setVisibility(View.VISIBLE);

        Button btnFechar = findViewById(R.id.btnFechar);
        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialogBottomQRCode.dismiss();
            }
        });
    }


}
