package com.example.medibook;

public class Booking {
    private String date;
    private String startTime;
    private String endTime;
    private String doctorID;

    public String dayOfMonth;
    public String specialty;
    private String uid;





    public Booking(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Booking(String date, String startTime, String endTime, String specialty, String doctorID, String uid) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.specialty = specialty;
        this.doctorID = doctorID;
        this.uid = uid;
    }


    public Booking(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getDoctorID () {
        return doctorID;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setDoctorID(String id) {
        this.doctorID = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSpecialty(){
        return specialty;
    }
}