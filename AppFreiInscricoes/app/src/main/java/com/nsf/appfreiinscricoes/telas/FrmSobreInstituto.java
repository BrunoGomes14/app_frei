package com.nsf.appfreiinscricoes.telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.google.android.material.snackbar.Snackbar;

public class FrmSobreInstituto extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_sobre_instituto);

        recuperaControles();
        configuraToolbar();
    }

    LinearLayout btnLigar;
    LinearLayout btnRota;
    Toolbar toolbar;
    ActionBar actionbar;
    LinearLayout lnlLigaTel1;
    LinearLayout lnlEndereco;
    RelativeLayout rnlPrincipal;
    LinearLayout lnlDevenv;
    LinearLayout lnlSite;
    LinearLayout lnlFaceook;
    LinearLayout lnlInstagram;
    TextView lblVersaoCode;
    LinearLayout lnlEmail;

    ClipboardManager clipboard;

    public void recuperaControles()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lnlLigaTel1 = findViewById(R.id.lnlLigaTel1);
        lnlEndereco = findViewById(R.id.lnlEndereco);
        rnlPrincipal = findViewById(R.id.rnlPrincipal);
        lnlDevenv = findViewById(R.id.lblVersao);
        lblVersaoCode = findViewById(R.id.lblVersaoCode);
        lnlSite = findViewById(R.id.lnlSite);
        lnlFaceook = findViewById(R.id.lnlFacebook);
        lnlInstagram = findViewById(R.id.lnlInstagram);
        lnlEmail = findViewById(R.id.lnlEmail);
        lblVersaoCode.setText(Ultil.InfoApp(this).versionName);

        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        lnlLigaTel1.setOnClickListener(this);
        lnlLigaTel1.setOnLongClickListener(this);
        lnlEndereco.setOnClickListener(this);
        lnlEndereco.setOnLongClickListener(this);
        lnlDevenv.setOnLongClickListener(this);
        lnlSite.setOnClickListener(this);
        lnlSite.setOnLongClickListener(this);
        lnlFaceook.setOnClickListener(this);
        lnlFaceook.setOnLongClickListener(this);
        lnlInstagram.setOnClickListener(this);
        lnlInstagram.setOnLongClickListener(this);
        lnlEmail.setOnLongClickListener(this);
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
        toolbar.setTitle("Sobre");
    }

    private void ExibeSnack(String Menssagem)
    {
        Snackbar snackbar = Snackbar
                .make(rnlPrincipal, Menssagem, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimaryOld));
        snackbar.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_in);
                return true;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_in);
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        Intent intentGeral;
        Uri uri;

        try
        {
            if (id == lnlLigaTel1.getId())
            {
                uri = Uri.parse("tel:(011)5687-8876");
                intentGeral = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intentGeral);
            }
            else if (id == lnlEndereco.getId())
            {
                uri = Uri.parse("http://maps.google.com/maps?daddr=Av. Coronel Octaviano de Freitas Costa, 463 - Socorro, São Paulo - SP, 04773-000");
                intentGeral = new Intent(android.content.Intent.ACTION_VIEW, uri);

                startActivity(intentGeral);
            }
            else if (id == lnlSite.getId())
            {
                uri = Uri.parse("https://acaonsfatima.org.br/");
                intentGeral = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intentGeral);
            }
            else if (id == lnlInstagram.getId())
            {
                uri = Uri.parse("https://www.instagram.com/institutonsfatima/");
                intentGeral = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intentGeral);

            }
            else if (id == lnlFaceook.getId())
            {
                try
                {
                    String sUri;
                    PackageManager packageManager = getPackageManager();
                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) {
                        sUri = "fb://facewebmodal/f?href=" + "https://www.facebook.com/Instituto-Social-Nossa-Senhora-de-F%C3%A1tima-230903353703049";
                    } else {
                        sUri =  "fb://page/" + "230903353703049";
                    }

                    intentGeral = new Intent(Intent.ACTION_VIEW);
                    intentGeral.setData(Uri.parse(sUri));
                    startActivity(intentGeral);
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    uri = Uri.parse("https://www.facebook.com/Instituto-Social-Nossa-Senhora-de-F%C3%A1tima-230903353703049");
                    intentGeral = new Intent(android.content.Intent.ACTION_VIEW, uri);
                    startActivity(intentGeral);
                }
            }
        }
        catch (Exception err)
        {
            ExibeSnack("Infelizmente, não consegui abrir essa opção :(");
        }
    }

    @Override
    public boolean onLongClick(View v)
    {
        int id = v.getId();
        ClipData clipGeral;

        if (id == lnlLigaTel1.getId())
        {
            clipGeral = ClipData.newPlainText("Telefone do Instituto", "(011)5687-8876");
            clipboard.setPrimaryClip(clipGeral);

            ExibeSnack("Contato copiado");
        }
        else if (id == lnlEndereco.getId())
        {
            clipGeral = ClipData.newPlainText("Endereço do instituto", "Av. Coronel Octaviano de Freitas Costa, 463 - Socorro, São Paulo - SP");
            clipboard.setPrimaryClip(clipGeral);

            ExibeSnack("Endereço copiado");
        }
        else if (id == lnlSite.getId())
        {
            clipGeral = ClipData.newPlainText("Site do frei", "https://acaonsfatima.org.br/");
            clipboard.setPrimaryClip(clipGeral);

            ExibeSnack("Site copiado");
        }
        else if (id == lnlInstagram.getId())
        {
            clipGeral = ClipData.newPlainText("Instagram do frei", "@institutonsfatima");
            clipboard.setPrimaryClip(clipGeral);

            ExibeSnack("Instagram copiado");
        }
        else if (id == lnlFaceook.getId())
        {
            clipGeral = ClipData.newPlainText("Facebook do instituto", "Instituto Social Nossa Senhora de Fátima");
            clipboard.setPrimaryClip(clipGeral);

            ExibeSnack("Facebook copiado");
        }
        else if (id == lnlEmail.getId())
        {
            clipGeral = ClipData.newPlainText("E-mail do instituto", "secretaria.n-fatima@outlook.com");
            clipboard.setPrimaryClip(clipGeral);

            ExibeSnack("E-mail copiado");
        }
        else if (id == lnlDevenv.getId())
        {
            ExibeSnack("Desenvolvido por Bruno Gomes :)");
        }

        return true;
    }
}
