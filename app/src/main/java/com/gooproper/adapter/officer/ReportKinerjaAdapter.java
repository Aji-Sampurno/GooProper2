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
import com.gooproper.model.ReportKinerjaModel;
import com.gooproper.util.FormatCurrency;

import java.util.List;

public class ReportKinerjaAdapter extends RecyclerView.Adapter<ReportKinerjaAdapter.HolderData> {

    List<ReportKinerjaModel> models;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;

    public ReportKinerjaAdapter(Context context, List<ReportKinerjaModel> list){
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
    public ReportKinerjaAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_report_kinerja,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        ReportKinerjaModel reportKinerjaModel = models.get(position);
        if (reportKinerjaModel.getPriority().equals("open")){
            holder.Lytpriority.setVisibility(View.INVISIBLE);
        } else {
            holder.Lytpriority.setVisibility(View.VISIBLE);
        }
        if (!reportKinerjaModel.getTemplate().equals("null")) {
            Glide.with(context).load(models.get(position).getTemplate()).into(holder.IVTemplate);
        } else {
            Glide.with(context).load(models.get(position).getImg1()).into(holder.IVTemplate);
        }

        holder.TVAlamat.setText(reportKinerjaModel.getAlamat());

        holder.TVListingan.setText(reportKinerjaModel.getJenisProperti()+" "+reportKinerjaModel.getKondisi());
        holder.TVKeterangan.setText(reportKinerjaModel.getKeterangan());

        holder.reportKinerjaModel = reportKinerjaModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    
    class HolderData extends RecyclerView.ViewHolder{
        TextView TVListingan, TVAlamat, TVKeterangan, TVpriority;
        ImageView IVTemplate;
        LinearLayout Lytpriority;
        public ReportKinerjaModel reportKinerjaModel;

        public HolderData(View view){
            super(view);
            TVListingan = view.findViewById(R.id.TVListingan);
            TVAlamat = view.findViewById(R.id.TVAlamat);
            TVKeterangan = view.findViewById(R.id.TVKeterangan);
            TVpriority = view.findViewById(R.id.TVPriority);
            IVTemplate = view.findViewById(R.id.IVTemplate);
            Lytpriority = view.findViewById(R.id.LytBadge);
        }
    }
}
