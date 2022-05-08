package com.nsf.appfreiinscricoes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.model.TbNotificacao;

import java.util.ArrayList;

public class AdapterNotificacoes extends RecyclerView.Adapter
{
    ArrayList<TbNotificacao> notificacoes;
    Context context;

    public AdapterNotificacoes(Context contextParam, ArrayList<TbNotificacao> noticacoesParam)
    {
        context = contextParam;
        notificacoes = noticacoesParam;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_notificacoes, parent, false);
        NotificacaoViewHolder holderNotificacao = new NotificacaoViewHolder(view);

        return holderNotificacao;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        TbNotificacao itemNotificacao;

        itemNotificacao = notificacoes.get(position);
        NotificacaoViewHolder holderNotifi = (NotificacaoViewHolder) holder;

        holderNotifi.lblTitulo.setText(itemNotificacao.getDsTituloNoficacao());
        holderNotifi.lblTexto.setText((itemNotificacao.getMensagem()));
    }

    @Override
    public int getItemCount() {
        return notificacoes.size();
    }

    public class NotificacaoViewHolder extends RecyclerView.ViewHolder
    {
        TextView lblTitulo;
        TextView lblTexto;

        public NotificacaoViewHolder(@NonNull View itemView)
        {
            super(itemView);

            lblTexto = itemView.findViewById(R.id.d);
            lblTitulo = itemView.findViewById(R.id.b);
        }
    }
}
