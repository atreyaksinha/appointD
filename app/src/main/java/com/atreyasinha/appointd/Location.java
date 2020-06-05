package com.atreyasinha.appointd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Location extends AppCompatActivity {

    private EditText mCountry, mState, mCity;
    private Button mNext;
    public static String userCountry, userState, userCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mCountry = (EditText) findViewById(R.id.country);
        mState = (EditText) findViewById(R.id.state);
        mCity = (EditText) findViewById(R.id.city);
        mNext = (Button) findViewById(R.id.next);

        registerPage();
        saveUserInformation();
    }

    private void saveUserInformation() {
        userCountry = mCountry.getText().toString();
        userState = mState.getText().toString();
        userCity = mCity.getText().toString();
    }

    private void registerPage() {
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Location.this, Register.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

}