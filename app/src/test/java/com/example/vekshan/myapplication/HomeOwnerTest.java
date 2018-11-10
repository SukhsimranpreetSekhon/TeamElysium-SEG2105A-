package com.example.vekshan.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HomeOwnerTest {
    @Test
    public void checkFirstName(){
        HomeOwner homeOwner = new HomeOwner("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertNotEquals("Check the first name of the homeOwner", "Harman", homeOwner.getFirstName());
    }

    @Test
    public void checkLastName(){
        HomeOwner homeOwner = new HomeOwner("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertEquals("Check the last name of the homeOwner", "Sekhon", homeOwner.getLastName());
    }

    @Test
    public void checkEmail(){
        HomeOwner homeOwner = new HomeOwner("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertEquals("Check the email of the homeOwner", "ssekh066@uottawa.ca", homeOwner.getEmail());
    }

}
