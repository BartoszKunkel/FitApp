package com.example.fitapp.Fragments;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.fitapp.Activities.MainActivity;

import java.util.Locale;

public class Position extends Service{
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    public Location position = null, lastPosition = null;
    public static double latitude, longitude, distance,dbDistance, totalDistance, step = 0.85;
    public static boolean working = true;
    LocationManager PosManager;
    LocationListener listener;

    public Position() {}


    public IBinder onBind(Intent intent){
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        PosManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.v("Work","ServiceIsWorking");
        listener = location -> {
            position = location;
            if(MainActivity.going ) {
                if (lastPosition == null) {
                    lastPosition = position;
                }
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                distance = location.distanceTo(lastPosition);
                totalDistance += distance;
                dbDistance += distance;
                if(dbDistance > 500){
                    String a = latitude + " " + longitude;
                    MainActivity.tour.add(a);
                    dbDistance = 0;
                    Log.v("Added", "Added distance to object");
                }}

            Intent i = new Intent("location_update");
            i.putExtra("position", position);
            i.putExtra("latitude", location.getLatitude());
            i.putExtra("longitude", location.getLongitude());
            if(MainActivity.going){
                i.putExtra("distance", (double)location.distanceTo(lastPosition));
                i.putExtra("totalDistance",totalDistance);
            }
            else
            {
                distance = 0;
                totalDistance = 0;
                dbDistance = 0;
            }
            i.putExtra("location", getResult());
            sendBroadcast(i);
            lastPosition = location;
        };

        try {
            PosManager.requestLocationUpdates("gps", 1000, 0, listener);
            Log.v("Access granted", "Access granted");
        } catch(SecurityException se){
            Log.v("Inaccessible", "Problem with Security");
            working = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(PosManager != null){
            //noinspection MissingPermission
            PosManager.removeUpdates(listener);
        }
    }


    public String getResult(){
        return "Your activity:" +
                "\nLatitude: " +
                Position.changeToDegrees(true, latitude) + "\nLongitude:  " +
                Position.changeToDegrees(false, longitude) + "\nDistance (from last maesure point): " +
                String.format(Locale.UK, "%10.1f", distance) + "\nTotal distance: " +
                String.format("%10.1f", totalDistance) + "\nSteps: " +
                String.format("%06d", (int) (totalDistance / step));
    }

    public static String changeToDegrees(boolean phi, double x)
    {
        int sign = (int)Math.signum(x);
        double abs = Math.abs(x);
        int degrees = (int)abs;
        int min = (int)((abs-(int)abs)*60);
        double minW = ((abs-(int)abs)*60);
        double sec = 60 * (minW % min);
        char a = 176;
        String changed;
        if(phi)
        {
            if(sign == 1)
                changed = String.format("N %02d" +a+"%02d'%04.1f\"", degrees,min,sec);
            else
                changed = String.format("S %02d" +a+"%02d'%04.1f\"", degrees,min,sec);
        }
        else
        {
            if(sign == 1)
                changed = String.format("E %02d" +a+"%02d'%04.1f\"", degrees,min,sec);
            else
                changed = String.format("W %02d" +a+"%02d'%04.1f\"", degrees,min,sec);
        }
        return changed;
    }


}