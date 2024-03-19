package com.gooproper.adapter.followup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gooproper.R;
import com.gooproper.model.UpdateFlowUpModel;

import java.util.List;

public class UpdateFlowUpPrimaryAdapter extends RecyclerView.Adapter<UpdateFlowUpPrimaryAdapter.HolderData> {

    private List<UpdateFlowUpModel> models;
    private Context context;

    public UpdateFlowUpPrimaryAdapter(Context context, List<UpdateFlowUpModel> list){
        this.models = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UpdateFlowUpPrimaryAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_update_flowup,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        UpdateFlowUpModel flowUpModel = models.get(position);


        holder.TglUpdate.setText(flowUpModel.getTanggal());
        holder.JamUpdate.setText(flowUpModel.getJam());

        if (flowUpModel.getDeal().equals("1")){
            holder.StatusUpdate.setText("Deal");
        } else if (flowUpModel.getLokasi().equals("1")) {
            holder.StatusUpdate.setText("Cari Lokasi Lain");
        } else if (flowUpModel.getTawar().equals("1")) {
            holder.StatusUpdate.setText("Negosiasi");
        } else if (flowUpModel.getSurvei().equals("1")) {
            holder.StatusUpdate.setText("Survei");
        } else if (flowUpModel.getChat().equals("1")){
            holder.StatusUpdate.setText("Chat");
        }

        holder.flowUpModel = flowUpModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView TglUpdate, JamUpdate, StatusUpdate;
        public UpdateFlowUpModel flowUpModel;
        public HolderData(View view){
            super(view);
            TglUpdate = view.findViewById(R.id.TVTglUpdateFollowUp);
            JamUpdate = view.findViewById(R.id.TVJamUpdateFollowUp);
            StatusUpdate = view.findViewById(R.id.TVStatusUpdateFollowUp);
        }
    }
}
