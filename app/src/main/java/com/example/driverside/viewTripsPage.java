package com.example.driverside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.driverside.Adapters.viewTripsAdapter;
import com.example.driverside.Helpers.viewTripsHelper;
import com.example.driverside.model.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewTripsPage extends AppCompatActivity {
    ArrayList<viewTripsHelper> list;
    RecyclerView recyclerView;
    viewTripsAdapter myAdap;
    FirebaseAuth authT;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driver_trips);
        FirebaseHelper firebaseDB = FirebaseHelper.getInstance();

        authT=FirebaseAuth.getInstance();
        user= authT.getCurrentUser();
        if(user==null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        recyclerView = findViewById(R.id.recyclerViewDriverTrips);

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
                DatabaseReference orderRef = firebaseDB.UpdateDriverStatus(orderId);
                DatabaseReference orderRef2 = firebaseDB.UpdateUserStatus(orderId);
                orderRef.setValue("Completed");
                orderRef2.setValue("Completed");
            }
            @Override
            public void onCancelClick(int position) {
                // Update the RecyclerView
                myAdap.notifyItemChanged(position);

                // Get the orderId for the accepted order
                String orderId = list.get(position).getOrderId();
                DatabaseReference orderRef = firebaseDB.UpdateDriverStatus(orderId);
                DatabaseReference orderRef2 = firebaseDB.UpdateUserStatus(orderId);
                orderRef.setValue("Cancelled");
                orderRef2.setValue("Cancelled");
            }
        });

        firebaseDB.getOrderReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    viewTripsHelper tripDriver = dataSnapshot.getValue(viewTripsHelper.class);
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