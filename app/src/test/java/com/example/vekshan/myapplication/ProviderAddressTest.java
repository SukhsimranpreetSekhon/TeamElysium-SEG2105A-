package com.example.vekshan.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ProviderAddressTest {

    @Test
    public void checkBuildingNumber(){

        ProviderAddress profile = new ProviderAddress("800", "King Edward Avenue", "Ottawa", "Ontario", "K1N6N5");
        assertEquals("Check the building number of the service provider", "800", profile.getBuildingNum());
    }

    @Test
    public void checkStreetName(){

        ProviderAddress profile = new ProviderAddress("800", "King Edward Avenue", "Ottawa", "Ontario", "K1N6N5");
        assertEquals("Check the street name of the service provider", "King Edward Avenue", profile.getStreetName());
    }

    @Test
    public void checkCity(){

        ProviderAddress profile = new ProviderAddress("800", "King Edward Avenue", "Ottawa", "Ontario", "K1N6N5");
        assertNotEquals("Check the city of the service provider", "Toronto", profile.getCity());
    }

    @Test
    public void checkPostalCode(){

        ProviderAddress profile = new ProviderAddress("800", "King Edward Avenue", "Ottawa", "Ontario", "K1N6N5");
        assertNotEquals("Check the postal code of the service provider", "K1N0C3", profile.getPostalCode());
    }

}
