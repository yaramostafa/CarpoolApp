package com.example.myproject.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

// UserDao.java
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserRepository.UserEntity user);

    @Query("SELECT * FROM users WHERE userId = :userId")
    UserRepository.UserEntity getUserById(String userId);
}
