package com.gooproper.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.gooproper.model.ListingModel;
import com.gooproper.ui.detail.DetailListingActivity;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.HolderData> {

    List<ListingModel> models;
    private List<ListingModel> originalList;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;
    private static final int MAX_TEXT_LENGTH_PRICE = 10;
    private static final int MAX_TEXT_LENGTH_PRICE_MILIAR = 23;
    private static final int MAX_TEXT_LENGTH_PRICE_JUTA = 19;
    private static final int MAX_TEXT_LENGTH_PRICE_RIBU = 15;

    public ListingAdapter(Context context, List<ListingModel> list){
        this.models = list;
        this.originalList = list;
        this.context = context;
    }

    private String truncateTextWithEllipsis(String text) {
        if (text.length() > MAX_TEXT_LENGTH) {
            return text.substring(0, MAX_TEXT_LENGTH) + " ...";
        } else {
            return text;
        }
    }

    private String truncateTextWithEllipsisPrice(String text) {
        if (text.length() > MAX_TEXT_LENGTH_PRICE) {
            if (text.length() < MAX_TEXT_LENGTH_PRICE_RIBU) {
                String truncatedText = removeTrailingZeroK(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " Rb";
                return truncatedText;
            } else if (text.length() < MAX_TEXT_LENGTH_PRICE_JUTA) {
                String truncatedText = removeTrailingZeroJ(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " Jt";
                return truncatedText;
            } else if (text.length() < MAX_TEXT_LENGTH_PRICE_MILIAR) {
                String truncatedText = removeTrailingZeroM(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " M";
                return truncatedText;
            } else {
                String truncatedText = removeTrailingZeroT(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " T";
                return truncatedText;
            }
        } else {
            return text;
        }
    }

    private String removeTrailingZeroT(String text) {
        if (text.endsWith(".000")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith(".00")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith(".0")) {
            return text.substring(0, text.length() - 2);
        } else if (text.endsWith(".000.")) {
            return text.substring(0, text.length() - 5);
        } else if (text.endsWith("000.")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith("00.")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith("0.")) {
            return text.substring(0, text.length() - 2);
        } else {
            return text;
        }
    }

    private String removeTrailingZeroM(String text) {
        if (text.endsWith(".000")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith(".00")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith(".0")) {
            return text.substring(0, text.length() - 2);
        } else if (text.endsWith(".000.")) {
            return text.substring(0, text.length() - 5);
        } else if (text.endsWith("000.")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith("00.")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith("0.")) {
            return text.substring(0, text.length() - 2);
        } else {
            return text;
        }
    }

    private String removeTrailingZeroJ(String text) {
        if (text.endsWith(".000")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith(".00")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith(".0")) {
            return text.substring(0, text.length() - 2);
        } else if (text.endsWith(".000.")) {
            return text.substring(0, text.length() - 5);
        } else if (text.endsWith("000.")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith("00.")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith(".")) {
            return text.substring(0, text.length() - 1);
        } else if (text.endsWith("00")) {
            return text.substring(0, text.length() - 2);
        } else {
            return text;
        }
    }

    private String removeTrailingZeroK(String text) {
        if (text.endsWith(".000")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith(".00")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith(".0")) {
            return text.substring(0, text.length() - 2);
        } else {
            return text;
        }
    }

    //searchView
    public void setFilteredlist (List<ListingModel> filteredlist){
        this.models = filteredlist;
        notifyDataSetChanged();
    }

    //reset filter
    public void resetFilter() {
        models.clear();
        models.addAll(originalList);
        notifyDataSetChanged();
    }

    //asc - desc
    private long parsePrice(String priceString) {
        return Long.parseLong(priceString.replaceAll(",", "").trim());
    }

    public void sortAscending() {
        Collections.sort(models, new Comparator<ListingModel>() {
            @Override
            public int compare(ListingModel item1, ListingModel item2) {
                long price1 = parsePrice(item1.getHarga());
                long price2 = parsePrice(item2.getHarga());
                return Long.compare(price1, price2);
            }
        });
        notifyDataSetChanged();
    }

    public void sortDescending() {
        Collections.sort(models, new Comparator<ListingModel>() {
            @Override
            public int compare(ListingModel item1, ListingModel item2) {
                long price1 = parsePrice(item1.getHarga());
                long price2 = parsePrice(item2.getHarga());
                return Long.compare(price2, price1);
            }
        });
        notifyDataSetChanged();
    }

    public void sortAscendingSewa() {
        Collections.sort(models, new Comparator<ListingModel>() {
            @Override
            public int compare(ListingModel item1, ListingModel item2) {
                long price1 = parsePrice(item1.getHargaSewa());
                long price2 = parsePrice(item2.getHargaSewa());
                return Long.compare(price1, price2);
            }
        });
        notifyDataSetChanged();
    }

    public void sortDescendingSewa() {
        Collections.sort(models, new Comparator<ListingModel>() {
            @Override
            public int compare(ListingModel item1, ListingModel item2) {
                long price1 = parsePrice(item1.getHargaSewa());
                long price2 = parsePrice(item2.getHargaSewa());
                return Long.compare(price2, price1);
            }
        });
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListingAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        ListingModel listingModel = models.get(position);
        if (listingModel.getPriority().equals("open")){
            holder.Lytpriority.setVisibility(View.INVISIBLE);
        } else {
            holder.Lytpriority.setVisibility(View.VISIBLE);
        }
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

        if (listingModel.getKondisi().equals("Jual")){
            String priceText = currency.formatRupiah(listingModel.getHarga());
            String truncatedprice = truncateTextWithEllipsisPrice(priceText);
            holder.priceTxt.setText(truncatedprice);
        } else if (listingModel.getKondisi().equals("Sewa")) {
            String priceSewaText = currency.formatRupiah(listingModel.getHargaSewa());
            String truncatedpriceSewa = truncateTextWithEllipsisPrice(priceSewaText);
            holder.priceTxt.setText(truncatedpriceSewa);
        } else {
            String priceText = currency.formatRupiah(listingModel.getHarga());
            String priceSewaText = currency.formatRupiah(listingModel.getHargaSewa());
            String truncatedprice = truncateTextWithEllipsisPrice(priceText);
            String truncatedpriceSewa = truncateTextWithEllipsisPrice(priceSewaText);
            holder.priceTxt.setText(truncatedprice + " / " + truncatedpriceSewa);
        }

        holder.listingModel = listingModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    
    class HolderData extends RecyclerView.ViewHolder{
        TextView titleTxt, addressTxt, priceTxt, bedTxt, bathTxt, levelTxt, garageTxt, bathArtTxt, bedArtTxt, carpotTxt, wideTxt, priorityTxt;
        ImageView pic;
        LinearLayout Lytpriority;
        String status;
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
            priorityTxt=view.findViewById(R.id.TVPriority);
            pic=view.findViewById(R.id.pic);
            Lytpriority=view.findViewById(R.id.LytBadge);

            status = Preferences.getKeyStatus(context);

            if (status.equals("1")){
                addressTxt.setVisibility(View.VISIBLE);
            } else if (status.equals("2")) {
                addressTxt.setVisibility(View.VISIBLE);
            } else if (status.equals("3")) {
                addressTxt.setVisibility(View.GONE);
            } else if (status.equals("4")) {
                addressTxt.setVisibility(View.GONE);
            } else {
                addressTxt.setVisibility(View.GONE);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailListingActivity.class);
                    update.putExtra("update",1);
                    update.putExtra("IdListing",listingModel.getIdListing());
                    update.putExtra("IdAgen",listingModel.getIdAgen());
                    update.putExtra("IdAgenCo",listingModel.getIdAgenCo());
                    update.putExtra("IdInput",listingModel.getIdInput());
                    update.putExtra("NamaListing",listingModel.getNamaListing());
                    update.putExtra("Alamat",listingModel.getAlamat());
                    update.putExtra("Latitude",listingModel.getLatitude());
                    update.putExtra("Longitude",listingModel.getLongitude());
                    update.putExtra("Location",listingModel.getLocation());
                    update.putExtra("Selfie",listingModel.getSelfie());
                    update.putExtra("Wide",listingModel.getWide());
                    update.putExtra("Land",listingModel.getLand());
                    update.putExtra("Dimensi", listingModel.getDimensi());
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
                    update.putExtra("AJB",listingModel.getAJB());
                    update.putExtra("PetokD",listingModel.getPetokD());
                    update.putExtra("Pjp",listingModel.getPjp());
                    update.putExtra("ImgSHM",listingModel.getImgSHM());
                    update.putExtra("ImgHGB",listingModel.getImgHGB());
                    update.putExtra("ImgHSHP",listingModel.getImgHSHP());
                    update.putExtra("ImgPPJB",listingModel.getImgPPJB());
                    update.putExtra("ImgStratatitle",listingModel.getImgStratatitle());
                    update.putExtra("ImgAJB",listingModel.getImgAJB());
                    update.putExtra("ImgPetokD",listingModel.getImgPetokD());
                    update.putExtra("ImgPjp",listingModel.getImgPjp());
                    update.putExtra("ImgPjp1",listingModel.getImgPjp1());
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
                    update.putExtra("HargaSewa",listingModel.getHargaSewa());
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
                    update.putExtra("IsRejected",listingModel.getIsRejected());
                    update.putExtra("View",listingModel.getView());
                    update.putExtra("Sold",listingModel.getSold());
                    update.putExtra("Rented",listingModel.getRented());
                    update.putExtra("Marketable",listingModel.getMarketable());
                    update.putExtra("StatusHarga",listingModel.getStatusHarga());
                    update.putExtra("Nama",listingModel.getNama());
                    update.putExtra("NoTelp",listingModel.getNoTelp());
                    update.putExtra("Instagram",listingModel.getInstagram());
                    update.putExtra("Fee",listingModel.getFee());
                    update.putExtra("NamaVendor",listingModel.getNamaVendor());
                    update.putExtra("NoTelpVendor",listingModel.getNoTelpVendor());
                    update.putExtra("IsSelfie",listingModel.getIsSelfie());
                    update.putExtra("IsLokasi",listingModel.getIsLokasi());
                    context.startActivity(update);
                }
            });
        }
    }
}
