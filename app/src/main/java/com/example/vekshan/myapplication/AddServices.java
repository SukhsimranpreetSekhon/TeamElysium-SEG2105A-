package com.example.vekshan.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddServices extends AppCompatActivity {
    private DatabaseReference dataServices;
    private DatabaseReference dataServiceProv;

    private ListView listViewServices;

    private List<Service> serviceList;

    public class CheckListOfServices extends ArrayAdapter<Service> {
        private Context activity;
        List<Service> services;

        public CheckListOfServices(Context activity, List<Service> services){
            super(activity, R.layout.layout_services_checklist, services);
            this.activity = activity;
            this.services = services;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View view = layoutInflater.inflate(R.layout.layout_services_checklist, null, true);


            TextView textViewServiceName = view.findViewById(R.id.txt_view_ServiceName);
            CheckBox checkBox = view.findViewById(R.id.check_Service);

            final Service service = services.get(position);
            textViewServiceName.setText(service.getServiceName());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        dataServiceProv.child("Services").setValue(service.getServiceId()); //put id of serviceprov as first child
                    }
                }
            });

            return view;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);

        dataServices = FirebaseDatabase.getInstance().getReference("Service");
        dataServiceProv = FirebaseDatabase.getInstance().getReference("ServiceProvider");

        //Creating List for Services
        listViewServices = findViewById(R.id.servicesChecklist);
        serviceList = new ArrayList<>();

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

                CheckListOfServices servicesListAdapter = new CheckListOfServices(AddServices.this, serviceList);
                listViewServices.setAdapter(servicesListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
