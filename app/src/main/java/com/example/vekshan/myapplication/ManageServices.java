package com.example.vekshan.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageServices extends AppCompatActivity implements View.OnClickListener{

    private EditText edit_txt_ServiceName;
    private EditText edit_txt_ServicePrice;
    private Button btnAdd;
    private Button btnDelete;
    private DatabaseReference dataServices;

    private ListView listViewServices;

    private List<Service> serviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);
        //Database
        dataServices = FirebaseDatabase.getInstance().getReference("Service");

        //Initializing Views
        edit_txt_ServiceName = findViewById(R.id.edit_txt_ServiceName);
        edit_txt_ServicePrice = findViewById(R.id.edit_txt_ServicePrice);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);

        //Setting Button Listeners
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        //Creating List for Services
        listViewServices = findViewById(R.id.servicesList);
        serviceList = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {

        if (view == btnAdd){

            addService();

        } else if (view == btnDelete){

            //deleteService();

        }

    }

    private void updateService(String id, String name, double price){

    }

    private void deleteService(String id){

    }

    private void addService() {

        String serviceName = edit_txt_ServiceName.getText().toString().trim();
        double servicePrice = Double.parseDouble(String.valueOf(edit_txt_ServicePrice.getText().toString()));

        if (!TextUtils.isEmpty((serviceName))) {

            String id = dataServices.push().getKey();

            Service service = new Service(id, serviceName, servicePrice);

            dataServices.child(id).setValue(service);

            //edit_txt_ServiceName.setText("");
            //edit_txt_ServicePrice.setText("");

            Toast.makeText(this, "Service Added", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(this, "Please enter a service", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        dataServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceList.clear();

                for(DataSnapshot serviceSnapshot: dataSnapshot.getChildren()){
                    Service service = serviceSnapshot.getValue(Service.class);

                    serviceList.add(service);

                }

                ListOfServices servicesListAdapter = new ListOfServices(ManageServices.this, serviceList);
                listViewServices.setAdapter(servicesListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
