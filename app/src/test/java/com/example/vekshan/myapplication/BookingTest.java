package com.example.vekshan.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BookingTest {

    @Test
    public void checkServiceName1(){
        Availability time = new Availability("Monday", "8:00-10:00");
        Booking booked = new Booking("Accounting", "Sukhu", "1000", time, "Colton");
        assertNotEquals("Check the service name for the booking", "Financial Planner", booked.getServiceName());
    }

    @Test
    public void checkServiceName2(){
        Availability time = new Availability("Friday", "12:00-14:00");
        Booking booked = new Booking("Lawn Mowing", "Vekshan", "2000", time, "Kabir");
        assertEquals("Check the service name for the booking", "Lawn Mowing", booked.getServiceName());
    }

    @Test
    public void checkProviderName1(){
        Availability time = new Availability("Monday", "8:00-10:00");
        Booking booked = new Booking("Accounting", "Sukhu", "1000", time, "Colton");
        assertNotEquals("Check the service name for the booking", "Harman", booked.getProviderName());
    }

    @Test
    public void checkProviderName2(){
        Availability time = new Availability("Friday", "12:00-14:00");
        Booking booked = new Booking("Lawn Mowing", "Vekshan", "2000", time, "Kabir");
        assertEquals("Check the service name for the booking", "Vekshan", booked.getProviderName());
    }

    @Test
    public void checkBookingId1(){
        Availability time = new Availability("Monday", "8:00-10:00");
        Booking booked = new Booking("Accounting", "Sukhu", "1000", time, "Colton");
        assertNotEquals("Check the service name for the booking", "1348", booked.getBookingId());
    }

    @Test
    public void checkBookingId2(){
        Availability time = new Availability("Friday", "12:00-14:00");
        Booking booked = new Booking("Lawn Mowing", "Vekshan", "2000", time, "Kabir");
        assertEquals("Check the service name for the booking", "2000", booked.getBookingId());
    }

    @Test
    public void checkHomeownerName1(){
        Availability time = new Availability("Monday", "8:00-10:00");
        Booking booked = new Booking("Accounting", "Sukhu", "1000", time, "Colton");
        assertNotEquals("Check the service name for the booking", "Steven", booked.getHomeOwnerName());
    }

    @Test
    public void checkHomeownerName2(){
        Availability time = new Availability("Friday", "12:00-14:00");
        Booking booked = new Booking("Lawn Mowing", "Vekshan", "2000", time, "Kabir");
        assertEquals("Check the service name for the booking", "Kabir", booked.getHomeOwnerName());
    }
}
