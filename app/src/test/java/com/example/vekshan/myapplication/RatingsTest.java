package com.example.vekshan.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RatingsTest {

    @Test
    public void checkRatingsScore1(){
        ServiceProvider provider = new ServiceProvider("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        provider.setRating("5");
        assertNotEquals("Check the ratings score for the Service Provider", "4", provider.getRating());
    }

    @Test
    public void checkRatingsScore2(){
        ServiceProvider provider = new ServiceProvider("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        provider.setRating("5");
        assertEquals("Check the ratings score for the Service Provider", "5", provider.getRating());
    }

    @Test
    public void checkRatingsComment1(){
        ServiceProvider provider = new ServiceProvider("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        provider.setComment("Good Job!");
        assertNotEquals("Check the ratings comment for the Service Provider", "Nice Job!", provider.getComment());
    }

    @Test
    public void checkRatingsComment2(){
        ServiceProvider provider = new ServiceProvider("Sukhu", "Sekhon", "ssekh066@uottawa.ca", "6476462021");
        provider.setComment("Good Job!");
        assertEquals("Check the ratings comment for the Service Provider", "Good Job!", provider.getComment());
    }
}
