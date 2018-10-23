package com.example.vekshan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intializing views
        btnLogIn = findViewById(R.id.btnLogIn);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        //Setting listeners
        btnLogIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);






    }

    @Override
    public void onClick(View view){
        if (view == btnLogIn){

        }
        if (view == textViewSignUp) {
            startActivity(new Intent(this, Register.class));
        }
    }




}
