package com.example.driverside;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class viewTripsAdapter extends RecyclerView.Adapter<viewTripsAdapter.ViewHolder> {
    ArrayList<viewTripsData> list;
    Context hist;
    public viewTripsAdapter(ArrayList<viewTripsData> list, viewTripsPage activity) {
        this.list=list;
        this.hist=activity;
    }

    private viewTripsAdapter.onItemClickListener mListener;
    public interface onItemClickListener {
        void onCompletedClick(int position);
        void onCancelClick(int position);
    }

    public void setOnItemListener(viewTripsAdapter.onItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View viewHist=layoutInflater.inflate(R.layout.driver_trip_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(viewHist,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewTripsAdapter.ViewHolder holder, int position) {
        viewTripsData myHistory = list.get(position);
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
        Button complete,decline;

        public ViewHolder(@NonNull View itemView,viewTripsAdapter.onItemClickListener listener) {
            super(itemView);
            status=itemView.findViewById(R.id.statusTrip);
            trip_from= itemView.findViewById(R.id.Pickup);
            trip_to = itemView.findViewById(R.id.DropOff);
            trip_price = itemView.findViewById(R.id.priceHist);
            car_num = itemView.findViewById(R.id.carNumberHist);
            trip_time = itemView.findViewById(R.id.timeHist);
            complete=itemView.findViewById(R.id.btnComplete);
            decline=itemView.findViewById(R.id.btnCancel);
            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onCompletedClick(position);
                        complete.setVisibility(View.GONE);
                        decline.setVisibility(View.GONE);
                        notifyDataSetChanged();

                    }
                }
            });
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onCancelClick(position);
                        complete.setVisibility(View.GONE);
                        decline.setVisibility(View.GONE);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

}
