package com.example.medibook;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class AdminInboxViewHolder extends RecyclerView.ViewHolder {
    RelativeLayout relativeLayout;
    TextView firstNameView, lastNameView, emailView, phoneNumberView, addressView, statusView;

    public AdminInboxViewHolder(View itemView){
        super(itemView);
        relativeLayout = itemView.findViewById(R.id.userBox);
        firstNameView = itemView.findViewById(R.id.inboxUserFirstName);
        lastNameView = itemView.findViewById(R.id.inboxUserLastName);
        emailView = itemView.findViewById(R.id.inboxUserEmail);
        phoneNumberView = itemView.findViewById(R.id.inboxUserPhoneNum);
        addressView = itemView.findViewById(R.id.inboxUserAddress);
        statusView = itemView.findViewById(R.id.inboxUserStatus);
    }

}
