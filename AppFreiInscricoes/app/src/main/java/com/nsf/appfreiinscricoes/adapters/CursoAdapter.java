package com.nsf.appfreiinscricoes.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.OnClickCallback;
import com.nsf.appfreiinscricoes.interfaces.RotasAPI;
import com.nsf.appfreiinscricoes.model.Requests.TbCursoRequest;

import java.util.ArrayList;

public class CursoAdapter extends RecyclerView.Adapter implements View.OnClickListener
{
    Context context;
    ArrayList<TbCursoRequest> cursos;
    OnClickCallback onClick;

    public CursoAdapter(Context contextParam, ArrayList<TbCursoRequest> cursoParam, OnClickCallback onClickParam)
    {
        context = contextParam;
        cursos = cursoParam;
        onClick = onClickParam;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_cursos, parent, false);
        HolderCurso holderCurso = new HolderCurso(view);
        return holderCurso;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        TbCursoRequest itemCurso;

        itemCurso = cursos.get(position);
        HolderCurso holderCurso = (HolderCurso) holder;

        String url = RotasAPI.URL_BASE + "Cursos/getImgCurso/" + itemCurso.curso.idImagem;

        Glide.with(context).load(url).circleCrop().into(holderCurso.imgCurso);
        holderCurso.imgCurso.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        holderCurso.lnlCurso.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holderCurso.lblTipoCurso.setText(itemCurso.tpCurso.tpCurso + " - " + itemCurso.detalhesCurso.dsCargaHoraria);
        holderCurso.lblNomeCurso.setText(itemCurso.curso.nmCurso);
        holderCurso.lblPretexto.setText(Html.fromHtml(itemCurso.detalhesCurso.dsVisaoGeral));
        holderCurso.lnlCurso.setOnClickListener(this);
        holderCurso.lnlCurso.setTag(position);
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }

    @Override
    public void onClick(View v)
    {
        onClick.onClick((int)v.getTag());
    }

    public class HolderCurso extends RecyclerView.ViewHolder
    {
        TextView lblNomeCurso;
        TextView lblTipoCurso;
        RelativeLayout lnlCurso;
        TextView lblPretexto;
        ImageView imgCurso;

        public HolderCurso(@NonNull View itemView)
        {
            super(itemView);

            lblPretexto = itemView.findViewById(R.id.lblPreTexto);
            lblNomeCurso = itemView.findViewById(R.id.b);
            lblTipoCurso = itemView.findViewById(R.id.d);
            lnlCurso = itemView.findViewById(R.id.lnlCurso);
            imgCurso = itemView.findViewById(R.id.a);
        }
    }
}
