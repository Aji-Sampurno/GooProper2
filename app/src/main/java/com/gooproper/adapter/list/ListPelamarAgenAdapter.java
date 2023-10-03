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
import com.gooproper.ui.DetailAgenActivity;

import java.util.List;

public class ListPelamarAgenAdapter extends RecyclerView.Adapter<ListPelamarAgenAdapter.HolderData> {
    private List<AgenModel> models;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;

    private String truncateTextWithEllipsis(String text) {
        if (text.length() > MAX_TEXT_LENGTH) {
            return text.substring(0, MAX_TEXT_LENGTH) + " ...";
        } else {
            return text;
        }
    }

    public ListPelamarAgenAdapter(Context context, List<AgenModel> list){
        this.models = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ListPelamarAgenAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_pelamar_agen,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        AgenModel agenModel = models.get(position);
        holder.NamaPelamarAgen.setText(agenModel.getNama());
        holder.AlamatPelamarAgen.setText(agenModel.getAlamatDomisili());
        holder.PendidikanPelamarAgen.setText(agenModel.getPendidikan());
        holder.PengalamanPelamarAgen.setText(agenModel.getJabatan()+" ( "+agenModel.getMasaKerja()+"Tahun )");
        holder.TelpPelamarAgen.setText(agenModel.getNoTelp());
        Glide.with(context).load(models.get(position).getPhoto()).into(holder.GambarPelamarAgen);
        holder.agenModel = agenModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView NamaPelamarAgen, AlamatPelamarAgen, PendidikanPelamarAgen, PengalamanPelamarAgen, TelpPelamarAgen;
        ImageView GambarPelamarAgen;
        public AgenModel agenModel;
        public HolderData(View view){
            super(view);
            NamaPelamarAgen = view.findViewById(R.id.TVNamaPelamarAgen);
            AlamatPelamarAgen = view.findViewById(R.id.TVAlamatPelamarAgen);
            PendidikanPelamarAgen = view.findViewById(R.id.TVPendidikanTerakhirPelamarAgen);
            PengalamanPelamarAgen = view.findViewById(R.id.TVPengalamanPelamarAgen);
            TelpPelamarAgen = view.findViewById(R.id.TVTelpPelamarAgen);
            GambarPelamarAgen = view.findViewById(R.id.CIVPelamarAgen);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(context, DetailAgenActivity.class);
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
                    update.putExtra("Konfirmasi",agenModel.getKonfirmasi());
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
                    update.putExtra("Approve",agenModel.getApprove());
                    context.startActivity(update);
                }
            });
        }
    }

}
