package com.example.driverside.model;

import androidx.annotation.NonNull;

import com.example.driverside.Helpers.addTripHelper;
import com.example.driverside.Helpers.userDataHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {
    private FirebaseDatabase database;
    private DatabaseReference userReference, driverReference, tripsReference, orderReference;
    private static FirebaseHelper instance;
    private FirebaseHelper() {
        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference("users");
        driverReference = database.getReference("drivers");
        tripsReference = database.getReference("trips");
        orderReference = FirebaseDatabase.getInstance().getReference("orders");
    }
    public static synchronized FirebaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseHelper();
        }
        return instance;
    }
    public DatabaseReference getUserReference() {
        return userReference;
    }

    public DatabaseReference getDriverReference() {
        return driverReference;
    }

    public DatabaseReference getTripsReference() {
        return tripsReference;
    }

    public DatabaseReference getOrderReference() {
        return orderReference;
    }
    public void insertTrip(String orderId, addTripHelper trip){
        tripsReference.child(orderId).setValue(trip);
    }
    public DatabaseReference UpdateDriverStatus(String orderId){
        return orderReference.child(orderId).child("driverStatus");
    }
    public DatabaseReference UpdateUserStatus(String orderId){
        return orderReference.child(orderId).child("tripState");
    }
    public DatabaseReference tripCompleted(String orderId){
        return orderReference.child(orderId).child("tripState");
    }
    public DatabaseReference tripDeclined(String orderId){
        return orderReference.child(orderId).child("tripState");
    }
    public void checkUser(FirebaseAuth mAuth){
        FirebaseUser user = mAuth.getCurrentUser();
        final String[] uniID = new String[1];
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase= reference.orderByChild("uid").equalTo(user.getUid());
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("drivers");
        Query checkDriverDatabase  = reference2.orderByChild("uid").equalTo(user.getUid());
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    uniID[0] =  snapshot.child(user.getUid()).child("uniID").getValue(String.class);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        checkDriverDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    userDataHelper helperClass=new userDataHelper(uniID[0],user.getEmail(),user.getUid());
                    reference2.child(user.getUid()).setValue(helperClass);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public interface TimeSlotCallback {
        void onCallback(boolean isAvailable);
    }
    public void isTimeSlotAvailable(String selectedTime, String selectedDate, TimeSlotCallback callback) {
        DatabaseReference tripsRef = getTripsReference();
        Query query = tripsRef.orderByChild("timeTrip").equalTo(selectedTime);
        final int[] tripsAtSelectedTime = {0};
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tripSnapshot : dataSnapshot.getChildren()) {
                    addTripHelper existingTrip = tripSnapshot.getValue(addTripHelper.class);

                    // Check if the existing trip is on the same date and time
                    if (existingTrip != null && existingTrip.getTripDate().equals(selectedDate) && existingTrip.getTimeTrip().equals(selectedTime)) {
                        tripsAtSelectedTime[0]++;
                        if (tripsAtSelectedTime[0] >= 2) {
                            callback.onCallback(false);
                            return; // No need to continue checking
                        }
                    }
                }
                // Invoke the callback with the result
                callback.onCallback(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                callback.onCallback(false); // Notify callback of failure
            }
        });
    }
}
