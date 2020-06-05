package com.atreyasinha.appointd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private Button mRegister;
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public FirebaseFirestore fireStoreDb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mRegister = (Button) findViewById(R.id.register);

        register();
    }

    private void register() {
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = SignUpActivity.userName;
                final String type = SignUpActivity.userType;
                final String city = Location.userCity;
                final String state = Location.userState;
                final String country = Location.userCountry;
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Error! Please Try Again.", Toast.LENGTH_SHORT).show();
                        } else {
                            String doctorId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference userName = currentUser.child("Users").child("Doctors").child("Name");
                            DatabaseReference userType = currentUser.child("Users").child("Doctors").child("Type");
                            DatabaseReference userCity = currentUser.child("Users").child("Doctors").child("City");
                            DatabaseReference userEmail = currentUser.child("Users").child("Doctors").child("Email");


                            currentUser.setValue(true);
                            userName.setValue(name);
                            userType.setValue(type);
                            userCity.setValue(city);
                            userEmail.setValue(email);

                            Map<String, Object> doctors = new HashMap<>();
                            doctors.put("name", name);
                            doctors.put("doctor_type", type);
                            doctors.put("city", city);
                            doctors.put("state", state);
                            doctors.put("country", country);
                            doctors.put("email_id", email);
                            doctors.put("url", "https://firebasestorage.googleapis.com/v0/b/appointd-183bd.appspot.com/o/profilepics%2Fprofile_doctor.png?alt=media&token=b2551332-eea6-4b45-897f-7a39472920aa");

                            Client client = new Client("9P30FM2JJO", "173121ad220ad04d02d230cfd3cf40c0");
                            Index index = client.getIndex("doctors");

                            List<JSONObject> search_array = new ArrayList<JSONObject>();

                            try {
                                search_array.add(
                                        new JSONObject(doctors).put("objectID", doctorId)
                                );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            index.addObjectsAsync(new JSONArray(search_array), null);

                            fireStoreDb.collection("Country").document(country).
                                    collection("State").document(state).
                                    collection("City").document(city).
                                    collection("Doctors").document(doctorId).
                                    set(doctors);

                            fireStoreDb.collection("Doctors").document(doctorId).set(doctors).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(Register.this, Profile.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}