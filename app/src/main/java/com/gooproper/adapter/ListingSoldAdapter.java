package com.gooproper.adapter;

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
import com.gooproper.model.ListingModel;
import com.gooproper.ui.DetailListingActivity;
import com.gooproper.util.FormatCurrency;

import java.util.List;

public class ListingSoldAdapter extends RecyclerView.Adapter<ListingSoldAdapter.HolderData>{

    private List<ListingModel> models;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;

    public ListingSoldAdapter(Context context, List<ListingModel> list){
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
    public ListingSoldAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_sold,parent,false);
        ListingSoldAdapter.HolderData holderData = new ListingSoldAdapter.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull ListingSoldAdapter.HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        ListingModel listingModel = models.get(position);
        holder.bedTxt.setText(listingModel.getBed());
        holder.bathTxt.setText(listingModel.getBath());
        holder.levelTxt.setText(listingModel.getLevel());
        holder.garageTxt.setText(listingModel.getGarage());
        Glide.with(context).load(models.get(position).getImg1()).into(holder.pic);

        String titleText = listingModel.getNamaListing();
        String truncatedtitle = truncateTextWithEllipsis(titleText);
        holder.titleTxt.setText(truncatedtitle);

        String addresText = listingModel.getAlamat();
        String truncatedaddres = truncateTextWithEllipsis(addresText);
        holder.addressTxt.setText(truncatedaddres);

        String priceText = currency.formatRupiah(listingModel.getHarga());
        String truncatedprice = truncateTextWithEllipsis(priceText);
        holder.priceTxt.setText(truncatedprice);

        holder.listingModel = listingModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView titleTxt, addressTxt, priceTxt, bedTxt, bathTxt, levelTxt, garageTxt, bathArtTxt, bedArtTxt, carpotTxt, wideTxt;
        ImageView pic;
        public ListingModel listingModel;

        public HolderData(View view){
            super(view);
            titleTxt=view.findViewById(R.id.titleTxt);
            addressTxt=view.findViewById(R.id.addressTxt);
            bathTxt=view.findViewById(R.id.bathTxt);
            bedTxt=view.findViewById(R.id.bedTxt);
            priceTxt=view.findViewById(R.id.priceTxt);
            levelTxt=view.findViewById(R.id.levelTxt);
            garageTxt=view.findViewById(R.id.garageTxt);
            pic=view.findViewById(R.id.pic);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailListingActivity.class);
                    update.putExtra("update",1);
                    update.putExtra("IdListing",listingModel.getIdListing());
                    update.putExtra("IdAgen",listingModel.getIdAgen());
                    update.putExtra("IdInput",listingModel.getIdInput());
                    update.putExtra("NamaListing",listingModel.getNamaListing());
                    update.putExtra("Alamat",listingModel.getAlamat());
                    update.putExtra("Latitude",listingModel.getLatitude());
                    update.putExtra("Longitude",listingModel.getLongitude());
                    update.putExtra("Location",listingModel.getLocation());
                    update.putExtra("Wide",listingModel.getWide());
                    update.putExtra("Land",listingModel.getLand());
                    update.putExtra("Listrik",listingModel.getListrik());
                    update.putExtra("Level",listingModel.getLevel());
                    update.putExtra("Bed",listingModel.getBed());
                    update.putExtra("BedArt",listingModel.getBedArt());
                    update.putExtra("Bath",listingModel.getBath());
                    update.putExtra("BathArt",listingModel.getBathArt());
                    update.putExtra("Garage",listingModel.getGarage());
                    update.putExtra("Carpot",listingModel.getCarpot());
                    update.putExtra("Hadap",listingModel.getHadap());
                    update.putExtra("SHM",listingModel.getSHM());
                    update.putExtra("HGB",listingModel.getHGB());
                    update.putExtra("HSHP",listingModel.getHSHP());
                    update.putExtra("PPJB",listingModel.getPPJB());
                    update.putExtra("Stratatitle",listingModel.getStratatitle());
                    update.putExtra("ImgSHM",listingModel.getImgSHM());
                    update.putExtra("ImgHGB",listingModel.getImgHGB());
                    update.putExtra("ImgHSHP",listingModel.getImgHSHP());
                    update.putExtra("ImgPPJB",listingModel.getImgPPJB());
                    update.putExtra("ImgStratatitle",listingModel.getImgStratatitle());
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
                    update.putExtra("Size",listingModel.getSize());
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
                    update.putExtra("Marketable",listingModel.getMarketable());
                    update.putExtra("StatusHarga",listingModel.getStatusHarga());
                    update.putExtra("Nama",listingModel.getNama());
                    update.putExtra("NoTelp",listingModel.getNoTelp());
                    update.putExtra("Instagram",listingModel.getInstagram());
                    context.startActivity(update);
                }
            });
        }
    }
}