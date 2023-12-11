package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    ArrayList<historyData> listH;
    Context hist;
    public HistoryAdapter(ArrayList<historyData> listH, historyPage activity) {
        this.listH=listH;
        this.hist=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View viewHist=layoutInflater.inflate(R.layout.track_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(viewHist);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        historyData myHistory = listH.get(position);
        holder.status.setText(myHistory.getTripState());
        holder.trip_to.setText(myHistory.getToTrip());
        holder.trip_from.setText(myHistory.getFromTrip());
        holder.trip_price.setText(myHistory.getPriceTrip());
        holder.car_num.setText(myHistory.getNumTrip());
        holder.trip_time.setText(myHistory.getTimeTrip());
    }

    @Override
    public int getItemCount() {
        return listH.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView trip_to,trip_from,trip_price,car_num,trip_time,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.statusTrip);
            trip_from= itemView.findViewById(R.id.Pickup);
            trip_to = itemView.findViewById(R.id.DropOff);
            trip_price = itemView.findViewById(R.id.priceHist);
            car_num = itemView.findViewById(R.id.carNumberHist);
            trip_time = itemView.findViewById(R.id.timeHist);
        }
    }
}
