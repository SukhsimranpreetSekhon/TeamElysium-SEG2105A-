package com.example.vekshan.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;

public class SearchByRating extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinnerRatingsScore;

    private ArrayAdapter<CharSequence> adapterRatingsScore;
    private Button btnSearchByRatings;
    private DatabaseReference dataServiceProv;
    private List<ServiceProvider> provList;
    private ListView listViewProv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_rating);

        //Initializing Views
        spinnerRatingsScore = findViewById(R.id.spinnerRatingsScore);

        //Spinner for Ratings
        adapterRatingsScore = ArrayAdapter.createFromResource(this, R.array.ratings, android.R.layout.simple_spinner_item);
        adapterRatingsScore.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRatingsScore.setAdapter(adapterRatingsScore);

        //Buttons
        btnSearchByRatings = findViewById(R.id.btnSearchByRatings);
        btnSearchByRatings.setOnClickListener(this);

        dataServiceProv = FirebaseDatabase.getInstance().getReference("ServiceProvider");

        listViewProv = findViewById(R.id.listViewProv);
        listViewProv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), " Booking for search by rating not implemented yet!", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        provList = new ArrayList<>();



    }

    @Override
    public void onClick(View v) {
        if (v == btnSearchByRatings){
            provList.clear();
            final Double rating = Double.valueOf(spinnerRatingsScore.getSelectedItem().toString().trim());
            dataServiceProv.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot provSnapshot : dataSnapshot.getChildren()) {
                        if (provSnapshot.child("rating").exists()){
                            Double dataRating = (Double) provSnapshot.child("rating").getValue();
                            if (dataRating.compareTo(rating) > 0){
                                String provId =provSnapshot.child("id").getValue(String.class);
                                String provName = provSnapshot.child("firstName").getValue(String.class);
                                String provPhone= provSnapshot.child("phoneNumber").getValue(String.class);
                                ServiceProvider prov = new ServiceProvider(provId,provName,provPhone);
                                provList.add(prov);
                                ListOfServiceProviders provAdapter =new ListOfServiceProviders(SearchByRating.this,provList);
                                listViewProv.setAdapter(provAdapter);
                            }
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
