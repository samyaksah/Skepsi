package com.example.skepsi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    Timer timer;

    CountDownTimer loading;
    MainActivity curr = this;

    SharedPreferences sharedPrefs, themes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        sharedPrefs = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

//        timer = new Timer();
//        String password = sharedPrefs.getString("password", Defaults.PASSWORD);
        startCounting();

    }

    void startCounting() {
        CountingTask tsk = new CountingTask(curr);
        tsk.execute();
        Toast.makeText(this, "counting", Toast.LENGTH_LONG).show();

//changeActivity();
    }

    public void changeActivity() {
        String text = "login";

            Intent i = new Intent(curr, SignUp.class);
            text = "sign up";
            startActivity(i);
            finish();


    }
}

class CountingTask extends AsyncTask<Void, Integer, Integer> {
    private final MainActivity aty;

    CountingTask(MainActivity a) {
        aty = a;

    }

    @Override
    protected Integer doInBackground(Void... voids) {
        SystemClock.sleep(2500);
        return 1;

    }

    @Override
    protected void onPostExecute(Integer l) {
        aty.changeActivity();
    }

}



