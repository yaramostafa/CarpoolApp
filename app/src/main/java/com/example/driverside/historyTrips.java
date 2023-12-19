package com.example.driverside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.driverside.Adapters.historyTripsAdapter;
import com.example.driverside.Helpers.historyTripsHelper;
import com.example.driverside.model.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class historyTrips extends AppCompatActivity {

    ArrayList<historyTripsHelper> listH;
    RecyclerView recyclerView;
    historyTripsAdapter myAdap;
    FirebaseAuth authT;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_trips);
        FirebaseHelper firebaseDB = FirebaseHelper.getInstance();

        authT=FirebaseAuth.getInstance();
        user= authT.getCurrentUser();
        if(user==null){
            Intent intent=new Intent(getApplicationContext(),SignIn.class);
            startActivity(intent);
            finish();
        }


        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(historyTrips.this));

        listH = new ArrayList<>();
        myAdap=new historyTripsAdapter(listH,this);
        recyclerView.setAdapter(myAdap);

        firebaseDB.getOrderReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listH.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    historyTripsHelper tripH = dataSnapshot.getValue(historyTripsHelper.class);

                    // Only add the historyData to the list if the email matches the current user's email
                    if (tripH  != null && tripH.getDriverEmail() != null && tripH.getDriverEmail().equals(user.getEmail())) {
                        listH.add(tripH);
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
