package com.example.medibook;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DoctorShiftTest {

    private DoctorShift doctorShift;

    @Before
    public void setUp() {
        doctorShift = new DoctorShift();
    }

    @Test
    public void testDateSetterAndGetter() {
        String testDate = "04012023";
        doctorShift.setDate(testDate);
        assertEquals(testDate, doctorShift.getDate());
    }

    @Test
    public void testStartTimeSetterAndGetter() {
        String startTime = "08:00";
        doctorShift.setStartTime(startTime);
        assertEquals(startTime, doctorShift.getStartTime());
    }

    @Test
    public void testEndTimeSetterAndGetter() {
        String endTime = "16:00";
        doctorShift.setEndTime(endTime);
        assertEquals(endTime, doctorShift.getEndTime());
    }

    @Test
    public void testDoctorIdSetterAndGetter() {
        String doctorId = "doc123";
        doctorShift.setDoctorID(doctorId);
        assertEquals(doctorId, doctorShift.getDoctorID());
    }

    @Test
    public void testUidSetterAndGetter() {
        String uid = "uid123";
        doctorShift.setUid(uid);
        assertEquals(uid, doctorShift.getUid());
    }

    @Test
    public void testDayOfMonthSetter() {
        String dayOfMonth = "15";
        doctorShift.setDayOfMonth(dayOfMonth);
        assertEquals(dayOfMonth, doctorShift.dayOfMonth);
    }


}

