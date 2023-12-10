package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class CartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        RecyclerView recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CartItem[] myCartData=new CartItem[]{
                new CartItem("ain shams to abdo basha gate 4 to gate 3","90"),
                new CartItem("ain shams to abdo basha gate 4 to gate 3","120"),
                new CartItem("ain shams to abdo basha gate 4 to gate 3","60"),

        };

        CartAdapter myCartAdapter= new CartAdapter(myCartData,CartPage.this);
        recyclerView.setAdapter(myCartAdapter);
    }
}