package com.example.fitapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String LOCATION_TABLE = "LOCATION_TABLE";
    public static final String COLUMN_FIRST_POS = "FIRST_POS";
    public static final String COLUMN_LAST_POS = "LAST_POS";
    public static final String COLUMN_TOUR = "TOUR";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_INIT_TIME = "INIT_" + COLUMN_TIME;
    public static final String COLUMN_END_TIME = "END_" + COLUMN_TIME;
    public static final String COLUMN_DISTANCE = "DISTANCE";
    public static final String PROFILE_TABLE = "PROFILE_TABLE";
    public static final String COLUMN_WEIGHT = "WEIGHT";
    public static final String COLUMN_AGE = "AGE";
    public static final String COLUMN_ISMALE = "ISMALE";
    public static final String COLUMN_TYPE = "TYPE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "fitapp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableLocation = "CREATE TABLE " + LOCATION_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_POS + " TEXT, " + COLUMN_LAST_POS + " TEXT, " + COLUMN_TOUR + " TEXT, " + COLUMN_INIT_TIME + " TEXT, " + COLUMN_END_TIME + " TEXT, " + COLUMN_TIME + " TEXT, " + COLUMN_DISTANCE + " BLOB, " + COLUMN_TYPE + " INT)";
        String createTableProfile = "CREATE TABLE " + PROFILE_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_WEIGHT + " BLOB, " + COLUMN_AGE + " INT, " + COLUMN_ISMALE + " BOOLEAN)";
        db.execSQL(createTableLocation);
        db.execSQL(createTableProfile);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addLocation(LocationModel locationModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRST_POS, locationModel.getFirstPossition());
        cv.put(COLUMN_LAST_POS, locationModel.getLastPossition());
        cv.put(COLUMN_TOUR, locationModel.getTour());
        cv.put(COLUMN_INIT_TIME,locationModel.getInitTime());
        cv.put(COLUMN_END_TIME,locationModel.getEndTime());
        cv.put(COLUMN_TIME, locationModel.getTime());
        cv.put(COLUMN_DISTANCE,locationModel.getDistance());
        cv.put(COLUMN_TYPE,locationModel.getType());

        long insert = db.insert(LOCATION_TABLE, COLUMN_TOUR, cv);
        if(insert == -1) return false;
        else return true;
    }

    public boolean addProfile(ProfileModel profileModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WEIGHT,profileModel.getWeight());
        cv.put(COLUMN_AGE,profileModel.getAge());
        cv.put(COLUMN_ISMALE,profileModel.isMale());

        long insert = db.insert(PROFILE_TABLE, null, cv);
        if(insert == -1) return false;
        else return true;
    }

    public boolean updateProfile(ProfileModel profileModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WEIGHT, profileModel.getWeight());
        cv.put(COLUMN_AGE, profileModel.getAge());
        cv.put(COLUMN_ISMALE,profileModel.isMale());



       int rowsAffected = db.update(PROFILE_TABLE, cv, "ID = ?", new String[]{"0"});
       if(rowsAffected == 0) return false;
       else return true;

    }

    public List<LocationModel> getActivity()
        {
            List<LocationModel> returnList = new ArrayList<>();
            String query = "SELECT * FROM " + LOCATION_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    int locationID = cursor.getInt(0);
                    String firstPossition = cursor.getString(1);
                    String lastPossition = cursor.getString(2);
                    String tour = cursor.getString(3);
                    String init_time = cursor.getString(4);
                    String end_time = cursor.getString(5);
                    String time = cursor.getString(6);
                    double distance = cursor.getDouble(7);
                    int type = cursor.getInt(8);

                    LocationModel location = new LocationModel(locationID,firstPossition,lastPossition,tour,init_time,end_time,time,distance,type);
                    returnList.add(location);
                }while(cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return returnList;
        }

}
