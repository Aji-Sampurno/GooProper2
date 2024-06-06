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

import com.gooproper.R;
import com.gooproper.model.ListingModel;
import com.gooproper.ui.detail.listing.DetailListingActivity;
import com.gooproper.util.FormatCurrency;

import java.util.List;

public class ListPraListingAdapter extends RecyclerView.Adapter<ListPraListingAdapter.HolderData>{

    private List<ListingModel> listingModels;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;

    public ListPraListingAdapter(Context context, List<ListingModel> list){
        this.listingModels = list;
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
    public ListPraListingAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_pralisting,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        ListingModel listingModel = listingModels.get(position);

    }

    @Override
    public int getItemCount() {
        return listingModels.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView NamaPralisting, AlamatPralisting, HargaPralisting, BedPralisting, BathPralisting, LantaiPralisting, GarasiPralisting;
        ImageView GambarPralisting;
        public ListingModel listingModel;
        public HolderData(View view){
            super(view);
            NamaPralisting = view.findViewById(R.id.TVNamaPralistingList);
            AlamatPralisting = view.findViewById(R.id.TVAlamatPralistingList);
            HargaPralisting = view.findViewById(R.id.TVHargaPralistingList);
            BedPralisting = view.findViewById(R.id.TVBedPralistingList);
            BathPralisting = view.findViewById(R.id.TVBathPralistingList);
            LantaiPralisting = view.findViewById(R.id.TVLantaiPralistingList);
            GarasiPralisting = view.findViewById(R.id.TVGarasiPralistingList);
            GambarPralisting = view.findViewById(R.id.IVPralistingList);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(context, DetailListingActivity.class);
                    update.putExtra("update",1);
                    update.putExtra("IdPraListing",listingModel.getIdPraListing());
                    update.putExtra("IdListing",listingModel.getIdListing());
                    update.putExtra("IdAgen",listingModel.getIdAgen());
                    update.putExtra("IdInput",listingModel.getIdInput());
                    update.putExtra("NamaListing",listingModel.getNamaListing());
                    update.putExtra("Alamat",listingModel.getAlamat());
                    update.putExtra("Location",listingModel.getLocation());
                    update.putExtra("Wide",listingModel.getWide());
                    update.putExtra("Level",listingModel.getLevel());
                    update.putExtra("Bed",listingModel.getBed());
                    update.putExtra("BedArt",listingModel.getBedArt());
                    update.putExtra("Bath",listingModel.getBath());
                    update.putExtra("BathArt",listingModel.getBathArt());
                    update.putExtra("Garage",listingModel.getGarage());
                    update.putExtra("Carpot",listingModel.getCarpot());
                    update.putExtra("NoCertificate",listingModel.getNoCertificate());
                    update.putExtra("Pbb",listingModel.getPbb());
                    update.putExtra("JenisProperti",listingModel.getJenisProperti());
                    update.putExtra("JenisCertificate",listingModel.getJenisCertificate());
                    update.putExtra("SumberAir",listingModel.getSumberAir());
                    update.putExtra("Kondisi",listingModel.getKondisi());
                    update.putExtra("Deskripsi",listingModel.getDeskripsi());
                    update.putExtra("Prabot",listingModel.getPrabot());
                    update.putExtra("KetPrabot",listingModel.getKetPrabot());
                    update.putExtra("Priority",listingModel.getPriority());
                    update.putExtra("Ttd",listingModel.getTtd());
                    update.putExtra("Banner",listingModel.getBanner());
                    update.putExtra("Harga",listingModel.getHarga());
                    update.putExtra("TglInput",listingModel.getTglInput());
                    update.putExtra("Img1",listingModel.getImg1());
                    update.putExtra("Img2",listingModel.getImg2());
                    update.putExtra("Img3",listingModel.getImg3());
                    update.putExtra("Img4",listingModel.getImg4());
                    update.putExtra("Img5",listingModel.getImg5());
                    update.putExtra("Img6",listingModel.getImg6());
                    update.putExtra("Img7",listingModel.getImg7());
                    update.putExtra("Img8",listingModel.getImg8());
                    update.putExtra("Video",listingModel.getVideo());
                    update.putExtra("LinkFacebook",listingModel.getLinkFacebook());
                    update.putExtra("LinkTiktok",listingModel.getLinkTiktok());
                    update.putExtra("LinkInstagram",listingModel.getLinkInstagram());
                    update.putExtra("LinkYoutube",listingModel.getLinkYoutube());
                    update.putExtra("IsAdmin",listingModel.getIsAdmin());
                    update.putExtra("IsManager",listingModel.getIsManager());
                    update.putExtra("View",listingModel.getView());
                    update.putExtra("Sold",listingModel.getSold());
                    context.startActivity(update);
                }
            });
        }
    }
}
