package com.example.driverside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class tripsRequests extends AppCompatActivity {
    DatabaseReference ref;
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

        if(user==null){
            Intent intent=new Intent(getApplicationContext(),SignIn.class);
            startActivity(intent);
            finish();
        }
        recyclerView = findViewById(R.id.recyclerViewReq);
        ref = FirebaseDatabase.getInstance().getReference("orders");

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
                DatabaseReference orderRef = ref.child(orderId).child("tripState");
                orderRef.setValue("Accepted");
            }
            @Override
            public void onDeclineClick(int position) {
                // Update the RecyclerView
                myAdapter.notifyItemChanged(position);

                // Get the orderId for the accepted order
                String orderId = dataList.get(position).getOrderId();

                DatabaseReference orderRef = ref.child(orderId).child("tripState");
                orderRef.setValue("Declined");

            }
        });

        ref.addValueEventListener(new ValueEventListener() {
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