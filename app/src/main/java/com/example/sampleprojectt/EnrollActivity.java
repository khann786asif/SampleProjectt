package com.example.sampleprojectt;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Year;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnrollActivity extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST = 101;
    private ImageView imageViewProfile;
    private Button buttonAddUser;
    private TextView textViewProfile;
    private TextInputLayout editTextFirstName, editTextLastName, editTextDateOfBirth,
            editTextGender, editTextCountry, editTextState, editTextHomeTown, editTextPhoneNumber,
            editTextTelephoneNumber;
    private String firstName, lastName, dateOfBirth, gender, country, state, homeTown;
    Long phoneNumber, telephoneNumber;
    byte[] imageByteArray;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        setTitle("Enroll");
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);
        initViews();
    }

    private void initViews() {
        imageViewProfile = findViewById(R.id.image_users);
        buttonAddUser = findViewById(R.id.button_submit);
        editTextFirstName = findViewById(R.id.edit_text_first_name);
        editTextLastName = findViewById(R.id.edit_text_last_name);
        editTextDateOfBirth = findViewById(R.id.edit_text_date_of_birth);
        editTextGender = findViewById(R.id.edit_text_gender);
        editTextCountry = findViewById(R.id.edit_text_country);
        editTextState = findViewById(R.id.edit_text_state);
        editTextHomeTown = findViewById(R.id.edit_text_home_town);
        editTextPhoneNumber = findViewById(R.id.edit_text_phone_number);
        editTextTelephoneNumber = findViewById(R.id.edit_text_telephone_number);
        textViewProfile = findViewById(R.id.text_view_profile);
        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        buttonAddUser.setOnClickListener(v -> addUser());
    }

    private void addUser(){
        if (validateImage() | validateFirstName() | validateLastName() | validateDateOfBirth()
                | validateGender() | validateState() | validateCountry() | validateHomeTown()
        | validatePhoneNumber() | validateTelephoneNumber()){
            return;
        }

        User user = new User(imageByteArray, firstName, lastName, dateOfBirth, gender, country, state, homeTown
                ,phoneNumber, telephoneNumber);

        MainActivity.userViewModel.insert(user);

        Toast.makeText(this, "User inserted Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EnrollActivity.this, MainActivity.class));
        finish();
    }

    private boolean validateFirstName() {
        firstName = Objects.requireNonNull(editTextFirstName.getEditText()).getText().toString().trim();

        if (firstName.isEmpty()) {
            editTextFirstName.setError("First Name Can't be empty");
            return true;
        } else {
            editTextFirstName.setError(null);
        }
        return false;
    }

    private boolean validateLastName() {
        lastName = Objects.requireNonNull(editTextLastName.getEditText()).getText().toString().trim();

        if (lastName.isEmpty()) {
            editTextLastName.setError("Last Name Can't be empty");
            return true;
        } else {
            editTextLastName.setError(null);
        }
        return false;
    }

    private boolean validateDateOfBirth() {
        dateOfBirth = Objects.requireNonNull(editTextDateOfBirth.getEditText()).getText().toString().trim();
        String date[] = dateOfBirth.split("/");

        String regex = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateOfBirth);

        if (!matcher.matches()) {
            editTextDateOfBirth.setError("Date should be in DD/MM/YYYY format");
            return true;
        }else if (Integer.parseInt(date[0]) > 31){
            editTextDateOfBirth.setError("Date is not correct");
            return true;
        }
        else if (Integer.parseInt(date[1]) > 12){
            editTextDateOfBirth.setError("Date is not correct");
            return true;
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Integer.parseInt(date[2]) > Year.now().getValue()){
                editTextDateOfBirth.setError("Date is not correct");
                return true;
            }
            else {
                editTextDateOfBirth.setError(null);
            }
        }
        return false;
    }

    private boolean validateGender() {
        gender = Objects.requireNonNull(editTextGender.getEditText()).getText().toString().trim().toLowerCase();

        if ((gender.equals("male") || gender.equals("female"))) {
            editTextGender.setError(null);
        } else {
            editTextGender.setError("Gender is either Male or Female");
            return true;
        }
        return false;
    }

    private boolean validateCountry(){
        country = Objects.requireNonNull(editTextCountry.getEditText()).getText().toString().trim();

        if (country.isEmpty()){
            editTextCountry.setError("Country can't be empty");
            return true;
        }
        else {
            editTextCountry.setError(null);
        }
        return false;
    }

    private boolean validateState(){
        state = Objects.requireNonNull(editTextState.getEditText()).getText().toString().trim();

        if (state.isEmpty()){
            editTextState.setError("State can't be empty");
            return true;
        }
        else {
            editTextState.setError(null);
        }
        return false;
    }

    private boolean validateHomeTown(){
        homeTown = Objects.requireNonNull(editTextHomeTown.getEditText()).getText().toString().trim();

        if (homeTown.isEmpty()){
            editTextHomeTown.setError("HomeTown can't be empty");
            return true;
        }
        else {
            editTextHomeTown.setError(null);
        }
        return false;
    }

   private boolean validatePhoneNumber(){
        String phoneNumberString = Objects.requireNonNull(editTextPhoneNumber.getEditText()).getText().toString();

        if (phoneNumberString.length() != 10){
            editTextPhoneNumber.setError("Phone Number should be 10 digit long");
            return true;
        }
        else {
            long phoneNumberProvided = 0;
            phoneNumber = Long.parseLong(phoneNumberString);
            try {
                phoneNumberProvided = MainActivity.userViewModel.validatePhoneNumber(phoneNumber);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (phoneNumberProvided == phoneNumber){
                editTextPhoneNumber.setError("Phone Number Already in use");
                return true;
            }else {
                editTextPhoneNumber.setError(null);
            }
        }
        return false;
    }

   private boolean validateTelephoneNumber(){
        String phoneNumberString = Objects.requireNonNull(editTextTelephoneNumber.getEditText()).getText().toString();

        if (phoneNumberString.length() != 10){
            editTextTelephoneNumber.setError("Telephone Number should be 10 digit long");
            return true;
        }
        else {
            long phoneNumberProvided = 0;
            telephoneNumber = Long.parseLong(phoneNumberString);
            try {
                phoneNumberProvided = MainActivity.userViewModel.validateTelephoneNumber(telephoneNumber);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (phoneNumberProvided == telephoneNumber){
                editTextTelephoneNumber.setError("Telephone Number Already in use");
                return true;
            }else {
                editTextTelephoneNumber.setError(null);
            }
        }
        return false;
    }

    private boolean validateImage(){
        if (imageUri == null){
            Toast.makeText(this, "Select Profile photo", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            try{
                InputStream iStream = getContentResolver().openInputStream(imageUri);
                imageByteArray = getBytes(iStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageViewProfile.setImageURI(imageUri);
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024 * 20;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}