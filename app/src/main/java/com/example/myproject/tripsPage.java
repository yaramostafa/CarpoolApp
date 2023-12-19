package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.myproject.Adapters.TripsAdapter;
import com.example.myproject.Helpers.tripsHelper;
import com.example.myproject.model.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class tripsPage extends AppCompatActivity {
    ArrayList<tripsHelper> list;
    RecyclerView recyclerView;
    TripsAdapter myAdapter;
    FirebaseAuth auth;
    FirebaseUser user;
    Button btnBook;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser == null){
            Intent intent1=new Intent(tripsPage.this, SignInPage.class);
            startActivity(intent1);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        btnBook=findViewById(R.id.btnBook);
        FirebaseHelper firebaseDB = FirebaseHelper.getInstance();

        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        if(user==null){
            Intent intent=new Intent(getApplicationContext(), SignInPage.class);
            startActivity(intent);
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerViewT);

        recyclerView.setLayoutManager(new LinearLayoutManager(tripsPage.this));

        list = new ArrayList<>();
        myAdapter = new TripsAdapter(list, this);
        recyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemListener(new TripsAdapter.onItemClickListener() {
            @Override
            public void onBookClick(int position) {
                Intent intent = new Intent(getApplicationContext(), paymentPage.class);
                intent.putExtra("from_content", list.get(position).getToTrip());
                intent.putExtra("to_content", list.get(position).getFromTrip());
                intent.putExtra("price_content", list.get(position).getPriceTrip());
                intent.putExtra("carnum_content", list.get(position).getNumTrip());
                intent.putExtra("time_content", list.get(position).getTimeTrip());
                intent.putExtra("driver_content", list.get(position).getDriverEmail());
                intent.putExtra("driver_tripState", list.get(position).getDriverStatus());
                intent.putExtra("ridersNum", list.get(position).getMaxRider());
                intent.putExtra("tripsID", list.get(position).getTripID());

                startActivity(intent);
            }
        });

        firebaseDB.getTripsReference().orderByChild("timeTrip").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    tripsHelper trip = dataSnapshot.getValue(tripsHelper.class);
                    if(Integer.parseInt(trip.getMaxRider()) > 0){
                        list.add(trip);
                    }
                }
                //Collections.sort(list, new TimeComparator());
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.action_history) {
            intent = new Intent(getApplicationContext(), historyPage.class);
            startActivity(intent);
            return true;
        }
         else if (id == R.id.action_profile) {
            intent=new Intent(getApplicationContext(),UserProfile.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}