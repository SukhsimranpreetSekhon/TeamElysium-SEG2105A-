package com.example.vekshan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeScreenActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView txt_view_Role;
    private String role;
    private String name;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        btnLogout = findViewById(R.id.btnLogout);
        //Setting listeners
        btnLogout.setOnClickListener(this);

        txt_view_Role = findViewById(R.id.txt_view_Role);
        Intent intent = getIntent();
        role = intent.getStringExtra("role");
        name = intent.getStringExtra("name");

        String msg = name + " : logged in as " + role;

       txt_view_Role.setText(msg);

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
