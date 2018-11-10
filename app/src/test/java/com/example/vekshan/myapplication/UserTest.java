package com.example.vekshan.myapplication;

import static org.junit.Assert.*;
import org.junit.Test;

public class UserTest {

    @Test
    public void checkFirstName(){
        User user = new User("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertNotEquals("Check the first name of the user", "Harman", user.getFirstName());
    }

    @Test
    public void checkLastName(){
        User user = new User("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertEquals("Check the last name of the user", "Sekhon", user.getLastName());
    }

    @Test
    public void checkEmail(){
        User user = new User("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        assertEquals("Check the email of the user", "ssekh066@uottawa.ca", user.getEmail());
    }

}
