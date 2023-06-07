package com.example.fitapp.Fragments;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

import com.example.fitapp.Activities.MainActivity;

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
                if(MainActivity.going) secs++;
                else secs = 0;
                handler.postDelayed(this,1000);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }






}
