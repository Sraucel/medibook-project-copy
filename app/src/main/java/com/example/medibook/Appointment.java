package com.example.medibook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Appointment {
    private String doctorShiftId;
    private String patientUid;
    private String startTime;
    private String endTime;
    private String status; //"New", "Accepted", "Rejected", "Cancelled"
    private String uid;

    private String date;

    private String shiftId;

    private String rating;

    public Appointment() {

    }

    public Appointment(String patientUid, String doctorShiftId, String date, String startTime, String endTime, String status, String uid, String shiftId, String rating) {
        this.patientUid = patientUid;
        this.doctorShiftId = doctorShiftId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.uid = uid;
        this.date = date;
        this.shiftId = shiftId;
        this.rating = rating;
    }

    // Getters and Setters
    public String getDoctorShiftId() {
        return doctorShiftId;
    }

    public void setDoctorShiftId(String appointmentId) {
        this.doctorShiftId = appointmentId;
    }

    public String getPatientUid() {
        return patientUid;
    }

    public void setPatientUid(String patientUid) {
        this.patientUid = patientUid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() { return uid; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
