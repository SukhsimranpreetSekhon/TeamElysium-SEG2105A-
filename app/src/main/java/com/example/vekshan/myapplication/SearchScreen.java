package com.example.vekshan.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchScreen extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerWeekdays;
    private Spinner spinnerTimeSlots;
    private ArrayAdapter<CharSequence> adapterWeekdays;
    private ArrayAdapter<CharSequence> adapterTimeSlots;
    private HashMap<String,Integer> map;
    private DatabaseReference dataServiceProv;
    private Button btnSearchByAvailability;
    private List<ServiceProvider> provList;
    private ListView listViewProv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        //Initializing Views
        spinnerWeekdays = findViewById(R.id.spinnerWeekdays);
        spinnerTimeSlots = findViewById(R.id.spinnerTimeSlots);

        //Spinner for Weekdays
        adapterWeekdays = ArrayAdapter.createFromResource(this, R.array.weekdays, android.R.layout.simple_spinner_item);
        adapterWeekdays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeekdays.setAdapter(adapterWeekdays);

        //Spinner for Time Slots
        adapterTimeSlots = ArrayAdapter.createFromResource(this, R.array.time_slots, android.R.layout.simple_spinner_item);
        adapterTimeSlots.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeSlots.setAdapter(adapterTimeSlots);

        //hashmap for id
        map = new HashMap<>();
        map.put("Monday",0);
        map.put("Tuesday",1);
        map.put("Wednesday",2);
        map.put("Thursday",3);
        map.put("Friday",4);
        map.put("Saturday",5);
        map.put("Sunday",6);

        //DatabaseReference
        dataServiceProv = FirebaseDatabase.getInstance().getReference("ServiceProvider");

        btnSearchByAvailability= findViewById(R.id.btnSearch);
        btnSearchByAvailability.setOnClickListener(this);

        listViewProv = findViewById(R.id.listViewProv);

        provList = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        if(v == btnSearchByAvailability){
            provList.clear();
            String dayOfTheWeek = spinnerWeekdays.getSelectedItem().toString().trim();
            String timeslot = spinnerTimeSlots.getSelectedItem().toString().trim();

            final String id = map.get(dayOfTheWeek)+ timeslot;
            dataServiceProv.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot provSnapshot: dataSnapshot.getChildren()){
                        if(provSnapshot.child("Availabilities").child(id).exists()){
                            String provId =provSnapshot.child("id").getValue(String.class);
                            String provName = provSnapshot.child("firstName").getValue(String.class);
                            String provPhone= provSnapshot.child("phoneNumber").getValue(String.class);
                            ServiceProvider prov = new ServiceProvider(provId,provName,provPhone);
                            provList.add(prov);
                            ListOfServiceProviders provAdapter =new ListOfServiceProviders(SearchScreen.this,provList);
                            listViewProv.setAdapter(provAdapter);
                            /*if(provList.size()> 0){
                                Toast.makeText(SearchScreen.this, provList.get(0).getId(), Toast.LENGTH_SHORT).show();
                            }*/

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
