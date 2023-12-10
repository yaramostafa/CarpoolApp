package com.example.myproject;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    CartItem[] CartItem;
    Context context;
    public CartAdapter(CartItem[] CartItem, CartPage activity) {
        this.CartItem= CartItem;
        this.context=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.cart_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CartItem myCartList=CartItem[position];
        holder.CartTxt.setText(myCartList.getItemName());
        holder.CartPrice.setText("Price" +myCartList.getItemPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, myCartList.getItemName(), Toast.LENGTH_SHORT).show();
                String message = "Price" + myCartList.getItemPrice();
                Toast.makeText(context,message , Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return CartItem.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView CartTxt;
        TextView CartPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CartTxt = itemView.findViewById(R.id.itemName);
            CartPrice = itemView.findViewById(R.id.itemPrice);

            itemView.findViewById(R.id.btnPay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getContext() != null) {
                        Intent pay = new Intent(v.getContext(), paymentPage.class);
                        v.getContext().startActivity(pay);
                    }
                }
            });

        }
    }
}
