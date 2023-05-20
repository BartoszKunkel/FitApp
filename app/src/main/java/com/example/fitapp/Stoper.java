package com.example.fitapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Stoper extends Service{

    public static int secs;

    public Stoper() {}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("dsa", "Stoper is working");

        Intent i = new Intent("timer");
        final Handler handler = new Handler();
        handler.post(new Runnable(){
            @Override
            public void run(){
                int hours = secs/3600;
                int mins = (secs%3600)/60;
                int sec = secs%60;
                String time = String.format("%d:%02d:%02d",hours,mins,sec);

                i.putExtra("Stoper" , time);
                sendBroadcast(i);
                if(MainActivity.going == true) secs++;
                handler.postDelayed(this,1000);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }






}
