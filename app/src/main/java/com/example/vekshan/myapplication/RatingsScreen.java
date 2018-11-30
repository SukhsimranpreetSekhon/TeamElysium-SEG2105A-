package com.example.vekshan.myapplication;

import android.provider.ContactsContract;
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
import java.util.List;

public class RatingsScreen extends AppCompatActivity {

    private ListView listViewProv;

    private List<ServiceProvider> pastProvidersList;
    private ServiceProvider prov;
    private DatabaseReference dataServiceProv;
    private DatabaseReference dataPastProv;
    private ValueEventListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_screen);

        //Database
        dataServiceProv = FirebaseDatabase.getInstance().getReference("ServiceProvider");
        dataPastProv = FirebaseDatabase.getInstance().getReference("HomeOwner").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ServiceProviders");

        /*spinnerRatings = findViewById(R.id.spinnerRatings);
        ratingsComment = findViewById(R.id.edit_txt_ratingsComment);
        btn_submitRating = findViewById(R.id.btnSubmitRating);

        //Setting Button Listeners
        btn_submitRating.setOnClickListener(this);
        */

        //Creating List
        listViewProv = findViewById(R.id.pastProvidersList);
        pastProvidersList = new ArrayList<>();

        //Long Click Listener
        listViewProv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                prov = pastProvidersList.get(position);
                openRatingsDialog();
                return true;
            }
        });
    }

    private void openRatingsDialog() {

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.ratings_dialog, null);

        final AlertDialog ratingsDialog = new AlertDialog.Builder(this).create();
        ratingsDialog.setView(view);
        ratingsDialog.setTitle("Add Rating");
        ratingsDialog.show();

        final Spinner spinnerRatings = view.findViewById(R.id.spinnerRatings);
        final EditText edit_txt_ratingsComment = view.findViewById(R.id.edit_txt_ratingsComment);

        Button btnSubmitRating = view.findViewById(R.id.btnSubmitRating);
        btnSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Double ratingScore = Double.parseDouble(spinnerRatings.getSelectedItem().toString().trim());
                String ratingsComment = edit_txt_ratingsComment.getText().toString().trim();

                final DatabaseReference dataRating = dataServiceProv.child(prov.getId()).child("rating");

                listener = dataRating.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                           Double rating = dataSnapshot.getValue(Double.class);
                           dataRating.setValue((rating+ratingScore)/2);
                           ratingsDialog.dismiss();
                           dataRating.removeEventListener(listener);
                           Toast.makeText(getApplicationContext(), "Rating Added!", Toast.LENGTH_LONG).show();
                        }else{
                            dataRating.setValue(ratingScore);
                            ratingsDialog.dismiss();
                            dataRating.removeEventListener(listener);
                            Toast.makeText(getApplicationContext(), "Rating Added!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

    }

    private void addRating() {
        //Database Reference stuff


    }

    @Override
    protected void onStart() {
        super.onStart();
        dataPastProv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                pastProvidersList.clear();
                for(DataSnapshot provSnapshot: dataSnapshot.getChildren()) {
                    String provId =provSnapshot.child("id").getValue(String.class);
                    String provName = provSnapshot.child("firstName").getValue(String.class);
                    String provPhone= provSnapshot.child("phoneNumber").getValue(String.class);
                    ServiceProvider pastProv = new ServiceProvider(provId,provName,provPhone);
                    pastProvidersList.add(pastProv);
                }

                ListOfServiceProviders provAdapter =new ListOfServiceProviders(RatingsScreen.this,pastProvidersList);
                listViewProv.setAdapter(provAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
