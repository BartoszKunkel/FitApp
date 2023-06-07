package com.example.fitapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.fitapp.Fragments.DataBaseHelper;
import com.example.fitapp.Model.ProfileModel;

public class ActivityChose extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProfileModel profile;
        DataBaseHelper db = new DataBaseHelper(this);
        profile = db.getProfile();
        if(profile.getAge() != 0 && profile.getWeight() != 0) startActivity(new Intent(this, MainActivity.class));
        else startActivity(new Intent(this,ProfileEdit.class));
        finish();
    }



    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;

// Sprawdź, czy uprawnienie jest przydzielone


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Uprawnienie zostało przyznane, wykonaj odpowiednie operacje
                // związane z usługą lokalizacji
            } else {
                // Uprawnienie zostało odrzucone, możesz podjąć odpowiednie działania
            }
        }
    }
    //Po wykonaniu tych kroków, twoja aplikacja będzie prosić o dostęp do usługi lokalizacji, jeśli jeszcze nie jest ona przydzielona. Jeśli użytkownik zatwierdzi prośbę, będziesz mógł kontynuować wykonywanie operacji związanych z usługą lokalizacji w swojej aplikacji. Jeśli uprawnienie zostanie odrzucone, możesz podjąć odpowiednie działania, takie jak wyświetlenie komunikatu lub zastosowanie alternatywnych rozwiązań.








}