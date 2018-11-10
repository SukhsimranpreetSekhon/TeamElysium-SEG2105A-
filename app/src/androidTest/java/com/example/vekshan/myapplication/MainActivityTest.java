package com.example.vekshan.myapplication;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> myActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity myActivity = null;
    private TextView adminEmail;
    private TextView adminPass;
    private Spinner spinnerAdmin;
    private TextView homeEmail;
    private TextView homePass;
    private Spinner spinnerHome;
    private TextView provEmail;
    private TextView provPass;
    private Spinner spinnerProv;

    private Button button;


    @Before
    public void setUp() throws Exception{
        myActivity = myActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkAdminLogin() throws Exception{
        //assertNotNull(myActivity.findViewById(R.id.edit_txt_Email));
        adminEmail=myActivity.findViewById(R.id.edit_txt_Email);
        adminPass=myActivity.findViewById(R.id.edit_txt_Password);
        spinnerAdmin=myActivity.findViewById(R.id.spinnerChoice);
        spinnerAdmin.setSelection(2);
        adminPass.setText("password");
        adminEmail.setText("admin@admin.com");
        assertEquals("password",adminPass.getText().toString());
        assertEquals("admin@admin.com",adminEmail.getText().toString());

        /*
        try {
            assertNotEquals("password",adminPass.getText().toString());
            //Toast.makeText(myActivity,"login failed", Toast.LENGTH_LONG).show();
        }
        catch(AssertionError e){
            //Toast.makeText(myActivity,"login success", Toast.LENGTH_LONG).show();
        }
        try {
            assertNotEquals("admin@admin.com",adminEmail.getText().toString());
            //Toast.makeText(myActivity,"login failed", Toast.LENGTH_LONG).show();
        }
        catch(AssertionError e){
            //Toast.makeText(myActivity,"login success", Toast.LENGTH_LONG).show();
        }
        */

        button = myActivity.findViewById((R.id.btnLogIn));

        button.performClick();

    }


    @Test
    @UiThreadTest
    public void checkHomeownerLogin() throws Exception{
        //assertNotNull(myActivity.findViewById(R.id.edit_txt_Email));
        homeEmail=myActivity.findViewById(R.id.edit_txt_Email);
        homePass=myActivity.findViewById(R.id.edit_txt_Password);
        spinnerHome=myActivity.findViewById(R.id.spinnerChoice);
        spinnerHome.setSelection(0);
        homePass.setText("password");
        homeEmail.setText("home@home.com");
        assertEquals("password",homePass.getText().toString());
        assertEquals("home@home.com",homeEmail.getText().toString());

        /*
        try {
            assertNotEquals("password",homePass.getText().toString());
            Toast.makeText(myActivity,"login failed", Toast.LENGTH_LONG).show();
        }
        catch(AssertionError e){
            Toast.makeText(myActivity,"login success", Toast.LENGTH_LONG).show();
        }
        try {
            assertNotEquals("home@home.com",homeEmail.getText().toString());
            Toast.makeText(myActivity,"login failed", Toast.LENGTH_LONG).show();
        }
        catch(AssertionError e){
            Toast.makeText(myActivity,"login success", Toast.LENGTH_LONG).show();
        }
        */
    }

    @Test
    @UiThreadTest
    public void checkServiceProviderLogin() throws Exception{
        //assertNotNull(myActivity.findViewById(R.id.edit_txt_Email));
        provEmail=myActivity.findViewById(R.id.edit_txt_Email);
        provPass=myActivity.findViewById(R.id.edit_txt_Password);
        spinnerProv=myActivity.findViewById(R.id.spinnerChoice);
        spinnerProv.setSelection(1);
        provPass.setText("password");
        provEmail.setText("prov@prov.com");
        assertEquals("password",provPass.getText().toString());
        assertEquals("prov@prov.com",provEmail.getText().toString());

        /*
        try {
            assertNotEquals("password",provPass.getText().toString());
            Toast.makeText(myActivity,"login failed", Toast.LENGTH_LONG).show();
        }
        catch(AssertionError e){
            Toast.makeText(myActivity,"login success", Toast.LENGTH_LONG).show();
        }
        try {
            assertNotEquals("prov@prov.com",provEmail.getText().toString());
            Toast.makeText(myActivity,"login failed", Toast.LENGTH_LONG).show();
        }
        catch(AssertionError e){
            Toast.makeText(myActivity,"login success", Toast.LENGTH_LONG).show();
        }
        */
    }


}
