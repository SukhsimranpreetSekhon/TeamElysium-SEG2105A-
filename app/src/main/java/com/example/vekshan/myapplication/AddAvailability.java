package com.example.vekshan.myapplication;

import android.support.annotation.NonNull;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class AddAvailability extends AppCompatActivity {
    private Spinner spinnerWeekdays;
    private Spinner spinnerTimeSlots;

    private Button btnSaveTimeSlot;

    private TextView txt_view_ListOfAvailability;
    private TextView txt_view_weekdayChoice;
    private TextView txt_view_timeslotChoice;

    private ArrayAdapter<CharSequence> adapterWeekdays;
    private ArrayAdapter<CharSequence> adapterTimeSlots;

    private ListView listViewAvailability;
    private List<Availability> availabilityList;
    
    private DatabaseReference dataServiceProv;

    private HashMap<String,Integer> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_availability);

        //Initializing Views
        spinnerWeekdays = findViewById(R.id.spinnerWeekdays);
        spinnerTimeSlots = findViewById(R.id.spinnerTimeSlots);

        btnSaveTimeSlot = findViewById(R.id.btnSaveTimeSlot);
        txt_view_ListOfAvailability = findViewById(R.id.txt_view_ListOfAvailability);
        txt_view_timeslotChoice = findViewById(R.id.txt_view_timeslotChoice);
        txt_view_weekdayChoice = findViewById(R.id.txt_view_weekdayChoice);


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



        //Setting Button Listeners
        btnSaveTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAvailability();
            }
        });

        //Creating List for Services Availability
        listViewAvailability = findViewById(R.id.availability_list);
        availabilityList = new ArrayList<>();

        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Reference to database
        dataServiceProv = FirebaseDatabase.getInstance().getReference("ServiceProvider").child(id).child("Availabilities");

        listViewAvailability.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Availability availability =  availabilityList.get((position));
                openAvailabilityDialog(availability.getId(),availability.getDay(),availability.getTimeslot());
                return true;
            }
        });
    }

   @Override
    protected void onStart() {
        super.onStart();
        dataServiceProv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                availabilityList.clear();

                for(DataSnapshot availabilitySnapshot: dataSnapshot.getChildren()) {

                    Availability availability = availabilitySnapshot.getValue(Availability.class);

                    availabilityList.add(availability);

                }

                ListOfAvailabilities availabilitiesAdapter = new ListOfAvailabilities(AddAvailability.this, availabilityList);
                listViewAvailability.setAdapter(availabilitiesAdapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void openAvailabilityDialog(final String id, final String day, final String timeslot){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view   = layoutInflater.inflate(R.layout.availability_dialog,null);
        final AlertDialog availabilityDialog =new AlertDialog.Builder(this).create();
        availabilityDialog.setView(view);
        availabilityDialog.setTitle("Applying changes to: " + day + "-" + timeslot);
        availabilityDialog.show();

        final Spinner spinner = view.findViewById(R.id.spinnertimeslot);
        spinner.setAdapter(adapterTimeSlots);

        final Button btnUpdate = view.findViewById(R.id.btnUpdate);
        final Button btnDelete = view.findViewById(R.id.btnDelete);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String timeslot = spinner.getSelectedItem().toString().trim();
                    updateAvailability(id, day, timeslot);
                    availabilityDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAvailability(id);
                availabilityDialog.dismiss();
            }
        });



    }

    public void updateAvailability(final String id, final String day, final String timeslot){
        DatabaseReference data = dataServiceProv.child(id);
        data.removeValue();

        String newId = map.get(day)+timeslot;
        Availability availability = new Availability(newId, day, timeslot);

        dataServiceProv.child(newId).setValue(availability).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddAvailability.this, "Update Successful!", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(AddAvailability.this, "Could not update availability!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void deleteAvailability(String id){
        DatabaseReference data = dataServiceProv.child(id);
        data.removeValue();
        Toast.makeText(getApplicationContext(), "Deletion Successful!", Toast.LENGTH_LONG).show();

    }

    public void addAvailability(){

        String dayOfTheWeek = spinnerWeekdays.getSelectedItem().toString().trim();
        String timeslot = spinnerTimeSlots.getSelectedItem().toString().trim();


        String id = map.get(dayOfTheWeek)+timeslot;

        Availability availability = new Availability(id, dayOfTheWeek, timeslot);


        dataServiceProv.child(id).setValue(availability).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddAvailability.this, "Availability added!", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(AddAvailability.this, "Could not add availability!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


