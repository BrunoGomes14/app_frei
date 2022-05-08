package com.nsf.appfreiinscricoes.telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.requestsAPI.CursosRequest;
import com.nsf.appfreiinscricoes.adapters.CursoAdapter;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.OnClickCallback;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaCursoCallbacks;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.Requests.TbCursoRequest;
import com.nsf.appfreiinscricoes.ultil.dialogs.DialogLoad;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class FrmCursos extends AppCompatActivity implements OnClickCallback {

    private Toolbar toolbar;
    private ActionBar actionbar;
    private LinearLayout errorConnection;
    private LinearLayout lnlTentarNovamente;
    private RecyclerView rvCursos;
    private ArrayList<TbCursoRequest> listCursos;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_cursos);
        RecuperaControles();
        ConfiguraToolbar();
        ConfiguraRecyclerView();
        TentarNovamente();
    }


    @Override
    protected void onStop()
    {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_in);
    }

    public void RecuperaControles()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvCursos = (RecyclerView) findViewById(R.id.rvCursos);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        errorConnection = findViewById(R.id.errorConection);
        lnlTentarNovamente = findViewById(R.id.lnlTentarNovamente);
    }

    public void ConfiguraRecyclerView()
    {
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        rvCursos.setVisibility(View.GONE);

        RetornaCursoCallbacks callbacks = new RetornaCursoCallbacks() {
            @Override
            public void onSuccess(@NonNull ArrayList<TbCursoRequest> cursos) {

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                listCursos = cursos;

                CursoAdapter cursoAdapter = new CursoAdapter(FrmCursos.this, listCursos , FrmCursos.this );
                rvCursos.setAdapter(cursoAdapter);

                LinearLayoutManager lnlManager = new LinearLayoutManager(FrmCursos.this, RecyclerView.VERTICAL, false);
                rvCursos.setLayoutManager(lnlManager);
                rvCursos.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(ErrorModel error)
            {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                rvCursos.setVisibility(View.GONE);
                errorConnection.setVisibility(View.VISIBLE);
            }
        };

        CursosRequest cursosRequest = new CursosRequest();
        cursosRequest.ListaCursos(callbacks, Ultil.retornaTokenAuth(this));
    }

    public void ConfiguraToolbar()
    {
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Branco));
        toolbar.setTitle("Cursos");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId)
        {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_in);
                return true;
        }
        return true;
    }

    @Override
    public void onClick(int position)
    {
        DialogLoad dlgLoad = new DialogLoad(this);
        dlgLoad.show();

        TbCursoRequest curso = listCursos.get(position);

        Intent intent = new Intent(FrmCursos.this, FrmCursoDetalhado.class);
        intent.putExtra("curso", curso);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);

        dlgLoad.dismiss();
    }

    public void TentarNovamente()
    {
        lnlTentarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                errorConnection.setVisibility(View.GONE);
                ConfiguraRecyclerView();
            }
        });
    }
}
