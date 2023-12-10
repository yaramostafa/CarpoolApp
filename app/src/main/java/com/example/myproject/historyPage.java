package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class historyPage extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<historyData> listH;
    RecyclerView recyclerView;
    HistoryAdapter myAdap;
    FirebaseAuth authT;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
        authT=FirebaseAuth.getInstance();
        user= authT.getCurrentUser();
        if(user==null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerViewHistory);
        ref = FirebaseDatabase.getInstance().getReference("orders");

        recyclerView.setLayoutManager(new LinearLayoutManager(historyPage.this));

        listH = new ArrayList<>();
        myAdap=new HistoryAdapter(listH,this);
        recyclerView.setAdapter(myAdap);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listH.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    historyData tripH = dataSnapshot.getValue(historyData.class);

                    // Only add the historyData to the list if the email matches the current user's email
                    if (tripH != null && tripH.getUserid() != null && tripH.getUserid().equals(user.getEmail())) {
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
