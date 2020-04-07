package com.example.skepsi;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class playLastRecording extends AppCompatActivity {


    Button buttonStart, buttonStop ;
    String AudioSavePathInDevice = null;
    MediaPlayer mediaPlayer ;
    private static final String TAG = "playLastRecording";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_last_recording);
        buttonStart = findViewById(R.id.play);
        buttonStop = findViewById(R.id.stop);

        buttonStop.setEnabled(false);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("REC_NAME");
                Log.d(TAG, "onCreate: " + newString);
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("REC_NAME");
        }
        AudioSavePathInDevice = newString;

        //start the recording
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                buttonStop.setEnabled(true);



                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
//                Toast.makeText(playLastRecording.this, AudioSavePathInDevice,
//                        Toast.LENGTH_LONG).show();
            }
        });

        //stop the recording
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);

                if(mediaPlayer != null){
                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                    MediaRecorderReady();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        // if the back button is press, the recording stops

        if(mediaPlayer != null){
            mediaPlayer.stop();
            Log.d(TAG, "onBackPressed: send back");
        }


        super.onBackPressed();
    }




    //save the audio path is the phone orientation is changed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("SaveState", "onSaveInstanceState called");

        //save current counter value in bundle key - value
        outState.putString("SAVED_STATE_PATH", AudioSavePathInDevice);
    }

    //restore the audio path
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        Log.d("SaveState", "onRestoreInstanceState called");

        //retrieve current counter value from bundle based on key
        String retrievedPath = savedInstanceState.getString("SAVED_STATE_PATH");

        //update text view

        AudioSavePathInDevice = retrievedPath; //update total number of clicks
    }

}
