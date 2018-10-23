package com.example.vekshan.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private TextView textViewLogin;
    private Button btnRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhoneNumber;
    private Spinner spinnerChoice;
    private ArrayAdapter<CharSequence> adapter;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        //Intializing views
        btnRegister = findViewById(R.id.btnRegister);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        spinnerChoice =findViewById(R.id.spinnerChoice);
        textViewLogin =findViewById(R.id.textViewLogin);

        adapter =ArrayAdapter.createFromResource(this,R.array.accountTypes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoice.setAdapter(adapter);

        //Setting listeners
        btnRegister.setOnClickListener(this);
        spinnerChoice.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == btnRegister){
            register();
        }
        if (view == textViewLogin) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void register() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Register.this,"Registration Complete!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Register.this,"Registration Failed! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
