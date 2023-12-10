package com.example.myproject;

public class CartItem {
    private String itemName;
    private String itemPrice;

    public CartItem(String itemName, String itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }
}
