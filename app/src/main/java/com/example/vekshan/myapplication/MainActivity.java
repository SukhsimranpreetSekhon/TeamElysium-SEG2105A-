package com.example.vekshan.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogIn;
    private EditText edit_txt_Email;
    private EditText edit_txt_Password;
    private TextView txt_view_Register;
    private FirebaseAuth firebaseAuth;
    private Spinner spinnerChoice;
    private ArrayAdapter<CharSequence> adapter;
    private DatabaseReference data;
    private ValueEventListener listener1;
    private ValueEventListener listener2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();


        //Intializing views
        btnLogIn = findViewById(R.id.btnLogIn);
        txt_view_Register =findViewById(R.id.txt_view_Register);

        edit_txt_Email= findViewById(R.id.edit_txt_Email);
        edit_txt_Password = findViewById(R.id.edit_txt_Password);
        spinnerChoice =findViewById(R.id.spinnerChoice);


        //spinner
        adapter =ArrayAdapter.createFromResource(this,R.array.accountTypes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoice.setAdapter(adapter);

        //Setting listeners
        btnLogIn.setOnClickListener(this);
        txt_view_Register.setOnClickListener(this);
        data = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onClick(View v) {
        if ( v== btnLogIn){
            login();
        }

        if( v == txt_view_Register){
            startActivity(new Intent(this, Register.class));
            finish();
        }

    }

    private void loginFailed(){
        Toast.makeText(MainActivity.this,"Log in Failed! Please try again!", Toast.LENGTH_SHORT).show();
    }


    private void login() {
        final String email = edit_txt_Email.getText().toString().trim();
        final String password = edit_txt_Password.getText().toString().trim();
        final String accountType = spinnerChoice.getSelectedItem().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_txt_Email.setError("Please enter a valid email");
            edit_txt_Email.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length()<6){
            edit_txt_Password.setError("Minimum length of password should be 6");
        }


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    data = data.child(accountType).child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    //implement code to see required login screen
                  listener1 = data.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                    Toast.makeText(MainActivity.this,"Successfully logged in!", Toast.LENGTH_SHORT).show();
                                  listener2 = data.child("firstName").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String firstName = dataSnapshot.getValue(String.class);

                                            Intent intent;
                                            if (accountType.equals("Administrator")){
                                                intent = new Intent(getApplicationContext(), ManageServices.class);
                                            }else if(accountType.equals("ServiceProvider")){ //HomeOwner or ServiceProvider
                                                intent = new Intent(getApplicationContext(), ProviderScreen.class);
                                            }else{
                                                intent = new Intent(getApplicationContext(), WelcomeScreenActivity.class);
                                            }

                                            intent.putExtra("role",accountType);
                                            intent.putExtra("name",firstName);
                                            finish(); //finish this activity before opening a new one
                                            startActivity(intent);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                            } else {
                                loginFailed();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }else{
                    loginFailed();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listener1 != null && listener2 != null){
            data.removeEventListener(listener1);
            data.removeEventListener(listener2);
        }

    }
}
