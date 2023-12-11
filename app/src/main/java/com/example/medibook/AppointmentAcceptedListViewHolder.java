package com.example.medibook;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AppointmentAcceptedListViewHolder extends RecyclerView.ViewHolder {
    RelativeLayout relativeLayout;
    TextView shiftIdView, startTimeView, endTimeView, patientUidView, statusView;
    Button cancelBtn, infoBtn;

    Button buttonAppointmentAcceptedList1;

    public AppointmentAcceptedListViewHolder(View itemView) {
        super(itemView);
        relativeLayout = itemView.findViewById(R.id.acceptedAppointment);

        patientUidView = itemView.findViewById(R.id.acceptedPatientUid);
        startTimeView = itemView.findViewById(R.id.acceptedStartTime);
        endTimeView = itemView.findViewById(R.id.acceptedEndTime);
        shiftIdView = itemView.findViewById(R.id.acceptedShiftId);
        statusView = itemView.findViewById(R.id.acceptedStatus);

        infoBtn = itemView.findViewById(R.id.acceptInfo);
        cancelBtn = itemView.findViewById(R.id.appointmentCancelBtn);
        buttonAppointmentAcceptedList1 = itemView.findViewById(R.id.buttonAppointmentAcceptedList1);

    }
}
