package com.example.medibook;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppointmentTest {

    private Appointment appointment;
    private final String doctorShiftId = "shift123";
    private final String patientUid = "patient123";

    private final String date = "12092023";

    private final String startTime = "10:00";

    private final String endTime = "11:00";
    private final String status = "new";
    private final String uid = "appointment123";

    private final String shiftID = "shift123";

    private final String rating = "0.0";

    @Before
    public void setUp() {
        appointment = new Appointment(patientUid, doctorShiftId, date, startTime, endTime, status, uid, shiftID, rating);
    }

    @Test
    public void testGetters() {
        assertEquals(doctorShiftId, appointment.getDoctorShiftId());
        assertEquals(patientUid, appointment.getPatientUid());
        assertEquals(startTime, appointment.getStartTime());
        assertEquals(endTime, appointment.getEndTime());
        assertEquals(status, appointment.getStatus());
        assertEquals(uid, appointment.getUid());
        assertEquals(date, appointment.getDate());
        assertEquals(shiftID, appointment.getShiftId());
        assertEquals(rating, appointment.getRating());
    }

    @Test
    public void testSetters() {
        String newDoctorShiftId = "shift456";
        String newPatientUid = "patient456";
        String newDate = "12122023";
        String newStartTime = "12:00";
        String newEndTime = "13:00";
        String newStatus = "Accepted";
        String newUid = "appointment456";
        String newRating = "4.0";

        appointment.setDoctorShiftId(newDoctorShiftId);
        appointment.setPatientUid(newPatientUid);
        appointment.setStartTime(newStartTime);
        appointment.setEndTime(newEndTime);
        appointment.setStatus(newStatus);
        appointment.setRating(newRating);
        appointment.setDate(newDate);

        assertEquals(newDoctorShiftId, appointment.getDoctorShiftId());
        assertEquals(newPatientUid, appointment.getPatientUid());
        assertEquals(newStartTime, appointment.getStartTime());
        assertEquals(newEndTime, appointment.getEndTime());
        assertEquals(newStatus, appointment.getStatus());
        assertEquals(newDate, appointment.getDate());
        assertEquals(newRating, appointment.getRating());

    }
}
