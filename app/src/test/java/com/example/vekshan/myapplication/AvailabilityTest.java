package com.example.vekshan.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AvailabilityTest {

    @Test
    public void checkTimeSlot(){
        Availability available = new Availability("monday", "8:00 - 10:00");
        assertEquals("Check the available time slot of the service provider", "8:00 - 10:00", available.getTimeslot());
    }

    @Test
    public void checkServiceDay(){
        Availability available = new Availability("monday", "8:00 - 10:00");
        assertNotEquals("Check the available day of the service provider", "friday", available.getDay());
    }

}
