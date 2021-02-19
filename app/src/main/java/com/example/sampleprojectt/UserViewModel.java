package com.example.sampleprojectt;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(Application application){
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }

    public void insert(User user){
        userRepository.insert(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

   public long validatePhoneNumber(long phoneNumber) throws ExecutionException, InterruptedException {
        return userRepository.phoneNumberValidation(phoneNumber);
    }

    public long validateTelephoneNumber(long telephoneNumber) throws ExecutionException, InterruptedException {
        return userRepository.telephoneNumberValidation(telephoneNumber);
    }

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }

}
