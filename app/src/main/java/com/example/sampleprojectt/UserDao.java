package com.example.sampleprojectt;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert()
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users ORDER BY id DESC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT phoneNumber FROM users WHERE phoneNumber = :phoneNumberProvided")
    long validatePhoneNumber(long phoneNumberProvided);

    @Query("SELECT telephoneNumber FROM users WHERE telephoneNumber = :telephoneNumberProvided")
    long validateTelephoneNumber(long telephoneNumberProvided);
}
