package com.example.myproject.model;

import androidx.annotation.NonNull;

import com.example.myproject.Helpers.UserHelper;
import com.example.myproject.Helpers.orderHelper;
import com.example.myproject.Helpers.tripsHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    public DatabaseReference getTripsReference() {
        return tripsReference;
    }
    public DatabaseReference getOrderReference() {
        return orderReference;
    }

    public void updateTripPassengerNumber(String tripId, String passengers){
        tripsReference.child(tripId).child("maxRider").setValue(passengers);
    }
    public void insertOrder(String orderId, orderHelper order){
        orderReference.child(orderId).setValue(order);
    }

    public void checkUser(FirebaseAuth mAuth){
        FirebaseUser user = mAuth.getCurrentUser();
        final String[] uniID = new String[1];
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase= reference.orderByChild("uid").equalTo(user.getUid());
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("drivers");
        Query checkDriverDatabase  = reference2.orderByChild("uid").equalTo(user.getUid());
        checkDriverDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
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
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    UserHelper helperClass=new UserHelper(uniID[0],user.getEmail(),user.getUid());
                    reference.child(user.getUid()).setValue(helperClass);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public interface DataCallback<T> {
        void onDataLoaded(T data);
        void onError(String errorMessage);
    }
    public void getTrip(String tripId, DataCallback<tripsHelper> callback) {
        Query checkTripDatabase = tripsReference.orderByChild("tripID").equalTo(tripId);
        checkTripDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tripsHelper trip = snapshot.child(tripId).getValue(tripsHelper.class);
                    callback.onDataLoaded(trip);
                } else {
                    callback.onError("Trip not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }
    public void getTripOrders(String tripId, DataCallback<ArrayList<String>> callback) {
        Query checkTripDatabase = tripsReference.orderByChild("tripID").equalTo(tripId);
        checkTripDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tripsHelper trip = snapshot.child(tripId).child("").getValue(tripsHelper.class);

                    if (trip != null && trip.getOrders() != null) {
                        callback.onDataLoaded(new ArrayList<>(trip.getOrders()));
                    } else {
                        callback.onError("Orders not found for the trip");
                    }
                } else {
                    callback.onError("Trip not found");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }
    public void updateTripOrders(String tripId, ArrayList<String> orders){
        tripsReference.child(tripId).child("orders").setValue(orders);
    }

}

