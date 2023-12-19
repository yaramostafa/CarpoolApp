package com.example.driverside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.driverside.Helpers.addTripHelper;
import com.example.driverside.model.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class addTripPage extends AppCompatActivity {
    EditText from, to, price, num, maxNum;
    DatePicker picker;
    Spinner time;
    Button addBtn;
    static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_options);
        addBtn = findViewById(R.id.addTrip);
        FirebaseHelper firebaseDB = FirebaseHelper.getInstance();

        from = findViewById(R.id.location);
        to = findViewById(R.id.destination);
        price = findViewById(R.id.priceTrip);
        num = findViewById(R.id.carNum);
        time = findViewById(R.id.Time);
        maxNum = findViewById(R.id.maxNum);
        picker = findViewById(R.id.datePicker1);
        String status="ongoing";

        // Check the last ID from the database
        firebaseDB.getTripsReference().orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String lastTripId = childSnapshot.getKey();
                    id = extractIdFromTripId(lastTripId);
                }
                id = id > 0 ? id : 0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    String userName = user.getEmail();
                    String fromInput, toInput, priceInput, numInput, selectedTime, riders,driverID,driverStatus;
                    fromInput = from.getText().toString();
                    selectedTime = time.getSelectedItem().toString();
                    toInput = to.getText().toString();
                    priceInput = price.getText().toString();
                    numInput = num.getText().toString();
                    riders = maxNum.getText().toString();
                    int day = picker.getDayOfMonth();
                    int month = picker.getMonth() + 1; // Month is zero-based
                    int year = picker.getYear();
                    String selectedDate = String.format("%02d/%02d/%04d", month, day, year);

                    driverStatus=status;
                    driverID=userName;


                    if (fromInput.isEmpty() || selectedTime.isEmpty() || toInput.isEmpty() || priceInput.isEmpty() || numInput.isEmpty()) {
                        Toast.makeText(addTripPage.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (selectedTime.equals("5:30 PM")) {
                        if (!from.getText().toString().equalsIgnoreCase("Gate 4") &&
                                !from.getText().toString().equalsIgnoreCase("Gate 3")) {
                            Toast.makeText(addTripPage.this, "For 5:30PM, 'from' must be Gate 4 or Gate 3", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else if (selectedTime.equals("7:30 AM")) {
                        if (!to.getText().toString().equalsIgnoreCase("Gate 4") &&
                                !to.getText().toString().equalsIgnoreCase("Gate 3")) {
                            Toast.makeText(addTripPage.this, "For 7:30AM, 'to' must be Gate 4 or Gate 3", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    firebaseDB.isTimeSlotAvailable(selectedTime, selectedDate, new FirebaseHelper.TimeSlotCallback() {
                        @Override
                        public void onCallback(boolean isAvailable) {
                            if (!isAvailable) {
                                Toast.makeText(addTripPage.this, "There is already a trip scheduled at this time.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String tripid;

                            // Continue with adding the trip
                            id = id + 1;
                            String tripId = "trip" + String.format("%03d", id);
                            tripid = tripId;

                            addTripHelper helperClass = new addTripHelper(fromInput, toInput, priceInput, numInput,
                                    selectedTime, riders, driverID, driverStatus, tripid, selectedDate);
                            firebaseDB.insertTrip(tripId, helperClass);

                            Toast.makeText(addTripPage.this, "Trip added successfully!", Toast.LENGTH_SHORT).show();
                            // Clear text fields
                            from.getText().clear();
                            to.getText().clear();
                            price.getText().clear();
                            num.getText().clear();
                            maxNum.getText().clear();
                        }
                    });


                }else {
                    // User is not logged in, handle accordingly
                    Toast.makeText(getApplicationContext(), "User not logged in",
                            Toast.LENGTH_SHORT).show();
                }


            }

        });
    }

    private int extractIdFromTripId(String tripId) {

        try {
            // Remove the "order" prefix and parse the remaining part as an integer
            return Integer.parseInt(tripId.replace("trip", ""));
        } catch (NumberFormatException e) {
            // Handle the case where parsing fails, if the numeric part is not an integer
            return 0;
        }
    }
}
