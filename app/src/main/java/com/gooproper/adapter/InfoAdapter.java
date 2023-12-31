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
import com.gooproper.model.InfoModel;
import com.gooproper.model.ListingModel;
import com.gooproper.ui.detail.DetailInfoActivity;
import com.gooproper.ui.detail.DetailListingActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.HolderData> {

    List<InfoModel> models;
    private List<InfoModel> originalList;
    private Context context;
    private static final int MAX_TEXT_LENGTH = 20;
    private static final int MAX_TEXT_LENGTH_PRICE = 10;
    private static final int MAX_TEXT_LENGTH_PRICE_JUTA = 19;
    private static final int MAX_TEXT_LENGTH_PRICE_RIBU = 15;

    public InfoAdapter(Context context, List<InfoModel> list){
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
                //return text.substring(0, MAX_TEXT_LENGTH_PRICE) + " Rb";
                String truncatedText = removeTrailingZeroK(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " Rb";
                return truncatedText;
            } else if (text.length() < MAX_TEXT_LENGTH_PRICE_JUTA) {
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
    public void setFilteredlist (List<InfoModel> filteredlist){
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
    public InfoAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.info,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        InfoModel infoModel = models.get(position);
        holder.JudulInfo.setText(infoModel.getStatusProperty() + " " + infoModel.getJenisProperty());
        holder.LokasiInfo.setText(infoModel.getLokasi());
        Glide.with(context).load(models.get(position).getImgProperty()).into(holder.pic);
        holder.infoModel = infoModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView JudulInfo, LokasiInfo;
        ImageView pic;
        public InfoModel infoModel;
        public HolderData(View view){
            super(view);
            JudulInfo = view.findViewById(R.id.TVJudulInfo);
            LokasiInfo = view.findViewById(R.id.TVAlamatInfo);
            pic = view.findViewById(R.id.IVInfo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailInfoActivity.class);
                    update.putExtra("update",1);
                    update.putExtra("IdInfo",infoModel.getIdInfo());
                    update.putExtra("IdAgen",infoModel.getIdAgen());
                    update.putExtra("JenisProperty",infoModel.getJenisProperty());
                    update.putExtra("StatusProperty",infoModel.getStatusProperty());
                    update.putExtra("Narahubung",infoModel.getNarahubung());
                    update.putExtra("ImgSelfie",infoModel.getImgSelfie());
                    update.putExtra("ImgProperty",infoModel.getImgProperty());
                    update.putExtra("Lokasi",infoModel.getLokasi());
                    update.putExtra("Alamat",infoModel.getAlamat());
                    update.putExtra("NoTelp",infoModel.getNoTelp());
                    update.putExtra("Latitude",infoModel.getLatitude());
                    update.putExtra("Longitude",infoModel.getLongitude());
                    update.putExtra("TglInput",infoModel.getTglInput());
                    update.putExtra("JamInput",infoModel.getJamInput());
                    update.putExtra("IsListing",infoModel.getIsListing());
                    update.putExtra("LBangunan",infoModel.getLBangunan());
                    update.putExtra("LTanah",infoModel.getLTanah());
                    update.putExtra("Harga",infoModel.getHarga());
                    update.putExtra("HargaSewa",infoModel.getHargaSewa());
                    update.putExtra("Keterangan",infoModel.getKeterangan());
                    update.putExtra("IsSpek",infoModel.getIsSpek());
                    context.startActivity(update);
                }
            });
        }
    }
}
