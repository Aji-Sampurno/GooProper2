package com.gooproper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.transition.Hold;
import com.gooproper.R;
import com.gooproper.model.ListingModel;
import com.gooproper.model.PrimaryModel;
import com.gooproper.model.TipeModel;
import com.gooproper.util.FormatCurrency;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TipeAdapter extends RecyclerView.Adapter<TipeAdapter.HolderData> {
    List<TipeModel> models;
    private List<TipeModel> originalList;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;
    private static final int MAX_TEXT_LENGTH_PRICE = 10;
    private static final int MAX_TEXT_LENGTH_PRICE_JUTA = 19;
    public TipeAdapter(Context context, List<TipeModel> list){
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
    public void setFilteredlist (List<TipeModel> filteredlist){
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

    @NonNull
    @Override
    public TipeAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.tipe,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull TipeAdapter.HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        TipeModel tipeModel = models.get(position);
        String priceText = currency.formatRupiah(tipeModel.getHargaTipe());
        holder.TVJudul.setText(tipeModel.getNamaTipe());
        holder.TVHarga.setText(priceText);
        holder.TVDeskripsi.setText(tipeModel.getDeskripsiTipe());
        Glide.with(context).load(models.get(position).getGambarTipe()).into(holder.IVGambar);
        holder.tipeModel = tipeModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView TVJudul, TVHarga, TVDeskripsi;
        ImageView IVGambar;
        public TipeModel tipeModel;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            TVJudul = itemView.findViewById(R.id.TVNamaTipe);
            TVHarga = itemView.findViewById(R.id.TVHargaTipe);
            TVDeskripsi = itemView.findViewById(R.id.TVDeskripsiTipe);
            IVGambar = itemView.findViewById(R.id.IVGambarTipe);
        }
    }
}
