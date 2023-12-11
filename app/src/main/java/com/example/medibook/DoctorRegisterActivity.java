package com.example.medibook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medibook.classes.Doctor;
import com.example.medibook.classes.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoctorRegisterActivity extends AppCompatActivity {
    public static User Doctor;
    private EditText editFirstName, editLastName, editEmail, editPassword, editPhoneNumber, editAddress, editHealthEmployeeNumber, editSpecialties;
    private TextView txtFirstName, txtLastName, txtEmail, txtPassword, txtPhoneNumber, txtAddress, txtEmployeeNumber, txtSpecialties;
    private Button registerDoctor,clickBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);


        createViews();


        registerDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRegister(); // check correct format before register
            }
        });

        clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorRegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        MainActivity.mAuthListener = firebaseAuth -> {
            FirebaseUser current = firebaseAuth.getCurrentUser();
            if (current != null) {
                Doctor doctor = new Doctor(editFirstName.getText().toString(), editLastName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString(), editPhoneNumber.getText().toString(), editAddress.getText().toString(), "pending", editHealthEmployeeNumber.getText().toString(), editSpecialties.getText().toString(), current.getUid());
                MainActivity.registrationRef.child(current.getUid()).setValue(doctor);
                Intent intent = new Intent(DoctorRegisterActivity.this, UserPendingInterface.class);
                startActivity(intent);
            }

        };
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.mAuth.addAuthStateListener(MainActivity.mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (MainActivity.mAuthListener != null) {
            MainActivity.mAuth.removeAuthStateListener(MainActivity.mAuthListener);
        }
    }


    public void createViews() {
        editFirstName = findViewById(R.id.firstName);
        editLastName = findViewById(R.id.lastName);
        editEmail = findViewById(R.id.emailAddress);
        editPassword = findViewById(R.id.editTextTextPassword);
        editPhoneNumber = findViewById(R.id.phoneNumber);
        editAddress = findViewById(R.id.address);
        editHealthEmployeeNumber = findViewById(R.id.employeeNumber);
        editSpecialties = findViewById(R.id.specialties);

        txtFirstName = findViewById(R.id.textWarnFirstName);
        txtLastName = findViewById(R.id.textWarnLastName);
        txtEmail = findViewById(R.id.textWarnEmail);
        txtPassword = findViewById(R.id.textWarnPassword);
        txtPhoneNumber = findViewById(R.id.textWarnPhoneNumber);
        txtAddress = findViewById(R.id.textWarnAddress);
        txtEmployeeNumber = findViewById(R.id.textWarnEmployeeNumber);
        txtSpecialties = findViewById(R.id.textWarnSpecialties);


        registerDoctor = findViewById(R.id.registerPatientButton);
        clickBack = findViewById(R.id.registerDoctorButtonClickBackward);


    }


    public void initRegister() {
        if (validateData()) {

            storeUser();
            snackBar();


        }


    }


    public boolean validateData() {
        boolean val = true;
        if (editFirstName.getText().toString().equals("")) {
            txtFirstName.setVisibility(View.VISIBLE);
            val = false;
        }
        else
            txtFirstName.setVisibility(View.GONE);

        if (editLastName.getText().toString().equals("")) {
            txtLastName.setVisibility(View.VISIBLE);
            val = false;
        }
        else
            txtLastName.setVisibility(View.GONE);

        if (editEmail.getText().toString().equals("") || !editEmail.getText().toString().contains("@")) {
            txtEmail.setVisibility(View.VISIBLE);
            val = false;
        }
        else
            txtEmail.setVisibility(View.GONE);

        if (editPassword.getText().toString().equals("") || editPassword.getText().toString().length() < 8) {
            txtPassword.setVisibility(View.VISIBLE);
            val = false;
        }
        else
            txtPassword.setVisibility(View.GONE);

        if (editPhoneNumber.getText().toString().equals("")) {
            txtPhoneNumber.setVisibility(View.VISIBLE);
            val = false;
        }
        else
            txtPhoneNumber.setVisibility(View.GONE);

        if (editAddress.getText().toString().equals("")) {
            txtAddress.setVisibility(View.VISIBLE);
            val = false;
        }
        else
            txtAddress.setVisibility(View.GONE);

        if (editHealthEmployeeNumber.getText().toString().equals("")) {
            txtEmployeeNumber.setVisibility(View.VISIBLE);
            val = false;
        }
        else
            txtEmployeeNumber.setVisibility(View.GONE);

        if (editSpecialties.getText().toString().equals("family medicine") || editSpecialties.getText().toString().equals("internal medicine") || editSpecialties.getText().toString().equals("pediatrics") || editSpecialties.getText().toString().equals("obstetrics") || editSpecialties.getText().toString().equals("gynecology")) {
            txtSpecialties.setVisibility(View.GONE);

        }
        else {
            txtSpecialties.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Your specialty must be one of - family medicine, internal medicine, pediatrics", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "obstetrics or gynecology",Toast.LENGTH_LONG).show();
            val = false;
        }
        return val;
    }


    public void snackBar() {
        txtFirstName.setVisibility(View.GONE);
        txtLastName.setVisibility(View.GONE);
        txtEmail.setVisibility(View.GONE);
        txtPassword.setVisibility(View.GONE);
        txtPhoneNumber.setVisibility(View.GONE);
        txtAddress.setVisibility(View.GONE);
        txtEmployeeNumber.setVisibility(View.GONE);
        txtSpecialties.setVisibility(View.GONE);


        View rootLayout = findViewById(R.id.patientLayout);
        Snackbar.make(rootLayout, "Registered successfully", Snackbar.LENGTH_SHORT).show();

    }


    public void storeUser() {

        MainActivity.mAuth.createUserWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString());

    }
}