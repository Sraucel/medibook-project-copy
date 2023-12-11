package com.example.medibook;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AppointmentInboxViewHolder extends RecyclerView.ViewHolder {
    RelativeLayout relativeLayout;
    TextView shiftIdView, startTimeView, endTimeView, patientUidView,statusView;
    Button acceptBtn, rejectBtn, infoBtn;

    public AppointmentInboxViewHolder(View itemView) {
        super(itemView);
        relativeLayout = itemView.findViewById(R.id.appointmentInbox);

        patientUidView = itemView.findViewById(R.id.appointmentPatientUid);
        startTimeView = itemView.findViewById(R.id.appointmentStartTime);
        endTimeView = itemView.findViewById(R.id.appointmentEndTime);
        shiftIdView = itemView.findViewById(R.id.appointmentShiftId);
        statusView = itemView.findViewById(R.id.appointmentStatus);

        infoBtn = itemView.findViewById(R.id.info);
        acceptBtn = itemView.findViewById(R.id.appointmentAcceptBtn);
        rejectBtn = itemView.findViewById(R.id.appointmentRejectBtn);

    }
}
