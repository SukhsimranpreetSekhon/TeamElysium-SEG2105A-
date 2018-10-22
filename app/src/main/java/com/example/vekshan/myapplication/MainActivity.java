package com.example.vekshan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Button btnLogIn;
    private Button btnSignup;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Spinner spinnerChoice;
    private ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intializing views
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignup = findViewById(R.id.btnSignUp);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        spinnerChoice =findViewById(R.id.spinnerChoice);

        adapter =ArrayAdapter.createFromResource(this,R.array.accountTypes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoice.setAdapter(adapter);

        //Setting listeners
        btnLogIn.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        spinnerChoice.setOnItemSelectedListener(this);






    }

    @Override
    public void onClick(View view){
        if (view == btnSignup){
            createUser();
        }
        if (view == btnLogIn) {

        }
    }

    private void createUser() {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String accountType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
