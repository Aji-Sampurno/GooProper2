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
import com.gooproper.model.PrimaryModel;
import com.gooproper.ui.detail.DetailListingActivity;
import com.gooproper.ui.detail.DetailPrimaryActivity;
import com.gooproper.util.FormatCurrency;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PrimaryAdapter extends RecyclerView.Adapter<PrimaryAdapter.HolderData> {

    List<PrimaryModel> models;
    private List<PrimaryModel> originalList;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;
    private static final int MAX_TEXT_LENGTH_PRICE = 10;
    private static final int MAX_TEXT_LENGTH_PRICE_JUTA = 19;

    public PrimaryAdapter(Context context, List<PrimaryModel> list){
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
            if (text.length() < MAX_TEXT_LENGTH_PRICE_JUTA) {
                //return text.substring(0, MAX_TEXT_LENGTH_PRICE) + " Jt";
                String truncatedText = removeTrailingZeroJ(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " Jt";
                return truncatedText;
            } else {
                //return text.substring(0, MAX_TEXT_LENGTH_PRICE) + " M";
                String truncatedText = removeTrailingZeroM(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " M";
                return truncatedText;
            }
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
        } else {
            return text;
        }
    }

    //searchView
    public void setFilteredlist (List<PrimaryModel> filteredlist){
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
        Collections.sort(models, new Comparator<PrimaryModel>() {
            @Override
            public int compare(PrimaryModel item1, PrimaryModel item2) {
                long price1 = parsePrice(item1.getHargaListingPrimary());
                long price2 = parsePrice(item2.getHargaListingPrimary());
                return Long.compare(price1, price2);
            }
        });
        notifyDataSetChanged();
    }

    public void sortDescending() {
        Collections.sort(models, new Comparator<PrimaryModel>() {
            @Override
            public int compare(PrimaryModel item1, PrimaryModel item2) {
                long price1 = parsePrice(item1.getHargaListingPrimary());
                long price2 = parsePrice(item2.getHargaListingPrimary());
                return Long.compare(price2, price1);
            }
        });
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PrimaryAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.primary,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull PrimaryAdapter.HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        PrimaryModel primaryModel = models.get(position);
        String priceText = currency.formatRupiah(primaryModel.getHargaListingPrimary());
        String truncatedprice = truncateTextWithEllipsisPrice(priceText);
        holder.TVAlamat.setText(primaryModel.getAlamatListingPrimary());
        holder.TVJudul.setText(primaryModel.getJudulListingPrimary());
        holder.TVHarga.setText(truncatedprice);
        Glide.with(context).load(models.get(position).getImg1()).into(holder.IVPrimary);
        holder.primaryModel = primaryModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView TVJudul, TVAlamat, TVHarga;
        ImageView IVPrimary;
        public PrimaryModel primaryModel;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            TVAlamat = itemView.findViewById(R.id.TVAlamatPrimary);
            TVJudul = itemView.findViewById(R.id.TVJudulPrimary);
            TVHarga = itemView.findViewById(R.id.TVHargaPrimary);
            IVPrimary = itemView.findViewById(R.id.IVPrimary);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(context, DetailPrimaryActivity.class);
                    update.putExtra("update",1);
                    update.putExtra("IdListingPrimary",primaryModel.getIdListingPrimary());
                    update.putExtra("JudulListingPrimary",primaryModel.getJudulListingPrimary());
                    update.putExtra("HargaListingPrimary",primaryModel.getHargaListingPrimary());
                    update.putExtra("DeskripsiListingPrimary",primaryModel.getDeskripsiListingPrimary());
                    update.putExtra("AlamatListingPrimary",primaryModel.getAlamatListingPrimary());
                    update.putExtra("LatitudeListingPrimary",primaryModel.getLatitudeListingPrimary());
                    update.putExtra("LongitudeListingPrimary",primaryModel.getLongitudeListingPrimary());
                    update.putExtra("LocationListingPrimary",primaryModel.getLocationListingPrimary());
                    update.putExtra("KontakPerson1",primaryModel.getKontakPerson1());
                    update.putExtra("KontakPerson2",primaryModel.getKontakPerson2());
                    update.putExtra("Img1",primaryModel.getImg1());
                    update.putExtra("Img2",primaryModel.getImg2());
                    update.putExtra("Img3",primaryModel.getImg3());
                    update.putExtra("Img4",primaryModel.getImg4());
                    update.putExtra("Img5",primaryModel.getImg5());
                    update.putExtra("Img6",primaryModel.getImg6());
                    update.putExtra("Img7",primaryModel.getImg7());
                    update.putExtra("Img8",primaryModel.getImg8());
                    update.putExtra("Img9",primaryModel.getImg9());
                    update.putExtra("Img10",primaryModel.getImg10());
                    context.startActivity(update);
                }
            });
        }
    }
}
