package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserProfile extends AppCompatActivity {
    TextView email, name,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        email = findViewById(R.id.emailTextView);
        name = findViewById(R.id.nameTextView);
        time = findViewById(R.id.tripsTextView);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String currentTime = sdf.format(calendar.getTime());
        time.setText("Current Time: " + currentTime);

        // Get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Get the user ID
            String userId = user.getUid();

            // Reference to the "users" node in the database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            // Read data from the database
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve user data
                        String userEmail = dataSnapshot.child("email").getValue(String.class);
                        String userName = dataSnapshot.child("uniID").getValue(String.class);

                        // Set the values to TextViews
                        email.setText(userEmail);
                        name.setText(userName);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

        ImageButton btn = findViewById(R.id.logOut);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(UserProfile.this, MainActivity.class);
                startActivity(intent2);
                finish();
            }
        });

        FloatingActionButton home = findViewById(R.id.floatingActionButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(UserProfile.this, tripsPage.class);
                startActivity(intent2);
                finish();
            }
        });
    }
}
