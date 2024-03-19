package com.gooproper.adapter.followup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gooproper.R;
import com.gooproper.model.FlowUpInfoModel;
import com.gooproper.ui.detail.followup.DetailFollowUpActivity;
import com.gooproper.ui.detail.followup.DetailFollowUpInfoActivity;

import java.util.List;

public class FlowUpInfoAdapter extends RecyclerView.Adapter<FlowUpInfoAdapter.HolderData> {

    private List<FlowUpInfoModel> models;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;

    public FlowUpInfoAdapter(Context context, List<FlowUpInfoModel> list){
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
    public FlowUpInfoAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_flowup_info,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        FlowUpInfoModel flowUpModel = models.get(position);

        String namaListing = flowUpModel.getStatusProperty() + " " + flowUpModel.getJenisProperty();
        String truncatedtitle = truncateTextWithEllipsis(namaListing);
        holder.NamaListing.setText(truncatedtitle);

        String alamatListing = flowUpModel.getLokasi();
        String truncatedalamat = truncateTextWithEllipsis(alamatListing);
        holder.AlamatListing.setText(truncatedalamat);

        holder.Keterangan.setText(flowUpModel.getKeteranganFollowUp());

        holder.flowUpModel = flowUpModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView NamaListing, AlamatListing, Keterangan;
        public FlowUpInfoModel flowUpModel;
        public HolderData(View view){
            super(view);
            NamaListing = view.findViewById(R.id.TVNamaListingFlowUp);
            AlamatListing = view.findViewById(R.id.TVAlamatListingFlowUp);
            Keterangan = view.findViewById(R.id.TVKeteranganFlowUpInfo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailFollowUpInfoActivity.class);
                    update.putExtra("update",1);
                    update.putExtra("IdFlowup",flowUpModel.getIdFlowup());
                    update.putExtra("IdInfo",flowUpModel.getIdInfo());
                    update.putExtra("IdAgen",flowUpModel.getIdAgen());
                    update.putExtra("Tanggal",flowUpModel.getTanggal());
                    update.putExtra("Jam",flowUpModel.getJam());
                    update.putExtra("KeteranganFollowUp",flowUpModel.getKeteranganFollowUp());
                    update.putExtra("Keterangan",flowUpModel.getKeterangan());
                    update.putExtra("JenisProperty",flowUpModel.getJenisProperty());
                    update.putExtra("StatusProperty",flowUpModel.getStatusProperty());
                    update.putExtra("Narahubung",flowUpModel.getNarahubung());
                    update.putExtra("ImgSelfie",flowUpModel.getImgSelfie());
                    update.putExtra("ImgProperty",flowUpModel.getImgProperty());
                    update.putExtra("Lokasi",flowUpModel.getLokasi());
                    update.putExtra("Alamat",flowUpModel.getAlamat());
                    update.putExtra("NoTelp",flowUpModel.getNoTelp());
                    update.putExtra("Latitude",flowUpModel.getLatitude());
                    update.putExtra("Longitude",flowUpModel.getLongitude());
                    update.putExtra("TglInput",flowUpModel.getTglInput());
                    update.putExtra("JamInput",flowUpModel.getJamInput());
                    update.putExtra("IsListing",flowUpModel.getIsListing());
                    update.putExtra("IsAgen",flowUpModel.getIsAgen());
                    update.putExtra("LBangunan",flowUpModel.getLBangunan());
                    update.putExtra("LTanah",flowUpModel.getLTanah());
                    update.putExtra("Harga",flowUpModel.getHarga());
                    update.putExtra("HargaSewa",flowUpModel.getHargaSewa());
                    update.putExtra("IsSpek",flowUpModel.getIsSpek());
                    context.startActivity(update);
                }
            });
        }
    }
}
