package com.example.skepsi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class Util {

    //    SharedPreferences sp;
    private static int sTheme;

    public static void changeToTheme(Activity activity, int theme, SharedPreferences sp) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currentTheme", sTheme);
        editor.commit();
    }

    void onActivityStartSetTheme(Activity a, SharedPreferences sp) {
        switch (sTheme) {
            default:
            case Defaults.DARK_SANS:
                a.setTheme(R.style.darkSans);
                break;
            case Defaults.LIGHT_SANS:
                a.setTheme(R.style.vanillaSans);
                break;
            case Defaults.DARK_SERIF:
                a.setTheme(R.style.darkSerif);
                break;
            case Defaults.LIGHT_SERIF:
                a.setTheme(R.style.vanillaSerif);
                break;
        }
    }
}
