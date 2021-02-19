package com.example.sampleprojectt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private byte[] image;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String country;
    private String state;
    private String homeTown;
    private long phoneNumber;
    private long telephoneNumber;

    public User(byte[] image, String firstName, String lastName, String dateOfBirth, String gender,
                String country, String state, String homeTown, long phoneNumber, long telephoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.country = country;
        this.state = state;
        this.homeTown = homeTown;
        this.phoneNumber = phoneNumber;
        this.telephoneNumber = telephoneNumber;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public long getTelephoneNumber() {
        return telephoneNumber;
    }

    public byte[] getImage(){
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }
}
