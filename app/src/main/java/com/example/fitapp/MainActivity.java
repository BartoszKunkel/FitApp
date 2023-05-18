package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public boolean binded;
    private Possition sPossition;

    private final ServiceConnection connect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Possition.ServerConnection sC = (Possition.ServerConnection) service;
            sPossition = sC.getPos();
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takeRoute();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent PossInt = new Intent(this, Possition.class);
        bindService(PossInt, connect, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(binded)
        {
            unbindService(connect);
            binded = false;
        }
    }

    private void takeRoute()
    {
        final TextView route = findViewById(R.id.route);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(binded == true) {
                    route.setText(sPossition.getResult());
                }
                handler.postDelayed(this, 1000);
                }
        });
    }


}