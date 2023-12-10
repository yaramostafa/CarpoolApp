package com.example.driverside;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationFragment extends Fragment {
    Button req,hist;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    TextView idText,emailText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);


        idText = view.findViewById(R.id.username);
        emailText = view.findViewById(R.id.userEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user != null) {
            // Get the user ID
            String userId = user.getUid();

            // Reference to the "users" node in the database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("drivers").child(userId);

            // Read data from the database
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve user data
                        String userEmail = dataSnapshot.child("email").getValue(String.class);
                        String userName = dataSnapshot.child("uniID").getValue(String.class);

                        // Set the values to TextViews
                        idText.setText(userName);
                        emailText.setText(userEmail);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

        req=view.findViewById(R.id.btnViewRequestedTrips);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getActivity(),tripsRequests.class);
                startActivity(intent2);
            }
        });

        hist=view.findViewById(R.id.btnViewHistory);

        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getActivity(),historyTrips.class);
                startActivity(intent2);
            }
        });

        return view;
    }
}