package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.model.UserRepository;
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

    TextView email, name, time;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        email = findViewById(R.id.emailTextView);
        name = findViewById(R.id.nameTextView);
        time = findViewById(R.id.tripsTextView);

        userRepository = new UserRepository(this);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String currentTime = sdf.format(calendar.getTime());
        time.setText("Current Time: " + currentTime);

        // Get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Get the user ID
            String userId = user.getUid();

            // Check for internet connectivity
            if (isNetworkAvailable()) {
                // If internet is available, fetch data from Firebase
                fetchDataFromFirebase(userId);
            } else {
                // If no internet, fetch data from Room database
                fetchDataFromRoom(userId);
            }
        }

        ImageButton btn = findViewById(R.id.logOut);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(UserProfile.this, SignInPage.class);
                startActivity(intent2);
                finish();
            }
        });

        /*FloatingActionButton home = findViewById(R.id.floatingActionButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(UserProfile.this, tripsPage.class);
                startActivity(intent2);
                finish();
            }
        });*/
    }

    private void fetchDataFromFirebase(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userEmail = dataSnapshot.child("email").getValue(String.class);
                    String userName = dataSnapshot.child("uniID").getValue(String.class);

                    // Save data to Room database
                    saveDataToRoom(userId, userEmail, userName);

                    // Set the values to TextViews
                    runOnUiThread(() -> {
                        email.setText(userEmail);
                        name.setText(userName);
                    });
                } else {
                    // If data is not available from Firebase, try fetching from Room
                    fetchDataFromRoom(userId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void fetchDataFromRoom(String userId) {
        new AsyncTask<String, Void, UserRepository.UserEntity>() {
            @Override
            protected UserRepository.UserEntity doInBackground(String... userIds) {
                return userRepository.getUserById(userIds[0]);
            }

            @Override
            protected void onPostExecute(UserRepository.UserEntity userEntity) {
                if (userEntity != null) {
                    runOnUiThread(() -> {
                        email.setText(userEntity.email);
                        name.setText(userEntity.uniID);
                    });
                } else {
                    // Handle the case where data is not available locally
                }
            }
        }.execute(userId);
    }


    private void saveDataToRoom(String userId, String userEmail, String userName) {
        // Save user data to Room database
        UserRepository.UserEntity userEntity = new UserRepository.UserEntity();
        userEntity.userId = userId;
        userEntity.email = userEmail;
        userEntity.uniID = userName;
        userRepository.insertUser(userEntity);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
