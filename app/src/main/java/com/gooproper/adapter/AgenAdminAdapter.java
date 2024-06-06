package com.gooproper.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gooproper.R;
import com.gooproper.model.AgenModel;
import com.gooproper.ui.detail.agen.DetailAgenActivity;
import com.gooproper.util.FormatCurrency;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AgenAdminAdapter extends RecyclerView.Adapter<AgenAdminAdapter.HolderData> {
    private List<AgenModel> models;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;

    public AgenAdminAdapter(Context context, List<AgenModel> list) {
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

    public void setFilteredlist(List<AgenModel> filteredlist) {
        this.models = filteredlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AgenAdminAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_agen_admin, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull AgenAdminAdapter.HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        AgenModel agenModel = models.get(position);
        holder.telp.setText(agenModel.getNoTelp());
        Glide.with(context).load(models.get(position).getPhoto()).into(holder.cvagen);

        String namaagen = agenModel.getNama();
        String truncatedtitle = truncateTextWithEllipsis(namaagen);
        holder.nama.setText(agenModel.getNama());
        holder.nokaryawan.setText(agenModel.getNoKaryawan());

        holder.agenModel = agenModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class HolderData extends RecyclerView.ViewHolder {
        TextView nama, nokaryawan, telp;
        CircleImageView cvagen;
        public AgenModel agenModel;

        public HolderData(View view) {
            super(view);
            nama = view.findViewById(R.id.TVNamaAgen);
            nokaryawan = view.findViewById(R.id.TVNoKaryawanAgen);
            telp = view.findViewById(R.id.TVTelpAgen);
            cvagen = view.findViewById(R.id.CIVAgen);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailAgenActivity.class);
                    update.putExtra("update", 1);
                    update.putExtra("IdAgen", agenModel.getIdAgen());
                    update.putExtra("Username", agenModel.getUsername());
                    update.putExtra("Password", agenModel.getPassword());
                    update.putExtra("Nama", agenModel.getNama());
                    update.putExtra("NoTelp", agenModel.getNoTelp());
                    update.putExtra("Email", agenModel.getEmail());
                    update.putExtra("TglLahir", agenModel.getTglLahir());
                    update.putExtra("KotaKelahiran", agenModel.getKotaKelahiran());
                    update.putExtra("Pendidikan", agenModel.getPendidikan());
                    update.putExtra("NamaSekolah", agenModel.getNamaSekolah());
                    update.putExtra("MasaKerja", agenModel.getMasaKerja());
                    update.putExtra("Jabatan", agenModel.getJabatan());
                    update.putExtra("Konfirmasi", agenModel.getKonfirmasi());
                    update.putExtra("Status", agenModel.getStatus());
                    update.putExtra("AlamatDomisili", agenModel.getAlamatDomisili());
                    update.putExtra("Facebook", agenModel.getFacebook());
                    update.putExtra("Instagram ", agenModel.getInstagram());
                    update.putExtra("NoKtp", agenModel.getNoKtp());
                    update.putExtra("ImgKtp", agenModel.getImgKtp());
                    update.putExtra("ImgTtd", agenModel.getImgTtd());
                    update.putExtra("Npwp ", agenModel.getNpwp());
                    update.putExtra("Photo", agenModel.getPhoto());
                    update.putExtra("Poin", agenModel.getPoin());
                    update.putExtra("IsAkses", agenModel.getIsAkses());
                    update.putExtra("Approve", agenModel.getApprove());
                    context.startActivity(update);
                }
            });
        }
    }
}
