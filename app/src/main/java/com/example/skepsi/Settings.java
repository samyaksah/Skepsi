package com.example.skepsi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup colorButton;
    String theme;
    String font;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        if (settings.contains("font")) {
            font = settings.getString("font", Defaults.FONT);
            theme = settings.getString("theme", Defaults.THEME);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//        String initFont = font, initCol = theme;
//
//        switch (i) {
//            case R.id.serifRadioButton:
//                font  = "dark";
//                break;
//            case R.id.sansRadioButton:
//                font = "light";
//                break;
//
//        }
////        switch(i){
////            case R.id.
////
////        }
//    }


    }
}