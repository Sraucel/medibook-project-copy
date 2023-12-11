package com.example.medibook;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AdminRejectedListViewHolder extends RecyclerView.ViewHolder {
    RelativeLayout relativeLayout;

    TextView firstNameView,LastnameView,emailView,phoneNumberView,addressView,statusView;

    public AdminRejectedListViewHolder(View itemView){
        super(itemView);
        relativeLayout = itemView.findViewById(R.id.rejectUserBox);
        firstNameView = itemView.findViewById(R.id.rejectedUserFirstName);
        LastnameView = itemView.findViewById(R.id.rejectedUserLastName);
        emailView = itemView.findViewById(R.id.rejectedUserEmail);
        phoneNumberView = itemView.findViewById(R.id.rejectedUserPhoneNum);
        addressView = itemView.findViewById(R.id.rejectedUserAddress);
        statusView = itemView.findViewById(R.id.rejectedUserStatus);


    }
}
