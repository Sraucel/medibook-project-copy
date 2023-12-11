package com.example.medibook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medibook.classes.Administrator;
import com.example.medibook.classes.User;
import com.example.medibook.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    protected static DatabaseReference userRef = database.getReference("Users");
    protected static DatabaseReference registrationRef = database.getReference("Registered");
    protected static DatabaseReference shiftRef = database.getReference("Shifts");
    protected static DatabaseReference appointmentRef = database.getReference("Appointments");

    protected static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    protected static FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnDoc, btnPatient, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth.signOut();
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);


        createViews();

        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PatientRegisterActivity.class);
                startActivity(intent);
            }
        });


        btnDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DoctorRegisterActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });


    }



    private void createViews(){
        btnDoc = findViewById(R.id.asDoctor);
        btnPatient = findViewById(R.id.asPatient);
        btnSignIn = findViewById(R.id.signInButton);
    }
}