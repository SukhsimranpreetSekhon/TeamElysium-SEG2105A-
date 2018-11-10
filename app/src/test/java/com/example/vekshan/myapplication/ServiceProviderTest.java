package com.example.vekshan.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ServiceProviderTest {
    @Test
    public void checkFirstName(){
        ServiceProvider provider = new ServiceProvider("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertNotEquals("Check the first name of the ServiceProvider", "Harman", provider.getFirstName());
    }

    @Test
    public void checkLastName(){
        ServiceProvider provider = new ServiceProvider("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertEquals("Check the last name of the ServiceProvider", "Sekhon", provider.getLastName());
    }

    @Test
    public void checkEmail(){
        ServiceProvider provider = new ServiceProvider("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertEquals("Check the email of the ServiceProvider", "ssekh066@uottawa.ca", provider.getEmail());
    }
}
