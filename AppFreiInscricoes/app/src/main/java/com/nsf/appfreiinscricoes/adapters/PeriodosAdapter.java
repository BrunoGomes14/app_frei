package com.nsf.appfreiinscricoes.adapters;

import android.app.PendingIntent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.model.TbPeriodos;
import com.nsf.appfreiinscricoes.ultil.Ultil;

import java.util.ArrayList;

public class PeriodosAdapter extends RecyclerView.Adapter
{
    ArrayList<TbPeriodos> periodos;
    Context context;

    public PeriodosAdapter (ArrayList<TbPeriodos> periodos, Context context)
    {
        this.periodos = periodos;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_periodo, parent, false);
        PeriodosAdapter.PeriodoViewHolder holderPeriodo = new PeriodosAdapter.PeriodoViewHolder(view);

        return holderPeriodo;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        TbPeriodos periodo = periodos.get(position);

        PeriodoViewHolder holderPeriodo = (PeriodoViewHolder)holder;

        holderPeriodo.lblPeriodo.setText(periodo.dsPeriodo);
        holderPeriodo.lblHorario.setText(Ultil.criaFraseHorario(periodo.hrEntrada, periodo.hrSaida));

        if (periodo.hrSaida.contains("<br>"))
        {
            int index = periodo.hrSaida.indexOf("<br>");
            holderPeriodo.lblVezesSemana.setText(periodo.hrSaida.substring(index + 4).trim());
        }
        else
        {
            holderPeriodo.lblVezesSemana.setVisibility(View.GONE);
        }

        int idImagem = 0;
        String sPeriodo = periodo.dsPeriodo.toLowerCase();

        if (sPeriodo.contains("manhã"))
        {
            idImagem = R.drawable.sun;
        }
        else if (sPeriodo.contains("tarde"))
        {
            idImagem = R.drawable.sunset;
        }
        else if (sPeriodo.contains("noite"))
        {
            idImagem = R.drawable.moon;
        }

        if (idImagem > 0)
        {
            Glide.with(context).load(idImagem).into(holderPeriodo.imgPeriodo);
        }
    }

    @Override
    public int getItemCount() {
        return periodos.size();
    }

    public class PeriodoViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgPeriodo;
        private TextView lblPeriodo;
        private TextView lblHorario;
        private TextView lblVezesSemana;

        public PeriodoViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imgPeriodo = itemView.findViewById(R.id.imgPeriodo);
            lblPeriodo = itemView.findViewById(R.id.lblTurno);
            lblHorario = itemView.findViewById(R.id.lblHorario);
            lblVezesSemana = itemView.findViewById(R.id.lblVezesSemana);
        }
    }
}
