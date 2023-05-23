package com.example.fitapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Possition extends Service{

    public Location possition = null, lastPossition = null;
    public static double latitude, longitude, distance,dbDistance, totalDistance, step = 0.85;

    LocationManager PosManager;
    LocationListener listener;
    public Possition() {}


    public IBinder onBind(Intent intent){
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        PosManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            listener = location -> {
                possition = location;
            if(MainActivity.going ) {
                if (lastPossition == null) {
                    lastPossition = possition;

                }
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                distance = location.distanceTo(lastPossition);
                totalDistance += distance;
                dbDistance += distance;
                if(dbDistance > 500){
                    String a = latitude + " " + longitude;
                    MainActivity.tour.add(a);
                    dbDistance = 0;
                    Log.v("Added", "Added distance to object");
                }
            }
            Intent i = new Intent("location_update");
            i.putExtra("possition", possition);
            i.putExtra("latitude", location.getLatitude());
            i.putExtra("longitude", location.getLongitude());
            if(MainActivity.going){
                i.putExtra("distance", (double)location.distanceTo(lastPossition));
                i.putExtra("totalDisance",totalDistance);
            }
            i.putExtra("location", getResult());
            sendBroadcast(i);

            lastPossition = location;

        };
        try {
            PosManager.requestLocationUpdates("gps", 1000, 0, listener);
            Log.v("Access granted", "Access granted");
        } catch(SecurityException se){
            Log.v("Inaccessible", "Problem with Security");
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

        String result = "Your activity:" +
                "\nLatitude: " +
                Possition.changeToDegrees(true, latitude) + "\nLongitude:  " +
                Possition.changeToDegrees(false, longitude) + "\nDistance (from last maesure point): " +
                String.format(Locale.UK, "%10.1f", distance) + "\nTotal distance: " +
                String.format("%10.1f", totalDistance) + "\nSteps: " +
                String.format("%06d", (int) (totalDistance / step));
        return result;
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
