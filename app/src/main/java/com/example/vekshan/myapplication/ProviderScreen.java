package com.example.vekshan.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProviderScreen extends AppCompatActivity implements View.OnClickListener{
    private TextView txt_view_Role;
    private String role;
    private String name;
    private Button btnAddServices;
    private Button btnLogout;
    private Button btnServicesAvailability;
    private Button btnCompleteProfile;
    private DatabaseReference data;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_screen);

        btnLogout = findViewById(R.id.btnLogout);
        btnAddServices =findViewById(R.id.btnAddServices);
        btnServicesAvailability = findViewById(R.id.btnServicesAvailability);
        btnCompleteProfile = findViewById(R.id.btnCompleteProfile);
        //Setting listeners
        btnLogout.setOnClickListener(this);
        btnAddServices.setOnClickListener(this);
        btnServicesAvailability.setOnClickListener(this);
        btnCompleteProfile.setOnClickListener(this);

        txt_view_Role = findViewById(R.id.txt_view_Role);
        Intent intent = getIntent();
        role = intent.getStringExtra("role");
        name = intent.getStringExtra("name");

        String msg = name + " : logged in as " + role;

        txt_view_Role.setText(msg);
        data = FirebaseDatabase.getInstance().getReference("ServiceProvider").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listener != null) {
            data.removeEventListener(listener);
        }
    }

    @Override
    public void onClick(View v) {

        if(v == btnLogout) {
            startActivity(new Intent(this, MainActivity.class));
            //finish();

        }

        if (v == btnCompleteProfile){
            startActivity(new Intent(this, CompleteProviderProfile.class));
            //finish();
        }

        if(v == btnAddServices ) {
            listener = data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        Toast.makeText(ProviderScreen.this, "Complete Profile first!", Toast.LENGTH_SHORT).show();
                        data.removeEventListener(listener);

                    }else{
                        startActivity(new Intent(getApplicationContext(), AddServices.class));
                        //finish();


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        if(v == btnServicesAvailability ) {
            listener = data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        Toast.makeText(ProviderScreen.this, "Complete Profile first!", Toast.LENGTH_SHORT).show();
                        data.removeEventListener(listener);

                    }else{
                        startActivity(new Intent(getApplicationContext(), AddAvailability.class));
                        //finish();


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        /*if(v == btnLogout) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        if(v== btnAddServices) {
            startActivity(new Intent(this, AddServices.class));
            finish();
        }
        if (v == btnServicesAvailability){
            startActivity(new Intent(this, AddAvailability.class));
            finish();
        }

        if (v == btnCompleteProfile){
            startActivity(new Intent(this, CompleteProviderProfile.class));
            //finish();
        }*/


    }
}
