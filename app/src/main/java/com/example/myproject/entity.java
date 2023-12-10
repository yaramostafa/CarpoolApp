package com.example.myproject;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile")
public class entity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String email;
    public String username;
}