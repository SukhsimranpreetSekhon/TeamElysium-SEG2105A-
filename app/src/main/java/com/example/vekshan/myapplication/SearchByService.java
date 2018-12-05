package com.example.vekshan.myapplication;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchByService extends AppCompatActivity implements View.OnClickListener{

    private Spinner spinnerWeekdays;
    private Spinner spinnerTimeSlots;
    private ArrayAdapter<CharSequence> adapterWeekdays;
    private ArrayAdapter<CharSequence> adapterTimeSlots;
    private HashMap<String,Integer> map;
    private Button btnSearchByAvailability;
    private DatabaseReference dataServiceProv;
    private String serviceId;
    private ListView listViewProv;
    private List<ServiceProvider> provList;
    private DatabaseReference dataHomeOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_service);

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

        serviceId = getIntent().getStringExtra("serviceId");
        provList = new ArrayList<>();

        btnSearchByAvailability =findViewById(R.id.btnSearch);

        btnSearchByAvailability.setOnClickListener(this);

        dataHomeOwner =FirebaseDatabase.getInstance().getReference("HomeOwner");

        listViewProv = findViewById(R.id.result_list);

        listViewProv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                openConfirmationDialog(provList.get(position));
                return true;
            }

        });

        dataServiceProv = FirebaseDatabase.getInstance().getReference("ServiceProvider");



        //hashmap for id
        map = new HashMap<>();
        map.put("Monday",0);
        map.put("Tuesday",1);
        map.put("Wednesday",2);
        map.put("Thursday",3);
        map.put("Friday",4);
        map.put("Saturday",5);
        map.put("Sunday",6);
    }

    private void openConfirmationDialog(final ServiceProvider prov){

        AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this);
        confirmationDialog.setTitle("Booking Confirmation");
        confirmationDialog.setMessage("Is this the correct booking you want?");
        confirmationDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataHomeOwner.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ServiceProviders").child(prov.getId()).setValue(prov).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //create new Booking and add to database
                            Toast.makeText(getApplicationContext(), "Booked with Service Provider", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Booking cannot be done at this time!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        confirmationDialog.setNegativeButton("Cancel", null).show();


    }


    @Override
    public void onClick(View v) {
        if (v == btnSearchByAvailability) {


            String dayOfTheWeek = spinnerWeekdays.getSelectedItem().toString().trim();
            String timeslot = spinnerTimeSlots.getSelectedItem().toString().trim();

            final String id = map.get(dayOfTheWeek) + timeslot;

            dataServiceProv.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot provSnapshot : dataSnapshot.getChildren()) {
                        if (provSnapshot.child("Availabilities").child(id).exists() && provSnapshot.child("Services").child(serviceId).exists()) {
                            String provId = provSnapshot.child("id").getValue(String.class);
                            String provName = provSnapshot.child("firstName").getValue(String.class);
                            String provPhone = provSnapshot.child("phoneNumber").getValue(String.class);
                            ServiceProvider prov = new ServiceProvider(provId, provName, provPhone);
                            provList.add(prov);
                            ListOfServiceProviders provAdapter = new ListOfServiceProviders(SearchByService.this, provList);
                            listViewProv.setAdapter(provAdapter);

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
}
