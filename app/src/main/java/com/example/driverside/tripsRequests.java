package com.example.driverside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.driverside.Adapters.tripsRequestsAdapter;
import com.example.driverside.Helpers.tripRequestsHelper;
import com.example.driverside.model.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class tripsRequests extends AppCompatActivity {
    ArrayList<tripRequestsHelper> dataList;
    RecyclerView recyclerView;
    tripsRequestsAdapter myAdapter;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_requests);
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        FirebaseHelper firebaseDB = FirebaseHelper.getInstance();

        if(user==null){
            Intent intent=new Intent(getApplicationContext(),SignIn.class);
            startActivity(intent);
            finish();
        }
        recyclerView = findViewById(R.id.recyclerViewReq);

        recyclerView.setLayoutManager(new LinearLayoutManager(tripsRequests.this));

        dataList = new ArrayList<>();
        myAdapter= new tripsRequestsAdapter(dataList,this);
        recyclerView.setAdapter(myAdapter);


        myAdapter.setOnItemListener(new tripsRequestsAdapter.onItemClickListener() {
            @Override
            public void onAcceptClick(int position) {
                // Update the RecyclerView
                myAdapter.notifyItemChanged(position);
                // Get the orderId for the accepted order
                String orderId = dataList.get(position).getOrderId();
                DatabaseReference orderRef = firebaseDB.tripCompleted(orderId);
                orderRef.setValue("Accepted");
            }
            @Override
            public void onDeclineClick(int position) {
                // Update the RecyclerView
                myAdapter.notifyItemChanged(position);

                // Get the orderId for the accepted order
                String orderId = dataList.get(position).getOrderId();

                DatabaseReference orderRef = firebaseDB.tripDeclined(orderId);
                orderRef.setValue("Declined");

            }
        });

        firebaseDB.getOrderReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    tripRequestsHelper order = dataSnapshot.getValue(tripRequestsHelper.class);

                    if (TextUtils.equals(order.getDriverEmail(),(user.getEmail()))) {
                        if(TextUtils.equals(order.getTripState(),"pending")){
                            dataList.add(order);
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}