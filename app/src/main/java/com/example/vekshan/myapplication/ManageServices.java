package com.example.vekshan.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

        //Setting Button Listeners
        btnAdd.setOnClickListener(this);

        //Creating List for Services
        listViewServices = findViewById(R.id.servicesList);
        serviceList = new ArrayList<>();

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = serviceList.get(position);
                showUpdateDialog(service.getServiceId(), service.getServiceName(),service.getServicePrice());
                return true;

            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == btnAdd){

            addService();

        }

    }

    private void updateService(String serviceId, String serviceName, double servicePrice){
        DatabaseReference data = dataServices.child(serviceId);

        Service service = new Service(serviceId,serviceName,servicePrice);
        data.setValue(service);

        Toast.makeText(getApplicationContext(), "Update Successful!", Toast.LENGTH_LONG).show();

    }

    private void deleteService(String serviceId){
        DatabaseReference data = dataServices.child(serviceId);

        data.removeValue();

        Toast.makeText(getApplicationContext(), "Deletion Successful!", Toast.LENGTH_LONG).show();

    }

    //based on lab 5
    private void showUpdateDialog(final String serviceId, final String serviceName, final double servicePrice){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText edit_txt_ServiceUpdatedName = dialogView.findViewById(R.id.edit_txt_ServiceUpdatedName);
        final EditText edit_txt_ServiceUpdatedPrice = dialogView.findViewById(R.id.edit_txt_ServiceUpdatedPrice);

        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);
        final Button btnDelete = dialogView.findViewById(R.id.btnDelete);

        String title = new String(serviceName);
        dialogBuilder.setTitle("Applying changes to: " + title);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edit_txt_ServiceUpdatedName.getText().toString().trim();
                String price_str = edit_txt_ServiceUpdatedPrice.getText().toString().trim();
                double price = Double.parseDouble((price_str));

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price_str)) { //change both name and price
                    updateService(serviceId, name, price);
                    alertDialog.dismiss();
                }else if (TextUtils.isEmpty(name) && !TextUtils.isEmpty(price_str)){ //change price only
                    updateService(serviceId,serviceName,price);
                }else {
                    updateService(serviceId,serviceName, servicePrice);
                }


            }


        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteService(serviceId);
                alertDialog.dismiss();
            }
        });



    }

    private void addService() {

        String serviceName = edit_txt_ServiceName.getText().toString().trim();
        String servicePrice_str = edit_txt_ServicePrice.getText().toString().trim();

        if (!TextUtils.isEmpty(serviceName) && !TextUtils.isEmpty(servicePrice_str)) { //name field and price field are not empty

            String id = dataServices.push().getKey();
            double servicePrice = Double.parseDouble((servicePrice_str));

            Service service = new Service(id, serviceName, servicePrice);

            dataServices.child(id).setValue(service);

            Toast.makeText(this, "Service Added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Please enter a service and a price!", Toast.LENGTH_LONG).show();
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
