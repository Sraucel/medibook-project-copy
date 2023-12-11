package com.example.medibook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserPendingInterface extends AppCompatActivity{

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pending_interface);



        createViews();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mAuth.signOut();
                Intent intent = new Intent(UserPendingInterface.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }



    public void createViews(){
        backButton = findViewById(R.id.pendingBackButton);


    }
}
