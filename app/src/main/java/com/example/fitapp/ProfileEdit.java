package com.example.fitapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileEdit extends AppCompatActivity {

    private boolean isMale;
    private String age, weight;

    private EditText et_age;
    private EditText et_weight;
    private DataBaseHelper db;
    private ProfileModel profile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Button cancel = findViewById(R.id.cancel);
        Button confirm = findViewById(R.id.comfirm);
        Switch gender = findViewById(R.id.switch1);
        et_age = findViewById(R.id.age);
        et_weight = findViewById(R.id.weight);
        cancel.setOnClickListener(cancelListener);
        confirm.setOnClickListener(confirmButton);
        db = new DataBaseHelper(ProfileEdit.this);
        profile = db.getProfile();

        if(profile.getId_profile() == 0)
        {
            et_age.setText(Integer.toString(profile.getAge()));
            et_weight.setText(Double.toString(profile.getWeight()));
            gender.setChecked(profile.isMale());
        }


        gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) isMale = true;
                else isMale = false;
            }
        });
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
        else {Toast.makeText(ProfileEdit.this, "Text fields can't be empty", Toast.LENGTH_SHORT).show();}
        if(!numCheck(age) || !numCheck(weight)) Toast.makeText(ProfileEdit.this,"Make sure that your Weight is a number and Age is total number",Toast.LENGTH_SHORT).show();
        else {
            profile.setMale(isMale);
            profile.setAge(Integer.parseInt(age));
            profile.setWeight(Double.parseDouble(weight));
            if (profile.getId_profile() == 1) {
                db.updateProfile(profile);
            } else {
                db.addProfile(profile);
            }
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

}
