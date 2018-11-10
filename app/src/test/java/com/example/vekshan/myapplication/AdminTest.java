package com.example.vekshan.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AdminTest {

    @Test
    public void checkFirstName(){
        Administrator admin = new Administrator("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertNotEquals("Check the first name of the Admin", "Harman", admin.getFirstName());
    }

    @Test
    public void checkLastName(){
        Administrator admin = new Administrator("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertEquals("Check the last name of the Admin", "Sekhon", admin.getLastName());
    }

    @Test
    public void checkEmail(){
        Administrator admin = new Administrator("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertEquals("Check the email of the Admin", "ssekh066@uottawa.ca", admin.getEmail());
    }
}
