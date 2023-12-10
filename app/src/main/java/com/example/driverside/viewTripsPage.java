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

public class viewTripsPage extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<viewTripsData> list;
    RecyclerView recyclerView;
    viewTripsAdapter myAdap;
    FirebaseAuth authT;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driver_trips);
        authT=FirebaseAuth.getInstance();
        user= authT.getCurrentUser();
        if(user==null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }


        recyclerView = findViewById(R.id.recyclerViewDriverTrips);
        ref = FirebaseDatabase.getInstance().getReference("orders");

        recyclerView.setLayoutManager(new LinearLayoutManager(viewTripsPage.this));

        list = new ArrayList<>();
        myAdap=new viewTripsAdapter(list,this);
        recyclerView.setAdapter(myAdap);

        myAdap.setOnItemListener(new viewTripsAdapter.onItemClickListener() {
            @Override
            public void onCompletedClick(int position) {
                // Update the RecyclerView
                myAdap.notifyItemChanged(position);
                // Get the orderId for the accepted order
                String orderId = list.get(position).getOrderId();
                DatabaseReference orderRef = ref.child(orderId).child("tripState");
                DatabaseReference orderRef2 = ref.child(orderId).child("driverStatus");
                orderRef.setValue("Completed");
                orderRef2.setValue("Completed");
            }
            @Override
            public void onCancelClick(int position) {
                // Update the RecyclerView
                myAdap.notifyItemChanged(position);

                // Get the orderId for the accepted order
                String orderId = list.get(position).getOrderId();

                DatabaseReference orderRef = ref.child(orderId).child("tripState");
                DatabaseReference orderRef2 = ref.child(orderId).child("driverStatus");
                orderRef.setValue("Cancelled");
                orderRef2.setValue("Cancelled");

            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    viewTripsData tripDriver = dataSnapshot.getValue(viewTripsData.class);

                    // Only add the historyData to the list if the email matches the current user's email
                    if (tripDriver != null && tripDriver.getDriverEmail() != null && tripDriver.getDriverEmail().equals(user.getEmail())) {
                        if(TextUtils.equals(tripDriver.getDriverStatus(),"ongoing")){
                            list.add(tripDriver);
                        }

                    }
                }
                myAdap.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}