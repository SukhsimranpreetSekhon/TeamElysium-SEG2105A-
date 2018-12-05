package com.example.vekshan.myapplication;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
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
import java.util.ServiceConfigurationError;

public class SearchScreen extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerWeekdays;
    private Spinner spinnerTimeSlots;
    private ArrayAdapter<CharSequence> adapterWeekdays;
    private ArrayAdapter<CharSequence> adapterTimeSlots;
    private HashMap<String,Integer> map;
    private DatabaseReference dataServiceProv;
    private DatabaseReference dataHomeOwner;
    private DatabaseReference dataBooking;
    private Button btnSearchByAvailability;
    private List<ServiceProvider> provList;
    private List<Service> serviceList;
    private ListView listViewProv;
    private ServiceProvider prov;


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
        dataHomeOwner =FirebaseDatabase.getInstance().getReference("HomeOwner");

        btnSearchByAvailability= findViewById(R.id.btnSearch);
        btnSearchByAvailability.setOnClickListener(this);

        listViewProv = findViewById(R.id.listViewProv);

        provList = new ArrayList<>();
        serviceList = new ArrayList<>();

        //Long Click Listener
        listViewProv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                prov = provList.get(position);
                final String provId = prov.getId();
                dataServiceProv.child(provId).child("Services").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        serviceList.clear();
                        if(!dataSnapshot.exists()){
                            Toast.makeText(SearchScreen.this, "No Services offered!", Toast.LENGTH_LONG).show();
                        }else{
                            for(DataSnapshot serviceSnapshot: dataSnapshot.getChildren()) {
                                Service service = serviceSnapshot.getValue(Service.class);
                                serviceList.add(service);
                                openBookingDialog();
                            }
                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return true;
            }
        });



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


    private void openBookingDialog(){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.booking_dialog, null);

        final AlertDialog bookingDialog = new AlertDialog.Builder(this).create();
        bookingDialog.setView(view);
        bookingDialog.setTitle("com.example.vekshan.myapplication.Booking Service:");
        bookingDialog.show();

        final TextView text_view_info = view.findViewById(R.id.text_view_info);
        final ListView listViewServices = view.findViewById(R.id.listViewServices);

        ListOfServices serviceAdapter = new ListOfServices(SearchScreen.this,serviceList);
        listViewServices.setAdapter(serviceAdapter);

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = serviceList.get(position);
                openConfirmationDialog(service.getServiceName());
                return true;
            }
        });

    }

    private void openConfirmationDialog(String serviceName){

       AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this);
       confirmationDialog.setTitle("com.example.vekshan.myapplication.Booking Confirmation");
       confirmationDialog.setMessage("Is this the correct booking you want?");
       confirmationDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dataHomeOwner.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ServiceProviders").child(prov.getId()).setValue(prov).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           //create new com.example.vekshan.myapplication.Booking and add to database
                           Toast.makeText(getApplicationContext(), "Booked with Service Provider", Toast.LENGTH_SHORT).show();
                       }else{
                           Toast.makeText(getApplicationContext(), "com.example.vekshan.myapplication.Booking cannot be done at this time!", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       });
       confirmationDialog.setNegativeButton("Cancel", null).show();


    }

}
