package com.example.driverside.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driverside.Helpers.tripRequestsHelper;
import com.example.driverside.R;
import com.example.driverside.tripsRequests;

import java.util.ArrayList;

public class tripsRequestsAdapter extends RecyclerView.Adapter<tripsRequestsAdapter.ViewHolder> {
    static ArrayList<tripRequestsHelper> list;
    Context context;
    public tripsRequestsAdapter(ArrayList<tripRequestsHelper> list, tripsRequests activity) {
        this.list=list;
        this.context=activity;
    }
    private tripsRequestsAdapter.onItemClickListener mListener;
    public interface onItemClickListener {
        void onAcceptClick(int position);
        void onDeclineClick(int position);
    }

    public void setOnItemListener(tripsRequestsAdapter.onItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.requests_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view,mListener);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull tripsRequestsAdapter.ViewHolder holder, int position) {
        tripRequestsHelper myTripDataList=list.get(position);
        holder.trip_user.setText(myTripDataList.getUserid());
        holder.trip_email.setText(myTripDataList.getEmail());
        holder.trip_time.setText(myTripDataList.getTimeTrip());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView trip_user,trip_email,trip_time;
        Button accept,decline;
        public ViewHolder(@NonNull View itemView, tripsRequestsAdapter.onItemClickListener listener) {
            super(itemView);
            trip_user=itemView.findViewById(R.id.userID);
            trip_email=itemView.findViewById(R.id.email);
            trip_time =itemView.findViewById(R.id.Time);
            accept=itemView.findViewById(R.id.btnAccept);
            decline=itemView.findViewById(R.id.btnDecline);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onAcceptClick(position);
                    }
                }
            });
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeclineClick(position);
                    }
                }
            });

        }
    }
}
