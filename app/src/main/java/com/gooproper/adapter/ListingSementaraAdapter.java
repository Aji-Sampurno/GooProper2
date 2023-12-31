package com.gooproper.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gooproper.R;
import com.gooproper.model.ListingSementaraModel;
import com.gooproper.ui.detail.DetailListingActivity;
import com.gooproper.ui.tambah.TambahDetailListingSementaraActivity;

import java.util.List;

public class ListingSementaraAdapter extends RecyclerView.Adapter<ListingSementaraAdapter.HolderData> {

    List<ListingSementaraModel> models;
    private Context context;

    public ListingSementaraAdapter(Context context, List<ListingSementaraModel> list){
        this.models = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ListingSementaraAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listingsementara,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull ListingSementaraAdapter.HolderData holder, int position) {
        ListingSementaraModel listingSementaraModel = models.get(position);
        holder.TVAlamat.setText(listingSementaraModel.getAlamat());
        holder.TVLokasi.setText(listingSementaraModel.getLokasi());
        holder.listingSementaraModel = listingSementaraModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView TVAlamat, TVLokasi;
        public ListingSementaraModel listingSementaraModel;
        public HolderData(View view){
            super(view);
            TVAlamat=view.findViewById(R.id.TVAlamat);
            TVLokasi=view.findViewById(R.id.TVLokasi);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, TambahDetailListingSementaraActivity.class);
                    update.putExtra("update",1);
                    update.putExtra("IdShareLokasi",listingSementaraModel.getIdShareLokasi());
                    update.putExtra("IdAgen",listingSementaraModel.getIdAgen());
                    update.putExtra("Alamat",listingSementaraModel.getAlamat());
                    update.putExtra("Lokasi",listingSementaraModel.getLokasi());
                    update.putExtra("Latitude",listingSementaraModel.getLatitude());
                    update.putExtra("Longitude",listingSementaraModel.getLongitude());
                    update.putExtra("Selfie",listingSementaraModel.getSelfie());
                    context.startActivity(update);
                }
            });
        }
    }
}
