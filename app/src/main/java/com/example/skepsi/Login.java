package com.example.skepsi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    final EditText usernameEditText = findViewById(R.id.username);
    final EditText passwordEditText = findViewById(R.id.password);
    final Button loginButton = findViewById(R.id.login);
    final ProgressBar loadingProgressBar = findViewById(R.id.loading);
    private String firstName;
    private String lastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void saveData(View view) {
//        firstName = firstNameEditText.getText().toString();
//        lastName = lastNameEditText.getText().toString();

//        Toast.makeText(this, firstName + lastName + color, Toast.LENGTH_SHORT).show();
//
//        Intent i = new Intent(this, ActivityTwo.class);
//
//        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.putString("firstName", firstName);
//        editor.putString("lastName", lastName);
//        editor.putString("selectedColor", color);
//        Toast.makeText(this, "First, last names and color saved to Preferences", Toast.LENGTH_LONG).show();
//        editor.commit();
    }
}
