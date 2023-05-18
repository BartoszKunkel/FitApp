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
    public double latitude, longitude, distance, totalDistance, step = 0.85;

    LocationManager PosManager;

    public Possition() {}

    public class ServerConnection extends Binder{
        Possition getPos() {return Possition.this;}
    }

    private final ServerConnection connection = new ServerConnection();

    public IBinder onBind(Intent intent){
        PosManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            PosManager.requestLocationUpdates("gps", 1000, 2, listener);
        } catch(SecurityException se){
            Log.v("Inaccessible", "Problem with Security");
        }
        return connection;
    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
          possition = location;
          if(lastPossition == null){lastPossition = possition;}
          latitude = location.getLatitude();
          longitude = location.getLongitude();
          distance = location.distanceTo(lastPossition);
          totalDistance += distance;
          lastPossition = location;
          }
    };

    public String getResult(){
        String result = "\nszerokość " +
                changeToDegrees(true, latitude) + "\ndługość  " +
                changeToDegrees(false, longitude) + "\nodległość " +
                String.format(Locale.UK, "%10.1f", distance) + "\ncałkowita " +
                String.format("%10.1f", totalDistance) + "\nliczba kroków " +
                String.format("%06d", (int) (totalDistance / step));
        return result;
    }

    public String changeToDegrees(boolean phi, double x)
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
