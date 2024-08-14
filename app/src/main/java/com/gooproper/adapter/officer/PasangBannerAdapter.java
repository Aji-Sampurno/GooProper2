package com.gooproper.adapter.officer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gooproper.R;
import com.gooproper.model.PasangBannerModel;
import com.gooproper.model.ReportKinerjaModel;
import com.gooproper.util.FormatCurrency;

import java.util.List;

public class PasangBannerAdapter extends RecyclerView.Adapter<PasangBannerAdapter.HolderData> {

    List<PasangBannerModel> models;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;

    public PasangBannerAdapter(Context context, List<PasangBannerModel> list){
        this.models = list;
        this.context = context;
    }

    private String truncateTextWithEllipsis(String text) {
        if (text.length() > MAX_TEXT_LENGTH) {
            return text.substring(0, MAX_TEXT_LENGTH) + " ...";
        } else {
            return text;
        }
    }

    @NonNull
    @Override
    public PasangBannerAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_pasang_banner,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        PasangBannerModel pasangBannerModel = models.get(position);
        if (pasangBannerModel.getPriority().equals("open")){
            holder.Lytpriority.setVisibility(View.INVISIBLE);
        } else {
            holder.Lytpriority.setVisibility(View.VISIBLE);
        }
        Glide.with(context).load(models.get(position).getImg1()).into(holder.IVTemplate);
        holder.TVAlamat.setText(pasangBannerModel.getAlamat());

        holder.TVListingan.setText(pasangBannerModel.getNamaListing());
        holder.TVKondisi.setText(pasangBannerModel.getKondisi());
        holder.TVSize.setText(pasangBannerModel.getSize());

        holder.pasangBannerModel = pasangBannerModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    
    class HolderData extends RecyclerView.ViewHolder{
        TextView TVListingan, TVKondisi, TVAlamat, TVSize, TVpriority;
        ImageView IVTemplate;
        LinearLayout Lytpriority;
        public PasangBannerModel pasangBannerModel;

        public HolderData(View view){
            super(view);
            TVListingan = view.findViewById(R.id.TVListingan);
            TVKondisi = view.findViewById(R.id.TVKondisi);
            TVAlamat = view.findViewById(R.id.TVAlamat);
            TVSize = view.findViewById(R.id.TVSize);
            TVpriority = view.findViewById(R.id.TVPriority);
            IVTemplate = view.findViewById(R.id.IVTemplate);
            Lytpriority = view.findViewById(R.id.LytBadge);
        }
    }
}
