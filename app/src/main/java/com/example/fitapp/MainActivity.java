package com.example.fitapp;

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
    Bundle bundle;
    public TextView route;

    Button start, stop;
    private BroadcastReceiver bR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        route = findViewById(R.id.route);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        start.setOnClickListener(startButton);
        stop.setOnClickListener(stopButton);
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
    protected void onResume() {
        super.onResume();
        if(bR == null){
            bR = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.v("BroadcastReceived", "Received Broadcast");
                            bundle = intent.getExtras();
                            String result = bundle.getString("location");
                            route.setText(result);
                }
            };
        }
        IntentFilter locationFilter = new IntentFilter("location_update");
        registerReceiver(bR, locationFilter);
    }


    View.OnClickListener startButton = v -> {
        Intent i = new Intent(MainActivity.this, Possition.class);
        startService(i);
    };

    View.OnClickListener stopButton = v -> {
        Intent i = new Intent(MainActivity.this, Possition.class);
        stopService(i);
    };

}