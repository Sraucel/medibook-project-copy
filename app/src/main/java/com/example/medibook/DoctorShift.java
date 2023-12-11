package com.example.medibook;

public class DoctorShift {
    private String date;
    private String startTime;
    private String endTime;
    private String doctorID;

    public String year;
    public String month;
    public String dayOfMonth;
    public String specialty;
    private String uid;

    private String status; // new, Booked



    public DoctorShift(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public DoctorShift(String date, String startTime, String endTime, String specialty, String uid, String status, String doctorID) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.specialty = specialty;
        this.uid = uid;
        this.status = status;
        this.doctorID = doctorID;
    }


    public DoctorShift(){

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}