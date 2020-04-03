package com.example.skepsi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Switch finger;
    Button submit;
    String useFinger = "false";
    SharedPreferences sharedPrefs;
    public static final String DEFAULT = "not available";
    private static final String TAG = "Signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        finger = findViewById(R.id.switch3);
        submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(myListener);
        finger.setOnClickListener(switchListener);
        sharedPrefs = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);



    }
    //set the finger value to true if user toggles the switch
    private Switch.OnClickListener switchListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            useFinger = "true";
        }
    };

    //send the credentials to shared prefs
    private Button.OnClickListener myListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("username", usernameEditText.getText().toString());
            editor.putString("password", passwordEditText.getText().toString());
            editor.putString("useFinger", useFinger);
//            Toast.makeText(this, "Username and password saved to Preferences", Toast.LENGTH_LONG).show();
            editor.commit();

            gotoActivity2();
        }
    };




    public void gotoActivity2(){
//        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
        Log.d(TAG, "gotoActivity2: going to acitvi two");
        Intent intent= new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void gotoActivity3(){
//        Toast.makeText(this, "A", Toast.LENGTH_LONG).show();
        Intent intent= new Intent(this, record.class);
        startActivity(intent);
        finish();
    }
}
