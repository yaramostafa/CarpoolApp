package com.example.driverside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addTripPage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText from, to, price, num, maxNum;
    Spinner time;
    Button addBtn;
    static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_options);
        addBtn = findViewById(R.id.addTrip);

        from = findViewById(R.id.location);
        to = findViewById(R.id.destination);
        price = findViewById(R.id.priceTrip);
        num = findViewById(R.id.carNum);
        time = findViewById(R.id.Time);
        maxNum = findViewById(R.id.maxNum);
        String status="ongoing";


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("trips");

        // Check the last ID from the database
        reference.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
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
                    driverStatus=status;
                    driverID=userName;

                    if (fromInput.isEmpty() || selectedTime.isEmpty() || toInput.isEmpty() || priceInput.isEmpty() || numInput.isEmpty()) {
                        Toast.makeText(addTripPage.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    addTripHelper helperClass = new addTripHelper(fromInput, toInput, priceInput, numInput,
                            selectedTime, riders,driverID,driverStatus);
                    id = id + 1;
                    String tripId = "trip" + Integer.toString(id);
                    reference.child(tripId).setValue(helperClass);


                    Toast.makeText(addTripPage.this, "Trip added successfully!", Toast.LENGTH_SHORT).show();

                }else {
                    // User is not logged in, handle accordingly
                    Toast.makeText(getApplicationContext(), "User not logged in",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private int extractIdFromTripId(String tripId) {

        return Integer.parseInt(tripId.replaceAll("\\D+", ""));
    }
}
