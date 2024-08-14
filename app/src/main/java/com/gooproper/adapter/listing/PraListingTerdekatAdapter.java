package com.gooproper.adapter.listing;

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
import com.gooproper.model.PraListingTerdekatModel;
import com.gooproper.ui.detail.pralisting.DetailPralistingTerdekatActivity;

import java.util.List;

public class PraListingTerdekatAdapter extends RecyclerView.Adapter<PraListingTerdekatAdapter.HolderData> {
    private List<PraListingTerdekatModel> locationList;
    private Context context;

    public PraListingTerdekatAdapter(Context context, List<PraListingTerdekatModel> locationList) {
        this.locationList = locationList;
        this.context = context;
    }
    @NonNull
    @Override
    public PraListingTerdekatAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_pralisting_terdekat, parent, false);
        PraListingTerdekatAdapter.HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull PraListingTerdekatAdapter.HolderData holder, int position) {
        PraListingTerdekatModel praListingTerdekatModel = locationList.get(position);

        holder.titleTxt.setText(praListingTerdekatModel.getNamaListing());

        holder.addressTxt.setText(praListingTerdekatModel.getAlamat());

        Glide.with(context).load(locationList.get(position).getImg1()).into(holder.pic);

        holder.praListingTerdekatModel = praListingTerdekatModel;
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    class HolderData extends RecyclerView.ViewHolder {
        TextView titleTxt, addressTxt;
        ImageView pic;
        public PraListingTerdekatModel praListingTerdekatModel;

        public HolderData(View view) {
            super(view);
            titleTxt = view.findViewById(R.id.titleTxt);
            addressTxt = view.findViewById(R.id.addressTxt);;
            pic = view.findViewById(R.id.pic);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailPralistingTerdekatActivity.class);
                    update.putExtra("update", 1);
                    update.putExtra("IdPraListing", praListingTerdekatModel.getIdPraListing());
                    context.startActivity(update);
                }
            });
        }
    }
}
