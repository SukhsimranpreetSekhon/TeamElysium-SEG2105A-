package com.example.vekshan.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

import com.google.android.gms.common.internal.AccountType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Register extends AppCompatActivity implements View.OnClickListener{
    private TextView txt_view_Login;
    private Button btnRegister;
    private EditText edit_txt_Email;
    private EditText edit_txt_Password;
    private EditText edit_txt_FirstName;
    private EditText edit_txt_LastName;
    private EditText edit_txt_PhoneNumber;
    private Spinner spinnerChoice;
    private ArrayAdapter<CharSequence> adapter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dataAdmin;
    private DatabaseReference dataHomeOwner;
    private DatabaseReference dataServiceProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        dataAdmin = FirebaseDatabase.getInstance().getReference("Administrator");
        dataHomeOwner = FirebaseDatabase.getInstance().getReference("HomeOwner");
        dataServiceProvider=FirebaseDatabase.getInstance().getReference("ServiceProvider");

        //Intializing views
        btnRegister = findViewById(R.id.btnRegister);
        edit_txt_Email = findViewById(R.id.edit_txt_Email);
        edit_txt_Password = findViewById(R.id.edit_txt_Password);
        edit_txt_FirstName = findViewById(R.id.edit_txt_FirstName);
        edit_txt_LastName = findViewById(R.id.edit_txt_LastName);
        edit_txt_PhoneNumber = findViewById(R.id.edit_txt_PhoneNumber);
        spinnerChoice =findViewById(R.id.spinnerChoice);
        txt_view_Login =findViewById(R.id.txt_view_Login);


        //spinner
        adapter =ArrayAdapter.createFromResource(this,R.array.accountTypes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoice.setAdapter(adapter);

        //Setting listeners
        btnRegister.setOnClickListener(this);
        txt_view_Login.setOnClickListener(this);

    }


    private void register() {
        final String email = edit_txt_Email.getText().toString().trim();
        final String password = edit_txt_Password.getText().toString().trim();
        final String firstName = edit_txt_FirstName.getText().toString().trim();
        final String lastName = edit_txt_LastName.getText().toString().trim();
        final String phoneNumber = edit_txt_PhoneNumber.getText().toString().trim().replaceAll(" ", "");
        final String accountType = spinnerChoice.getSelectedItem().toString().trim();


      if(TextUtils.isEmpty(email)){
            Toast.makeText(Register.this, "Email field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_txt_Email.setError("Please enter a valid email");
            edit_txt_Email.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(Register.this, "Password field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length()<8){
            edit_txt_Password.setError("Minimum length of password should be 8");
        }

        if(TextUtils.isEmpty(firstName)){
            Toast.makeText(Register.this, "First Name field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(lastName)){
            Toast.makeText(Register.this, "Last Name field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(Register.this, "Phone Number field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }


        if(!(phoneNumber.length() == 10)){
          Toast.makeText(Register.this, "Invalid Canadian Phone Number", Toast.LENGTH_SHORT).show();
        }
        for(int i = 0; i<phoneNumber.length(); i++) {
          if(!Character.isDigit(phoneNumber.charAt(i))){
              Toast.makeText(Register.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
          }
        }

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(accountType.equals("Administrator")){

                       DatabaseReference data = FirebaseDatabase.getInstance().getReference().child(accountType);
                       data.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               if (!dataSnapshot.exists()) {
                                   Administrator admin = new Administrator(firstName, lastName, email, phoneNumber);

                                   dataAdmin.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if (task.isSuccessful()) {
                                               Toast.makeText(Register.this, "Registration Complete!", Toast.LENGTH_SHORT).show();
                                               { //use this as message below, vb
                                               }
                                               Intent intent = new Intent(getApplicationContext(), WelcomeScreenActivity.class);
                                               intent.putExtra("role",accountType);
                                               intent.putExtra("name",firstName);
                                               finish(); //finish this activity before opening a new one
                                               startActivity(intent);
                                           } else {
                                               Toast.makeText(Register.this, "Registration Failed! Please try again!", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   });
                               }else{
                                   Toast.makeText(Register.this, "Admin account already created!", Toast.LENGTH_LONG).show();
                               }

                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });

                    }

                    if(accountType.equals("ServiceProvider")){
                        ServiceProvider serviceProvider = new ServiceProvider(firstName, lastName, email, phoneNumber);

                        dataServiceProvider.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(serviceProvider).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task){
                                if(task.isSuccessful()) {
                                    Toast.makeText(Register.this,"Registration Complete",Toast.LENGTH_LONG).show();{
                                    }

                                    //open the Welcome screen
                                    Intent intent = new Intent(getApplicationContext(), ProviderScreen.class);
                                    intent.putExtra("role",accountType);
                                    intent.putExtra("name",firstName);
                                    finish(); //finish this activity before opening a new one
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Register.this,"Registration Failed! Please try again!", Toast.LENGTH_SHORT).show(); //update string.xml if you want to use res..., vb
                                }
                            }
                        });

                    }

                    if(accountType.equals("HomeOwner")){
                        HomeOwner homeowner = new HomeOwner(firstName,lastName,email,phoneNumber);

                        dataHomeOwner.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(homeowner).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task){
                                if(task.isSuccessful()) {
                                    Toast.makeText(Register.this,"Registration Complete",Toast.LENGTH_LONG).show();{
                                    }
                                    Intent intent = new Intent(getApplicationContext(), WelcomeScreenActivity.class);
                                    intent.putExtra("role",accountType);
                                    intent.putExtra("name",firstName);
                                    finish(); //finish this activity before opening a new one
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Register.this,"Registration Failed! Please try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }



                }else{
                    Toast.makeText(Register.this,"Registration Failed! Please try again!" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == txt_view_Login) {
            startActivity(new Intent(this, MainActivity.class));
        }
        if (v == btnRegister) {
            register();
        }
    }
}
