package com.example.myproject.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myproject.model.UserDao;
import com.example.myproject.model.UserRepository;

@Database(entities = {UserRepository.UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
