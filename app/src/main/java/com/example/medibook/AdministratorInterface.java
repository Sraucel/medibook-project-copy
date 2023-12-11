package com.example.medibook;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class AdministratorInterface extends AppCompatActivity {

    private Button logOffBtn,inboxBtn,rejectedBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_interface);


        createViews();

        logOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mAuth.signOut();
                Intent intent = new Intent(AdministratorInterface.this,MainActivity.class);
                startActivity(intent);
            }
        });

        inboxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorInterface.this,AdminInbox.class); //Change to the inbox class
                startActivity(intent);
            }
        });
        rejectedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ButtonClicked", "rejectedBtn clicked");
                Intent intent = new Intent(AdministratorInterface.this,AdminRejectedList.class); //Change to the rejected file class
                startActivity(intent);
            }
        });


    }



    public void createViews(
    ){
        logOffBtn = findViewById(R.id.logOutAsAdministrator);
        inboxBtn = findViewById(R.id.inboxButton);
        rejectedBtn = findViewById(R.id.rejectButton);

    }
}
