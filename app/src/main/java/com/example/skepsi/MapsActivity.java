package com.example.skepsi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    Switch toggle;
    MyDatabase db;
    Cursor cursor;
    ArrayList<String> mArrayList;
    String path;
    Context context;
    private static final String TAG = "MapsActivity";
    public MapsActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = new MyDatabase(this);
        cursor = db.getData();


        toggle = findViewById(R.id.switch2);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), RecyclerActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(49.24057781, -123.02991003);
        Marker mark = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        int i = 0;
//       while(i<10){
//            Log.d(TAG, "onMapReady: " + i);
//            i++;
//        }

        cursor.moveToFirst();
        int index1 = cursor.getColumnIndex(Constants.NAME);
        int index2 = cursor.getColumnIndex(Constants.LAT);
        int index3 = cursor.getColumnIndex(Constants.LONGI);
        int index4 = cursor.getColumnIndex(Constants.ADD);

        while (!cursor.isAfterLast()) {
            String recordingName = cursor.getString(index1);
            String latitude = cursor.getString(index2);
            String longitude = cursor.getString(index3);
            String address = cursor.getString(index4);
            String s = recordingName + " | " + latitude + " | " + longitude + " | " + address;
            Log.d(TAG, "onMapReady: " + s);
            String[] rows = (recordingName).split("\\?");
            String[] results = (rows[0]).split("/");
            String[] rec_name_mid = results[4].split("\\.");
            String rec_name = rec_name_mid[0];
            createMarker(mMap, Double.parseDouble(latitude), Double.parseDouble(longitude), rec_name);
//            mArrayList.add(s);
            cursor.moveToNext();
        }

//        for(int i = 0; i < 10; i++){
//            createMarker(mMap, 34 * 0.1 * i, 56* 0.1 * i, "recordingName" + i*10);
//        }

        // Add a marker in Sydney and move the camera
//        createMarker(mMap, markersArray.get(i).getLatitude(), markersArray.get(i).getLongitude(), markersArray.get(i).getTitle(), markersArray.get(i).getSnippet(), markersArray.get(i).getIconResID());
        createMarker(mMap, 34, 56, "Recording 01");
        createMarker(mMap, 38, 57, "Recording 02");
        createMarker(mMap, 40, 60, "Recording 03");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(15);
        mMap.setOnMarkerClickListener(this);

    }

    protected Marker createMarker(GoogleMap googleMap, double latitude, double longitude, String title) {
        Log.d(TAG, "createMarker: created");
            return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {


        path = "/storage/emulated/0/" + marker.getTitle() +".3gp";
        Log.d(TAG, "onMarkerClick: getting here" + path);

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alert dialog builder
        alertDialogBuilder.setView(promptsView);

            final TextView header = promptsView.findViewById(R.id.header);
            header.setText("Open " + marker.getTitle() + "?");
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                gotoLastRecording();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        return false;
    }

    public void gotoLastRecording(){
        this.activity = (MapsActivity) activity;
        Log.d(TAG, "gotoLastRecording: " + path);
        Intent refresh = new Intent(getApplicationContext(), playLastRecording.class);
        refresh.putExtra("REC_NAME", path);
        startActivity(refresh);
    }
}
