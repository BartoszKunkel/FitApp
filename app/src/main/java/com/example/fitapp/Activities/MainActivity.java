package com.example.fitapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.Fragments.DataBaseHelper;
import com.example.fitapp.Adapter.ListAdapter;
import com.example.fitapp.Model.LocationModel;
import com.example.fitapp.Fragments.Position;
import com.example.fitapp.R;
import com.example.fitapp.Fragments.Stoper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime currentTime;
    private Bundle bundle, bundle2;
    private TextView route, stoper;


    private static ListView lv;
    private BroadcastReceiver bRLocation = null, bRStoper = null;
    public int type;
    private double longitude, latitude, totalDistance;
    private String time;
    public static Boolean going = false;
    public static ArrayList<String> tour = new ArrayList<>();
    public LocationModel locationModel = new LocationModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding components----------------------------
        route = findViewById(R.id.route);
        stoper = findViewById(R.id.stoper);
        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        Button show = findViewById(R.id.show);
        ImageButton edit = findViewById(R.id.profileEdit);
        lv = findViewById(R.id.activities);
        Spinner spinner = findViewById(R.id.spinner);

        //Setting Listeners-----------------------------
        start.setOnClickListener(startButton);
        stop.setOnClickListener(stopButton);
        show.setOnClickListener(showButton);
        edit.setOnClickListener(editProfileButton);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerItems, android.R.layout.simple_spinner_item);
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
                }}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        //Starting Services
        Intent ii = new Intent(this, Stoper.class);
        Intent i = new Intent(MainActivity.this, Position.class);
        startService(i);
        startService(ii);
        showListView(MainActivity.this);
    }



    //App Lifecycle---------------------------------------------------------------------------------

    @Override
    protected void onStart() {super.onStart();}

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        going = false;
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
                    route.setText(result);}
            };}
        if(bRStoper == null){
            bRStoper = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    bundle2 = intent.getExtras();
                    time = bundle2.getString("Stoper");
                    stoper.setText(time);
                }};
        }
        registerReceiver(bRLocation, new IntentFilter("location_update"));
        registerReceiver(bRStoper, new IntentFilter("timer"));

        Intent ii = new Intent(this, Stoper.class);
        Intent i = new Intent(MainActivity.this, Position.class);
        startService(i);
        startService(ii);
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putDouble("distance", Position.distance);
//        outState.putDouble("totalDistance", Position.totalDistance);
//        outState.putInt("secs", Stoper.secs);}


    //Listeners-------------------------------------------------------------------------------------

    View.OnClickListener editProfileButton = v ->
    {
      Intent i = new Intent(v.getContext(), ProfileEdit.class);
      startActivity(i);
    };


    View.OnClickListener showButton = v -> {
       showListView(MainActivity.this);
    };

    View.OnClickListener startButton = v -> {
        String fPos = latitude + " " + longitude;
        currentTime = LocalDateTime.now();
        String InitTime = currentTime.format(formatter);
        locationModel.setId(-1);
        locationModel.setType(type);
        locationModel.setFirstPosition(fPos);
        locationModel.setInitTime(InitTime);

        going = true;
    };

    View.OnClickListener stopButton = v -> {
        String lPos = latitude + " " + longitude;
        currentTime = LocalDateTime.now();
        String EndTime = currentTime.format(formatter);
        locationModel.setLastPosition(lPos);
        locationModel.setTime(time);
        locationModel.setDistance(totalDistance);
        locationModel.setEndTime(EndTime);
        locationModel.setTour(tour);

        DataBaseHelper db = new DataBaseHelper(MainActivity.this);
        boolean added = db.addLocation(locationModel);
        Toast.makeText(MainActivity.this,"Success = " + added, Toast.LENGTH_SHORT).show();
        showListView(MainActivity.this);
        going = false;
    };


    public static void showListView(Activity activity)
    {
        ListAdapter adapter = new ListAdapter(activity);
        lv.setAdapter(adapter);
    }

}