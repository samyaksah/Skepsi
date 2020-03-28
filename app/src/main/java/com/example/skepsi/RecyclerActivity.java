package com.example.skepsi;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    RecyclerView myRecycler;
    MyDatabase db;
    RecyclerView.Adapter myAdapter;
    MyHelper helper;
    private RecyclerView.LayoutManager layoutManager;
    Cursor cursor;
    String x = "x";
    ArrayList<String> mArrayList;
    private static final String TAG = "RecyclerActivity";
    Switch toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        myRecycler = findViewById(R.id.recycler);

        db = new MyDatabase(this);
        helper = new MyHelper(this);
        layoutManager = new LinearLayoutManager(this);
        toggle = findViewById(R.id.switch1);
        myRecycler.setLayoutManager(layoutManager);
        cursor = db.getData();



        int index1 = cursor.getColumnIndex(Constants.NAME);
        int index2 = cursor.getColumnIndex(Constants.LAT);
        int index3 = cursor.getColumnIndex(Constants.LONGI);
        int index4 = cursor.getColumnIndex(Constants.ADD);


        mArrayList = new ArrayList<String>();
        cursor.moveToFirst();
        int pos = 0;


        while (!cursor.isAfterLast()) {
            String recordingName = cursor.getString(index1);
            String latitude = cursor.getString(index2);
            String longitude = cursor.getString(index3);
            String address = cursor.getString(index4);
            String s = recordingName + "?" + latitude + "?" + longitude + "?" + address;
            mArrayList.add(s);
            Log.d(TAG, "onCreate:" + pos);
            pos++;
            cursor.moveToNext();
        }


        myAdapter = new MyAdapter(mArrayList, this);

//        Toast.makeText(getApplicationContext(), "getting closer" , Toast.LENGTH_SHORT).show();
        myRecycler.setAdapter(myAdapter);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout clickedRow = (LinearLayout) view;
        TextView recordTextView = (TextView) view.findViewById(R.id.recordingname);
        Toast.makeText(this, "row " + (1+position) + ":  " + recordTextView.getText() , Toast.LENGTH_LONG).show();
    }
}

