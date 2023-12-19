package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.Helpers.orderHelper;
import com.example.myproject.Helpers.tripsHelper;
import com.example.myproject.model.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class paymentPage extends AppCompatActivity {
    Button payBtn1;
    RadioGroup radio;
    static int id = 0;
    tripsHelper trip;
    ArrayList<String> orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);
        FirebaseHelper firebaseDB = FirebaseHelper.getInstance();

        radio=findViewById(R.id.paymentRadioGroup);

        String to = getIntent().getStringExtra("from_content");
        String from = getIntent().getStringExtra("to_content");
        String price = getIntent().getStringExtra("price_content");
        String carNum = getIntent().getStringExtra("carnum_content");
        String time = getIntent().getStringExtra("time_content");
        String status = "pending";
        String driverState = getIntent().getStringExtra("driver_tripState");;
        String driverName=getIntent().getStringExtra("driver_content");
        //String ridersNum=getIntent().getStringExtra("ridersNum");
        String tripid=getIntent().getStringExtra("tripsID");

        // Display the received text content in a TextView or use it as needed
        TextView textViewReceived = findViewById(R.id.paymentTo);
        textViewReceived.setText("To: " + to);
        TextView textViewReceived2 = findViewById(R.id.paymentFrom);
        textViewReceived2.setText("From: " + from);
        TextView textViewReceived3 = findViewById(R.id.paymentPrice);
        textViewReceived3.setText("Trip Price: " + price);
        TextView textViewReceived4 = findViewById(R.id.paymentCarNum);
        textViewReceived4.setText("Car Number: " + carNum);

        payBtn1 = findViewById(R.id.payBtn);
        firebaseDB.getTripOrders(tripid, new FirebaseHelper.DataCallback<ArrayList<String>>() {
            @Override
            public void onDataLoaded(ArrayList<String> data) {
                orders = data;
            }
            @Override
            public void onError(String errorMessage) {
                orders =  new ArrayList<>();
            }
        });
        firebaseDB.getOrderReference().orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String lastTripId = childSnapshot.getKey();
                    id = extractIdFromOrderId(lastTripId);
                }
                id = id > 0 ? id : 0;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        firebaseDB.getTrip(tripid, new FirebaseHelper.DataCallback<tripsHelper>() {
            @Override
            public void onDataLoaded(tripsHelper data) {
                trip = data;
            }
            @Override
            public void onError(String errorMessage) {
            }
        });

        payBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (radio.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Please select a Payment method", Toast.LENGTH_SHORT).show();
                    radio.requestFocus();
                    return;
                } else if(Integer.parseInt(trip.getMaxRider()) == 0){
                    Toast.makeText(getApplicationContext(), "Trip Fully booked!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(paymentPage.this, SignInPage.class);
                    startActivity(intent1);
                    finish();
                    return;
                }

                if (user != null) {
                    //decrement the seat number when trip is booked
                    int seat = Integer.parseInt(trip.getMaxRider());
                    seat-=1;
                    String passengers = Integer.toString(seat);
                    firebaseDB.updateTripPassengerNumber(trip.getTripID(),passengers);

                    String userName = user.getEmail();

                    String tripState,fromInput, toInput, priceInput, numInput, selectedTime,
                            userID,userEmail,orderID,driverStatus;
                    tripState=status;
                    fromInput = from;
                    toInput = to;
                    priceInput = price;
                    numInput = carNum;
                    selectedTime = time;
                    userEmail=driverName;
                    driverStatus=driverState;

                    userID = userName != null ? userName : "";
                    id = id + 1;
                    //set trip id to increment with each new order
                    String orderId = "order" + String.format("%03d", id);
                    orderID=orderId;
                    orders.add(orderID);

                    // Include the user's name in the order
                    orderHelper helperClass = new orderHelper(tripState,fromInput, toInput,
                            priceInput, numInput, selectedTime, userID,userEmail,orderID,
                            driverStatus);
                    firebaseDB.updateTripOrders(tripid,orders);

                    firebaseDB.insertOrder(orderId,helperClass);


                    Toast.makeText(getApplicationContext(), "Successful Payment",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // User is not logged in, handle accordingly
                    Toast.makeText(getApplicationContext(), "User not logged in",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Extract and return the numerical part of the order ID
    private int extractIdFromOrderId(String orderId) {
        try {
            // Remove the "order" prefix and parse the remaining part as an integer
            return Integer.parseInt(orderId.replace("order", ""));
        } catch (NumberFormatException e) {
            // Handle the case where parsing fails, e.g., if the numeric part is not an integer
            return 0;
        }
    }
}
