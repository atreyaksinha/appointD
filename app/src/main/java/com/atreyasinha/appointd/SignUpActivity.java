package com.atreyasinha.appointd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText mName, mType;
    private Button mSignIn, mNext;
    public static String userName, userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mName = (EditText) findViewById(R.id.name);
        mType = (EditText) findViewById(R.id.doctor_type);
        mSignIn = (Button) findViewById(R.id.signIn);
        mNext = (Button) findViewById(R.id.next);

        signIn();
        locationPage();
        saveUserInformation();
    }

    private void saveUserInformation() {
        userName = mName.getText().toString();
        userType = mType.getText().toString();
    }

    private void locationPage() {
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, Location.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    private void signIn() {
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}