package com.example.driverside.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driverside.Helpers.historyTripsHelper;
import com.example.driverside.R;
import com.example.driverside.historyTrips;

import java.util.ArrayList;

public class historyTripsAdapter extends RecyclerView.Adapter<historyTripsAdapter.ViewHolder> {
    ArrayList<historyTripsHelper> list;
    Context hist;
    public historyTripsAdapter(ArrayList<historyTripsHelper> list, historyTrips activity) {
        this.list=list;
        this.hist=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View viewHist=layoutInflater.inflate(R.layout.history_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(viewHist);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull historyTripsAdapter.ViewHolder holder, int position) {
        historyTripsHelper myHistory = list.get(position);
        holder.status.setText(myHistory.getDriverStatus());
        holder.trip_to.setText(myHistory.getToTrip());
        holder.trip_from.setText(myHistory.getFromTrip());
        holder.trip_price.setText(myHistory.getPriceTrip());
        holder.car_num.setText(myHistory.getNumTrip());
        holder.trip_time.setText(myHistory.getTimeTrip());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
