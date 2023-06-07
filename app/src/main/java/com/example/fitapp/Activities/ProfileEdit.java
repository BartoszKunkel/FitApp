package com.example.fitapp.Activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.example.fitapp.Fragments.DataBaseHelper;
import com.example.fitapp.Model.ProfileModel;
import com.example.fitapp.R;

public class ProfileEdit extends AppCompatActivity {

    public static final int LOCATION_REQUEST_CODE = 123;
    private boolean isMale;
    private String age, weight;

    private EditText et_age;
    private EditText et_weight;
    private DataBaseHelper db;
    private ProfileModel profile;
    private boolean set = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("location_permission_denied");
        editor.apply();
        permissionCheck();
        Button cancel = findViewById(R.id.cancel);
        Button confirm = findViewById(R.id.comfirm);
        Switch gender = findViewById(R.id.switch1);
        et_age = findViewById(R.id.age);
        et_weight = findViewById(R.id.weight);
        cancel.setOnClickListener(cancelListener);
        confirm.setOnClickListener(confirmButton);
        db = new DataBaseHelper(ProfileEdit.this);
        profile = db.getProfile();
        if(profile.getWeight() != 0 && profile.getAge() != 0)
        {
            et_age.setText(Integer.toString(profile.getAge()));
            et_weight.setText(Double.toString(profile.getWeight()));
            gender.setChecked(profile.isMale());
            set = true;
        }


        gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) isMale = true;
                else isMale = false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("location_permission_denied");
        editor.apply();
    }

    View.OnClickListener cancelListener = v ->
    {
        Intent i = new Intent(v.getContext(), MainActivity.class);
        startActivity(i);

    };

    View.OnClickListener confirmButton = v ->
    {
        if(!String.valueOf(et_age.getText()).isEmpty() && !String.valueOf(et_weight.getText()).isEmpty()) {
            age = String.valueOf(et_age.getText());
            weight = String.valueOf(et_weight.getText());
            profile.setMale(isMale);
            profile.setAge(Integer.parseInt(age));
            profile.setWeight(Double.parseDouble(weight));
            Boolean added = db.addProfile(profile);
            Toast.makeText(ProfileEdit.this, "Success = " + added, Toast.LENGTH_SHORT).show();
        }
        else {Toast.makeText(ProfileEdit.this, "Text fields can't be empty", Toast.LENGTH_SHORT).show();
        return;}
        if(!numCheck(age) || !numCheck(weight)) Toast.makeText(ProfileEdit.this,"Make sure that your Weight is a number and Age is total number",Toast.LENGTH_SHORT).show();
        else {
            profile.setMale(isMale);
            profile.setAge(Integer.parseInt(age));
            profile.setWeight(Double.parseDouble(weight));
            if (set = true) {
                db.updateProfile(profile);
            } else {
                db.addProfile(profile);
            }
            Intent i = new Intent(v.getContext(), MainActivity.class);
            startActivity(i);
        }
    };


    private boolean numCheck(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }


    public void permissionCheck()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
    }
    private void permissionDeniedDialog() {

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Location permission")
                    .setMessage("The application requires location permission. It cannot work without it. Are you sure you won't give the permission? Select \"Yes\" to close app. \n WARNING! If you denied permission twice, you'll must add it manualy in device settings.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            permissionCheck();
                        }
                    })
                    .create();

            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) permissionDeniedDialog();
        }
    }
}


