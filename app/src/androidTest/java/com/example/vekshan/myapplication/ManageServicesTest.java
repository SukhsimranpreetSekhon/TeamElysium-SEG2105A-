package com.example.vekshan.myapplication;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ManageServicesTest {

    @Rule
    public ActivityTestRule<ManageServices> myServiceTestRule = new ActivityTestRule<ManageServices>(ManageServices.class);
    private ManageServices myServices = null;

    private TextView serviceName;
    private TextView servicePrice;
    private Button addButton;

    private TextView serviceUpdatedName;
    private TextView serviceUpdatedPrice;
    private Button updateButton;


    @Before
    public void setUp() throws Exception{
        myServices = myServiceTestRule.getActivity();
    }

    //Add Success
    @Test
    @UiThreadTest
    public void testServiceAdd1() throws Exception{

        serviceName = myServices.findViewById(R.id.edit_txt_ServiceName);
        servicePrice = myServices.findViewById(R.id.edit_txt_ServicePrice);
        serviceName.setText("Renovation");
        servicePrice.setText("50");

        addButton = myServices.findViewById(R.id.btnAdd);
        addButton.performClick();
    }

    //Add Fail - Not adding service
    @Test
    @UiThreadTest
    public void testServiceAdd2() throws Exception{

        serviceName = myServices.findViewById(R.id.edit_txt_ServiceName);
        servicePrice = myServices.findViewById(R.id.edit_txt_ServicePrice);
        serviceName.setText("");
        servicePrice.setText("50");

        addButton = myServices.findViewById(R.id.btnAdd);
        addButton.performClick();
    }

    //Add Fail - Not adding price
    @Test
    @UiThreadTest
    public void testServiceAdd3() throws Exception{

        serviceName = myServices.findViewById(R.id.edit_txt_ServiceName);
        servicePrice = myServices.findViewById(R.id.edit_txt_ServicePrice);
        serviceName.setText("Renovation ");
        servicePrice.setText("");

        addButton = myServices.findViewById(R.id.btnAdd);
        addButton.performClick();
    }
}



