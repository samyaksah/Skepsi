package com.example.skepsi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class playLastRecording extends AppCompatActivity {


    Button buttonStart, buttonStop ;
    String AudioSavePathInDevice = null;
    MediaPlayer mediaPlayer ;


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
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("REC_NAME");
        }
        AudioSavePathInDevice = newString;


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                buttonStop.setEnabled(true);

//                try {
//                    mediaRecorder.prepare();
//                    mediaRecorder.start();
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(playLastRecording.this, AudioSavePathInDevice,
                        Toast.LENGTH_LONG).show();
            }
        });


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

    public void viewRecord(View view){
        Intent intent = new Intent(this, RecyclerActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("SaveState", "onSaveInstanceState called");

        //save current counter value in bundle key - value
        outState.putString("SAVED_STATE_PATH", AudioSavePathInDevice);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("SaveState", "onRestoreInstanceState called");

        //retrieve current counter value from bundle based on key
        String retrievedPath = savedInstanceState.getString("SAVED_STATE_PATH");

        //update text view

        AudioSavePathInDevice = retrievedPath; //update total number of clicks
    }

}
