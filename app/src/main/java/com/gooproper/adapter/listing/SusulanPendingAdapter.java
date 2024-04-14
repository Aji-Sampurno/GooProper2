package com.gooproper.adapter.listing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gooproper.R;
import com.gooproper.model.SusulanModel;

import java.util.List;

public class SusulanPendingAdapter extends RecyclerView.Adapter<SusulanPendingAdapter.HolderData> {

    private List<SusulanModel> models;
    private Context context;

    public SusulanPendingAdapter(Context context, List<SusulanModel> list){
        this.models = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SusulanPendingAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_susulan,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull SusulanPendingAdapter.HolderData holder, int position) {
        SusulanModel susulanModel = models.get(position);

        holder.TVKeterangan.setText(susulanModel.getKeterangan());
        holder.TVTglInput.setText(susulanModel.getTglInput());
        holder.TVPoinTambah.setText(susulanModel.getPoinTambahan());
        holder.TVPoinKurang.setText(susulanModel.getPoinBerkurang());
        holder.susulanModel = susulanModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    class HolderData extends RecyclerView.ViewHolder{
        TextView TVKeterangan, TVPoinTambah, TVPoinKurang, TVTglInput;
        public SusulanModel susulanModel;

        public HolderData(View view){
            super(view);
            TVKeterangan = view.findViewById(R.id.TVKeteranganSusulan);
            TVTglInput = view.findViewById(R.id.TVTglSusulan);
            TVPoinKurang = view.findViewById(R.id.TVPoinKurangSusulan);
            TVPoinTambah = view.findViewById(R.id.TVPoinTambahSusulan);
        }
    }
}
