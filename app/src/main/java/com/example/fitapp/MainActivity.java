package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime currentTime;
    Bundle bundle, bundle2;
    public TextView route, stoper;

    Button start, stop;
    private BroadcastReceiver bRLocation = null, bRStoper = null;
    private String[] options = {"Bike", "Rollers","Walk","Run"};
    public int type;
    private double longitude, latitude, totalDistance;
    private String time;
    public static Boolean going = false;
    public static ArrayList<String> tour = new ArrayList<String>();
    public LocationModel locationModel = new LocationModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        route = findViewById(R.id.route);
        stoper = findViewById(R.id.stoper);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        start.setOnClickListener(startButton);
        stop.setOnClickListener(stopButton);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                switch(selectedOption)
                {
                    case "Bike":
                        type = 0;
                        break;
                    case "Rollers":
                        type = 1;
                        break;
                    case "Walk":
                        type = 2;
                        break;
                    case "Run":
                        type = 3;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Intent i = new Intent(this, Possition.class);
        Intent ii = new Intent(this, Stoper.class);
        startService(i);
        startService(ii);

    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        going = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(bRLocation == null){
            bRLocation = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                            bundle = intent.getExtras();
                            String result = bundle.getString("location");
                            latitude = bundle.getDouble("latitude");
                            longitude = bundle.getDouble("longitude");
                            totalDistance = bundle.getDouble("totalDistance");
                            route.setText(result);

                }
            };
        }

        if(bRStoper == null){
            bRStoper = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.v("BroadcastReceived", "Received Broadcast");
                    bundle2 = intent.getExtras();
                    time = bundle2.getString("Stoper");
                    stoper.setText(time);
                }
            };
        }
        registerReceiver(bRLocation, new IntentFilter("location_update"));
        registerReceiver(bRStoper, new IntentFilter("timer"));


    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("distance", Possition.distance);
        outState.putDouble("totalDistance",Possition.totalDistance);
        outState.putInt("secs", Stoper.secs);

    }



    View.OnClickListener startButton = v -> {
        String fPos = Possition.changeToDegrees(true, latitude) + " " + Possition.changeToDegrees(false, longitude);
        currentTime = LocalDateTime.now();
        String InitTime = currentTime.format(formatter);
        locationModel.setId(-1);
        locationModel.setType(type);
        locationModel.setFirstPossition(fPos);
        locationModel.setInitTime(InitTime);
        going = true;
    };

    View.OnClickListener stopButton = v -> {
        String lPos = Possition.changeToDegrees(true, latitude) + " " + Possition.changeToDegrees(false, longitude);
        currentTime = LocalDateTime.now();
        String EndTime = currentTime.format(formatter);
        locationModel.setLastPossition(lPos);
        locationModel.setTime(time);
        locationModel.setDistance(totalDistance);
        locationModel.setEndTime(EndTime);
        locationModel.setTour(tour);

        DataBaseHelper db = new DataBaseHelper(MainActivity.this);
        boolean added = db.addLocation(locationModel);
        Toast.makeText(MainActivity.this,"Success = " + added, Toast.LENGTH_SHORT).show();

        going = false;
    };

}