package com.example.myproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.Helpers.tripsHelper;
import com.example.myproject.R;
import com.example.myproject.tripsPage;

import java.util.ArrayList;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder>{

    static ArrayList<tripsHelper> list;
    Context context;
    public TripsAdapter(ArrayList<tripsHelper> list, tripsPage activity) {
        this.list=list;
        this.context=activity;
    }
    private TripsAdapter.onItemClickListener mListener;
    public interface onItemClickListener {
        void onBookClick(int position);
    }

    public void setOnItemListener(TripsAdapter.onItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.trips_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        tripsHelper myTripDataList=list.get(position);
        holder.trip_to.setText(myTripDataList.getToTrip());
        holder.trip_from.setText(myTripDataList.getFromTrip());
        holder.trip_price.setText(myTripDataList.getPriceTrip());
        holder.car_num.setText(myTripDataList.getNumTrip());
        holder.trip_time.setText(myTripDataList.getTimeTrip());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView trip_to,trip_from,trip_price,car_num,trip_time;

        public ViewHolder(@NonNull View itemView,TripsAdapter.onItemClickListener listener) {
            super(itemView);
            trip_to=itemView.findViewById(R.id.tripTo);
            trip_from=itemView.findViewById(R.id.tripFrom);
            trip_price =itemView.findViewById(R.id.TripPrice);
            car_num= itemView.findViewById(R.id.carNum);
            trip_time= itemView.findViewById(R.id.tripTime);

            itemView.findViewById(R.id.btnBook).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onBookClick(position);

                    }
                }
            });

        }
    }
}
