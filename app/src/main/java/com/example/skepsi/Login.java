package com.example.skepsi;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {

    EditText usernameTextView, passwordTextView;
    Button retrButton, regButton;
    SharedPreferences sharedPrefs;
    String username, password, finger;
    private static final String TAG = MainActivity.class.getName();

    public static final String DEFAULT = "not available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: in login");
        usernameTextView = findViewById(R.id.retrUsername);
        passwordTextView = findViewById(R.id.retrPassword);
        retrButton = findViewById(R.id.retrButton);
//        regButton = findViewById(R.id.regButton);

        sharedPrefs = getSharedPreferences(getResources().getString(R.string.sharedPreferencesFileKey), Context.MODE_PRIVATE);
        finger = sharedPrefs.getString("useFinger", DEFAULT);
        Log.d(TAG, "onCreate: fingerprint " + finger);

        Executor executor = Executors.newSingleThreadExecutor();
        Log.d(TAG, "onCreate: 1");
        FragmentActivity activity = this;
        Log.d(TAG, "onCreate: 2");

        final BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.d(TAG, "onAuthenticationError: ");
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                } else {
                    //TODO: Called when an unrecoverable error has been encountered and the operation is complete.
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.d(TAG, "Fingerprint recognised successfully");
                Intent status = new Intent(getApplicationContext(), record.class);
                startActivity(status);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.d(TAG, "Fingerprint not recognised");
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Set the title to display.")
                .setSubtitle("Set the subtitle to display.")
                .setDescription("Set the description to display")
                .setNegativeButtonText("Negative Button")
                .build();


        Log.d(TAG, "onCreate: 3");
        if(finger.equals("false")) {
            Log.d(TAG, "onCreate: inside false");
            retrButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onCreate: mannual ");
                    sharedPrefs = getSharedPreferences(getResources().getString(R.string.sharedPreferencesFileKey), Context.MODE_PRIVATE);
                    username = sharedPrefs.getString("username", DEFAULT);
                    password = sharedPrefs.getString("password", DEFAULT);
                    if (username.equals(DEFAULT) || password.equals(DEFAULT)) {
                        Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                    } else if (username.equals(usernameTextView.getText().toString()) && password.equals(passwordTextView.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                        gotoSettingsActivity();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Data: Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else if(finger.equals("true")){
            Toast.makeText(getApplicationContext(), "using fingerprint", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onCreate: using finger " + finger);
            biometricPrompt.authenticate(promptInfo);
        }

    }



    public void gotoSettingsActivity(){
        Intent intent= new Intent(this, record.class);
        intent.putExtra("NAME", username);
        startActivity(intent);
        finish();

    }
}

