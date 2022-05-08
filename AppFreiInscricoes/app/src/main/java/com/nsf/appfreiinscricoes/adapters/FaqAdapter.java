package com.nsf.appfreiinscricoes.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.model.TbFaq;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter
{
    ArrayList<TbFaq> listFaq;
    Context context;
    boolean isOpen = true;
    int iQtd = 1;

    public FaqAdapter(ArrayList<TbFaq> faqs, Context contextP)
    {
        listFaq = faqs;
        context = contextP;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        FaqViewHolder faqViewHolder = (FaqViewHolder) holder;
        TbFaq faq = listFaq.get(position);

        faqViewHolder.lnlCard.setTag(position);
        faqViewHolder.lblPergunta.setText(faq.dsPergunta);
        faqViewHolder.lblResposta.setText(Html.fromHtml(faq.dsResposta));
        faqViewHolder.lblPos.setText(String.valueOf(position + 1));

        boolean isExpanded = faq.isExpanded();
        Drawable arrowUp = context.getResources().getDrawable(R.drawable.ic_arrow_up);
        Drawable arrowDown = context.getResources().getDrawable(R.drawable.ic_arrow_down);

        faqViewHolder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        faqViewHolder.imgArrow.setImageDrawable(isExpanded ? arrowUp : arrowDown);

        if (!isOpen)
        {
            isOpen = false;
            faqViewHolder.lnlCard.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        }

        faqViewHolder.lnlCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                faq.setExpanded(!faq.isExpanded());
                notifyItemChanged((int) faqViewHolder.lnlCard.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFaq.size();
    }

    public class FaqViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout expandableLayout;
        LinearLayout lnlCard;
        TextView lblPergunta;
        TextView lblResposta;
        TextView lblPos;
        ImageView imgArrow;

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);

            lblPergunta = (TextView) itemView.findViewById(R.id.lblPergunta);
            lblResposta = (TextView) itemView.findViewById(R.id.lblResposta);
            expandableLayout = (LinearLayout) itemView.findViewById(R.id.expandableLayout);
            lnlCard = (LinearLayout) itemView.findViewById(R.id.lnlCard);
            imgArrow = (ImageView) itemView.findViewById(R.id.imgArrow);
            lblPos = itemView.findViewById(R.id.lblPos);
        }
    }


}


