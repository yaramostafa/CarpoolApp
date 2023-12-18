package com.example.myproject;

import android.content.Context;

import androidx.room.Room;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// UserRepository.java
public class UserRepository {
    private UserDao userDao;
    private Executor executor = Executors.newSingleThreadExecutor(); // Use Executor for background operations

    public UserRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "app-database").build();
        userDao = db.userDao();
    }

    public void insertUser(UserEntity user) {
        executor.execute(() -> userDao.insertUser(user));
    }

    public UserEntity getUserById(String userId) {
        return userDao.getUserById(userId);
    }
}
