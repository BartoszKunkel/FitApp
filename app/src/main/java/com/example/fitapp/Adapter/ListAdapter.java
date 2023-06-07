package com.example.fitapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitapp.Activities.MainActivity;
import com.example.fitapp.Fragments.DataBaseHelper;
import com.example.fitapp.Fragments.KcalCalculator;
import com.example.fitapp.Fragments.Position;
import com.example.fitapp.Model.LocationModel;
import com.example.fitapp.Model.ProfileModel;
import com.example.fitapp.R;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    Activity mActivity;
    List<LocationModel> locationList;
    ProfileModel profile = new ProfileModel();
    DataBaseHelper db;
    public ListAdapter(Activity activity) {
        mActivity = activity;
        db = new DataBaseHelper(activity);
        locationList = db.getActivity();
        profile = db.getProfile();
    }

    @Override
    public int getCount() {
        return locationList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        View activityOneLine;

        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        activityOneLine = inflater.inflate(R.layout.activity_one_line, parent ,false);

        //Finding all layout elements
        TextView type = activityOneLine.findViewById(R.id.type);
        TextView startDate = activityOneLine.findViewById(R.id.startDate);
        TextView endDate = activityOneLine.findViewById(R.id.endDate);
        TextView firstPos = activityOneLine.findViewById(R.id.firstPos);
        TextView lastPos = activityOneLine.findViewById(R.id.lastPos);
        TextView totalDist = activityOneLine.findViewById(R.id.totalDistance);
        TextView kcal = activityOneLine.findViewById(R.id.kcal);
        TextView steps = activityOneLine.findViewById(R.id.steps);
        TextView time = activityOneLine.findViewById(R.id.timeShow);
        Button show = activityOneLine.findViewById(R.id.show);
        Button delete = activityOneLine.findViewById(R.id.delete);


        //Getting values to format them later
        LocationModel location = (LocationModel) this.getItem(position);
        Double totalDistance = location.getDistance();
        Double stepsTotal = location.getDistance()/0.85;
        Double burnedKcal = KcalCalculator.calcKcal(getTime(location.getTime()),location.getDistance(),location.getType(), profile);
        String totalTime = location.getTime();


        //Getting latitude and longitude to proper format
        String firstPosition = location.getFirstPosition();
        String lastPosition = location.getLastPosition();
        String[] splitedFirst = firstPosition.split(" ");
        String[] splitedLast = lastPosition.split(" ");

        if(numCheck(splitedFirst[0])) splitedFirst[0] = Position.changeToDegrees(true, Double.parseDouble(splitedFirst[0]));

        if(numCheck(splitedFirst[1])) splitedFirst[1] = Position.changeToDegrees(false, Double.parseDouble(splitedLast[1]));

        if(numCheck(splitedLast[0]))splitedLast[0] = Position.changeToDegrees(true, Double.parseDouble(splitedLast[0]));

        if(numCheck(splitedLast[1])) splitedLast[1] = Position.changeToDegrees(false, Double.parseDouble(splitedLast[1]));

        String[] types = mActivity.getResources().getStringArray(R.array.spinnerItems);
        //Setting values for TextViews
        type.setText(types[location.getType()]);
        startDate.setText(location.getInitTime());
        endDate.setText(location.getEndTime());
        firstPosition = splitedFirst[0] + " " + splitedFirst[1];
        lastPosition = splitedLast[0] + " " + splitedLast[1];
        firstPos.setText(firstPosition);
        lastPos.setText(lastPosition);
        totalDist.setText(String.format("%10.1f", totalDistance));
        steps.setText(String.format("%10.1f", stepsTotal));
        time.setText(location.getTime());
        kcal.setText(String.format("%10.1f", burnedKcal));


        //Creating listeners
        View.OnClickListener deleteListener = v ->
        {
            LocationModel deleteLocation = (LocationModel) getItem(position);
            db.deleteActivity(deleteLocation);
            MainActivity.showListView(mActivity);

        };
        delete.setOnClickListener(deleteListener);

        return activityOneLine;
    }

    private int getTime(String together)
    {
        String[] divided = together.split(":");
        int h = 3600 * Integer.parseInt(divided[0]);
        int m =  60 *Integer.parseInt(divided[1]);
        int s = Integer.parseInt(divided[2]);
        return h+m+s;
    }

    private boolean numCheck(String str)
    {
        try
        {
            Double.parseDouble(str);
            if(Double.parseDouble(str) != 0.0) {
                return true;
            }
            else return false;

        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }


}
