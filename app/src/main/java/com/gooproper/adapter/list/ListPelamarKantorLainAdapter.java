package com.gooproper.adapter.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gooproper.R;
import com.gooproper.model.AgenModel;
import com.gooproper.ui.detail.DetailListingActivity;

import java.util.List;

public class ListPelamarKantorLainAdapter extends RecyclerView.Adapter<ListPelamarKantorLainAdapter.HolderData> {
    private List<AgenModel> agenModels;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;

    private String truncateTextWithEllipsis(String text) {
        if (text.length() > MAX_TEXT_LENGTH) {
            return text.substring(0, MAX_TEXT_LENGTH) + " ...";
        } else {
            return text;
        }
    }

    public ListPelamarKantorLainAdapter(Context context, List<AgenModel> list){
        this.agenModels = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_pelamar_kantor_lain,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        AgenModel agenModel = agenModels.get(position);
        holder.NamaPelamarKantorLain.setText(agenModel.getNama());
        holder.AlamatPelamarKantorLain.setText(agenModel.getAlamatDomisili());
        holder.TelpPelamarKantorLain.setText(agenModel.getNoTelp());
        Glide.with(context).load(agenModels.get(position).getPhoto()).into(holder.GambarPelamarKantorLain);
    }

    @Override
    public int getItemCount() {
        return agenModels.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView NamaPelamarKantorLain, AlamatPelamarKantorLain, TelpPelamarKantorLain;
        ImageView GambarPelamarKantorLain;
        public AgenModel agenModel;
        public HolderData(View view){
            super(view);
            NamaPelamarKantorLain = view.findViewById(R.id.TVNamaPelamarKantorLain);
            AlamatPelamarKantorLain = view.findViewById(R.id.TVAlamatPelamarKantorLain);
            TelpPelamarKantorLain = view.findViewById(R.id.TVTelpPelamarKantorLain);
            GambarPelamarKantorLain = view.findViewById(R.id.CIVPelamarKantorLain);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(context, DetailListingActivity.class);
                    update.putExtra("update",1);update.putExtra("update",1);
                    update.putExtra("IdAgen",agenModel.getIdAgen());
                    update.putExtra("Username",agenModel.getUsername());
                    update.putExtra("Password",agenModel.getPassword());
                    update.putExtra("Nama",agenModel.getNama());
                    update.putExtra("NoTelp",agenModel.getNoTelp());
                    update.putExtra("Email",agenModel.getEmail());
                    update.putExtra("TglLahir",agenModel.getTglLahir());
                    update.putExtra("KotaKelahiran",agenModel.getKotaKelahiran());
                    update.putExtra("Pendidikan",agenModel.getPendidikan());
                    update.putExtra("NamaSekolah",agenModel.getNamaSekolah());
                    update.putExtra("MasaKerja",agenModel.getMasaKerja());
                    update.putExtra("Jabatan",agenModel.getJabatan());
                    update.putExtra("Status",agenModel.getStatus());
                    update.putExtra("AlamatDomisili",agenModel.getAlamatDomisili());
                    update.putExtra("Facebook",agenModel.getFacebook());
                    update.putExtra("Instagram ",agenModel.getInstagram());
                    update.putExtra("NoKtp",agenModel.getNoKtp());
                    update.putExtra("ImgKtp",agenModel.getImgKtp());
                    update.putExtra("ImgTtd",agenModel.getImgTtd());
                    update.putExtra("Npwp ",agenModel.getNpwp ());
                    update.putExtra("Photo",agenModel.getPhoto());
                    update.putExtra("Poin",agenModel.getPoin ());
                    update.putExtra("IsAkses",agenModel.getIsAkses());
                    context.startActivity(update);
                }
            });
        }
    }
}
