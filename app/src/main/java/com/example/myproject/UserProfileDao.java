package com.example.myproject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserProfileDao {
    @Insert
    void insert(UserProfile userProfile);

    @Query("SELECT * FROM profile")
    List<UserProfile> getAllProfiles();
}
