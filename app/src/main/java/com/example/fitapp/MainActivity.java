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
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Bundle bundle, bundle2;
    public TextView route, stoper;

    Button start, stop;
    private BroadcastReceiver bRLocation = null, bRStoper = null;

    public static Boolean going = false;
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
                    String time = bundle2.getString("Stoper");
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
        going = true;
    };

    View.OnClickListener stopButton = v -> {
        going = false;
    };

}