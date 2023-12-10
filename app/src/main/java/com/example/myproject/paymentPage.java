package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class paymentPage extends AppCompatActivity {
    Button payBtn1;
    RadioGroup radio;
    FirebaseDatabase database;
    DatabaseReference reference,ref;
    static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        radio=findViewById(R.id.paymentRadioGroup);



        String to = getIntent().getStringExtra("from_content");
        String from = getIntent().getStringExtra("to_content");
        String price = getIntent().getStringExtra("price_content");
        String carNum = getIntent().getStringExtra("carnum_content");
        String time = getIntent().getStringExtra("time_content");
        String status = "pending";
        String driverState = getIntent().getStringExtra("driver_tripState");;
        String driverName=getIntent().getStringExtra("driver_content");
        String ridersNum=getIntent().getStringExtra("ridersNum");

        // Display the received text content in a TextView or use it as needed
        TextView textViewReceived = findViewById(R.id.paymentTo);
        textViewReceived.setText("To: " + to);
        TextView textViewReceived2 = findViewById(R.id.paymentFrom);
        textViewReceived2.setText("From: " + from);
        TextView textViewReceived3 = findViewById(R.id.paymentPrice);
        textViewReceived3.setText("Trip Price: " + price);

        payBtn1 = findViewById(R.id.payBtn);

        // Retrieve the last order ID from the database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("orders");
        reference.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
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

        payBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (radio.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Please select a Payment option", Toast.LENGTH_SHORT).show();
                    radio.requestFocus();
                    return;
                }

                if (user != null) {
                    String userName = user.getEmail();

                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("orders");
                    ref = database.getReference("trips");

                    ref.child(ridersNum).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                tripsData trip = dataSnapshot.getValue(tripsData.class);

                                // Decrease maxRider by 1
                                int currentMaxRider = Integer.parseInt(trip.getMaxRider());
                                if (currentMaxRider > 0) {
                                    currentMaxRider--;

                                    // Update the maxRider for the specific trip
                                    ref.child(ridersNum).child("maxRider").setValue(String.valueOf(currentMaxRider));
                                } else {
                                    // Handle the case where maxRider is already 0
                                    Toast.makeText(getApplicationContext(), "No available seats in the trip",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle database error
                            Toast.makeText(getApplicationContext(), "Error updating maxRider: " + databaseError.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });



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
                    String orderId = "order" + Integer.toString(id);
                    orderID=orderId;

                    // Include the user's name in the order
                    historyHelper helperClass = new historyHelper(tripState,fromInput, toInput, priceInput, numInput,
                            selectedTime, userID,userEmail,orderID,driverStatus);

                    reference.child(orderId).setValue(helperClass);


                    Intent intent1 = new Intent(getApplicationContext(), historyPage.class);
                    startActivity(intent1);

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

    // Check if an order with the given ID already exists
    private boolean isOrderIdExists(int orderId) {
        // You may need to modify this part based on your database structure
        DatabaseReference orderRef = reference.child("order" + orderId);
        return orderRef != null;
    }

    // Extract and return the numerical part of the order ID
    private int extractIdFromOrderId(String tripId) {

        return Integer.parseInt(tripId.replaceAll("\\D+", ""));
    }
}
