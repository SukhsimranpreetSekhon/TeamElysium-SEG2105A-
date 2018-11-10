package com.example.vekshan.myapplication;

import static org.junit.Assert.*;
import org.junit.Test;

public class ServiceTest {

    @Test
    public void checkServiceName(){
        Service aService = new Service("1", "Electrician", 120);
        assertEquals("Check the name of the service", "Electrician", aService.getServiceName());
    }

    @Test
    public void checkServiceId(){
        Service aService = new Service("1", "Electrician", 120);
        assertEquals("Check the id of the product", "1", aService.getServiceId());
    }

    @Test
    public void checkServicePrice(){
        Service aService = new Service("1", "Electrician", 120);
        assertNotEquals("Check the price of the service", "180.0", Double.toString(aService.getServicePrice()));
    }

}
