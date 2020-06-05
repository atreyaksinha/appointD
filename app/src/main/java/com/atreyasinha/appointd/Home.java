package com.atreyasinha.appointd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button mDoctor, mPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDoctor = (Button) findViewById(R.id.doctor_button);
        mPatient = (Button) findViewById(R.id.patient_button);

        doctor();
        patient();
    }

    private void doctor() {
        mDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    private void patient() {
        mPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Search.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

}