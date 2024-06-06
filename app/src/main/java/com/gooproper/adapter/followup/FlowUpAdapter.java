package com.gooproper.adapter.followup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gooproper.R;
import com.gooproper.model.FlowUpModel;
import com.gooproper.ui.detail.followup.DetailFollowUpActivity;

import java.util.List;

public class FlowUpAdapter extends RecyclerView.Adapter<FlowUpAdapter.HolderData> {

    private List<FlowUpModel> models;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;

    public FlowUpAdapter(Context context, List<FlowUpModel> list){
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
    public FlowUpAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_flowup,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        FlowUpModel flowUpModel = models.get(position);

        if (flowUpModel.getChat().equals("1")){
            holder.LytChat.setVisibility(View.VISIBLE);
        } else {
            holder.LytChat.setVisibility(View.GONE);
        }
        if (flowUpModel.getSurvei().equals("1")){
            holder.LytSurvei.setVisibility(View.VISIBLE);
        } else {
            holder.LytSurvei.setVisibility(View.GONE);
        }
        if (flowUpModel.getTawar().equals("1")){
            holder.LytTawar.setVisibility(View.VISIBLE);
        } else {
            holder.LytTawar.setVisibility(View.GONE);
        }
        if (flowUpModel.getLokasi().equals("1")){
            holder.LytLokasi.setVisibility(View.VISIBLE);
        } else {
            holder.LytLokasi.setVisibility(View.GONE);
        }
        if (flowUpModel.getDeal().equals("1")){
            holder.LytDeal.setVisibility(View.VISIBLE);
        } else {
            holder.LytDeal.setVisibility(View.GONE);
        }

        String namaListing = flowUpModel.getNamaListing();
        String truncatedtitle = truncateTextWithEllipsis(namaListing);
        holder.NamaListing.setText(truncatedtitle);

        String namaBuyer = flowUpModel.getNamaBuyer();
        String truncatedbuyer = truncateTextWithEllipsis(namaBuyer);
        holder.NamaBuyer.setText(truncatedbuyer);

        String alamatListing = flowUpModel.getAlamat();
        String truncatedalamat = truncateTextWithEllipsis(alamatListing);
        holder.AlamatListing.setText(truncatedalamat);

        holder.flowUpModel = flowUpModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView NamaListing, AlamatListing, NamaBuyer;
        LinearLayout LytChat, LytSurvei, LytTawar, LytLokasi, LytDeal;
        public FlowUpModel flowUpModel;
        public HolderData(View view){
            super(view);
            NamaListing = view.findViewById(R.id.TVNamaListingFlowUp);
            AlamatListing = view.findViewById(R.id.TVAlamatListingFlowUp);
            NamaBuyer = view.findViewById(R.id.TVNamaBuyerFlowUp);
            LytChat = view.findViewById(R.id.LytChatFollowUp);
            LytSurvei = view.findViewById(R.id.LytSurveiFollowUp);
            LytTawar = view.findViewById(R.id.LytTawarFollowUp);
            LytLokasi = view.findViewById(R.id.LytLokasiFollowUp);
            LytDeal = view.findViewById(R.id.LytDealFollowUp);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailFollowUpActivity.class);
                    update.putExtra("update",1);
                    update.putExtra("IdFlowup",flowUpModel.getIdFlowup());
                    update.putExtra("IdAgen",flowUpModel.getIdAgen());
                    update.putExtra("IdInput",flowUpModel.getIdInput());
                    update.putExtra("IdListing",flowUpModel.getIdListing());
                    update.putExtra("NamaBuyer",flowUpModel.getNamaBuyer());
                    update.putExtra("TelpBuyer",flowUpModel.getTelpBuyer());
                    update.putExtra("SumberBuyer",flowUpModel.getSumberBuyer());
                    update.putExtra("Tanggal",flowUpModel.getTanggal());
                    update.putExtra("Jam",flowUpModel.getJam());
                    update.putExtra("Keterangan",flowUpModel.getKeterangan());
                    update.putExtra("Chat",flowUpModel.getChat());
                    update.putExtra("Survei",flowUpModel.getSurvei());
                    update.putExtra("Tawar",flowUpModel.getTawar());
                    update.putExtra("Lokasi",flowUpModel.getLokasi());
                    update.putExtra("Deal",flowUpModel.getDeal());
                    update.putExtra("Selfie",flowUpModel.getSelfie());
                    update.putExtra("NamaListing",flowUpModel.getNamaListing());
                    update.putExtra("Alamat",flowUpModel.getAlamat());
                    update.putExtra("Latitude",flowUpModel.getLatitude());
                    update.putExtra("Longitude",flowUpModel.getLongitude());
                    update.putExtra("Harga",flowUpModel.getHarga());
                    update.putExtra("NamaAgen",flowUpModel.getNamaAgen());
                    update.putExtra("TelpAgen",flowUpModel.getTelpAgen());
                    update.putExtra("NamaInput",flowUpModel.getNamaInput());
                    update.putExtra("TelpInput",flowUpModel.getTelpInput());
                    update.putExtra("Img1",flowUpModel.getImg1());
                    update.putExtra("Img2",flowUpModel.getImg2());
                    update.putExtra("Img3",flowUpModel.getImg3());
                    update.putExtra("Img4",flowUpModel.getImg4());
                    update.putExtra("Img5",flowUpModel.getImg5());
                    update.putExtra("Img6",flowUpModel.getImg6());
                    update.putExtra("Img7",flowUpModel.getImg7());
                    update.putExtra("Img8",flowUpModel.getImg8());
                    update.putExtra("Img9",flowUpModel.getImg9());
                    update.putExtra("Img10",flowUpModel.getImg10());
                    update.putExtra("Img11",flowUpModel.getImg11());
                    update.putExtra("Img12",flowUpModel.getImg12());
                    context.startActivity(update);
                }
            });
        }
    }
}
