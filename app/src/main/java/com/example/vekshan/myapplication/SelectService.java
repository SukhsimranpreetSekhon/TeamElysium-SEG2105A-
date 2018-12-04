package com.example.vekshan.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectService extends AppCompatActivity {
    private EditText edit_txt_ServiceName;
    private EditText edit_txt_ServicePrice;
    private DatabaseReference dataServices;

    private ListView listViewServices;

    private List<Service> serviceList;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);

        //Database
        dataServices = FirebaseDatabase.getInstance().getReference("Service");

        //Initializing Views
        edit_txt_ServiceName = findViewById(R.id.edit_txt_ServiceName);
        edit_txt_ServicePrice = findViewById(R.id.edit_txt_ServicePrice);

        //Creating List for Services
        listViewServices = findViewById(R.id.servicesList);
        serviceList = new ArrayList<>();


        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                service = serviceList.get(position);
                openSearchScreen();
                return true;

            }
        });

    }


    private void openSearchScreen(){
        Intent intent = new Intent(getApplicationContext(),SearchByService.class);
        intent.putExtra("serviceId",service.getServiceId());
        startActivity(intent);



    }

    @Override
    protected void onStart() {
        super.onStart();

        dataServices.orderByChild("serviceName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceList.clear();

                for(DataSnapshot serviceSnapshot: dataSnapshot.getChildren()){
                    Service service = serviceSnapshot.getValue(Service.class);

                    serviceList.add(service);

                }

                ListOfServices servicesListAdapter = new ListOfServices(SelectService.this, serviceList);
                listViewServices.setAdapter(servicesListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
