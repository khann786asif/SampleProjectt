package com.example.sampleprojectt;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
    }

    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user){
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public long phoneNumberValidation(long phoneNumber) throws ExecutionException, InterruptedException {
       return new ValidatingPhoneNumberAsyncTask(userDao).execute(phoneNumber).get();
    }

    public long telephoneNumberValidation(long telephoneNumber) throws ExecutionException, InterruptedException {
        return new ValidatingTelephoneNumberAsyncTask(userDao).execute(telephoneNumber).get();
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDao userDao;
        private InsertUserAsyncTask(UserDao noteDao) {
            this.userDao = noteDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    private static class ValidatingPhoneNumberAsyncTask extends AsyncTask<Long, Void, Long> {
        private UserDao userDao;
        private ValidatingPhoneNumberAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        @Override
        protected Long doInBackground(Long... phoneNumber) {
            return userDao.validatePhoneNumber(phoneNumber[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }


    private static class ValidatingTelephoneNumberAsyncTask extends AsyncTask<Long, Void, Long> {
        private UserDao userDao;
        private ValidatingTelephoneNumberAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        @Override
        protected Long doInBackground(Long... phoneNumber) {
            return userDao.validateTelephoneNumber(phoneNumber[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }

}
