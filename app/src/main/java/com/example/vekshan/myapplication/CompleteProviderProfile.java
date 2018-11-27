package com.example.vekshan.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompleteProviderProfile extends AppCompatActivity {

    private EditText enter_building_num;
    private EditText enter_street_name;
    private EditText enter_city;
    private EditText enter_postal_code;
    private Spinner spinnerProvince;
    private ArrayAdapter<CharSequence> adapter;
    private EditText enter_companyName;
    private EditText enter_generalDescription;
    private CheckBox checkBoxLicensed;
    private Button btnAddInfo;

    private DatabaseReference dataServiceProv;

    private Button addInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_provider_profile);

        //Initializing Views

        enter_building_num = findViewById(R.id.enter_buildingNum);
        enter_street_name = findViewById(R.id.enter_streetName);
        enter_city = findViewById(R.id.enter_city);
        enter_postal_code = findViewById(R.id.enter_postalCode);
        spinnerProvince = findViewById(R.id.spinnerProvince);

        enter_companyName = findViewById(R.id.enter_CompanyName);
        enter_generalDescription = findViewById(R.id.enter_generalDescription);
        btnAddInfo = findViewById(R.id.btn_AddInfo);
        checkBoxLicensed = findViewById(R.id.checkBox_Licensed);


        //spinner
        adapter = ArrayAdapter.createFromResource(this,R.array.provinces,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapter);

        dataServiceProv = FirebaseDatabase.getInstance().getReference("ServiceProvider").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // Setting Listeners
        btnAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call method addInfo
                addInfo();
            }
        });
    }

    private void addInfo(){
        final String buildingNum = enter_building_num.getText().toString().trim();
        final String streetName = enter_street_name.getText().toString().trim();
        final String cityName = enter_city.getText().toString().trim();
        final String postalCode = enter_postal_code.getText().toString().trim().replaceAll(" ", "");
        final String companyName = enter_companyName.getText().toString().trim();
        final String generalDescription = enter_generalDescription.getText().toString().trim();
        final String province = spinnerProvince.getSelectedItem().toString().trim();





        //Validations

        //Empty Address
        if(TextUtils.isEmpty(buildingNum) || TextUtils.isEmpty(streetName) || TextUtils.isEmpty(cityName) || TextUtils.isEmpty(postalCode)){
            Toast.makeText(CompleteProviderProfile.this, "All aspects of Postal Address must be included", Toast.LENGTH_SHORT).show();
            return;
        }


        // Empty Company Name
        if(TextUtils.isEmpty(companyName)){
            Toast.makeText(CompleteProviderProfile.this, "Company Name is required", Toast.LENGTH_SHORT).show();
            return;
        }


        // Incorrect Postal Code

        if(!(postalCode.length() == 6)){
            enter_postal_code.setError("Invalid Postal Code");
            return;
        }
        for(int i = 0; i < postalCode.length(); i++){
            if(!Character.isLetter(postalCode.charAt(0)) || !Character.isLetter(postalCode.charAt(2)) || !Character.isLetter(postalCode.charAt(4)) || !Character.isDigit(postalCode.charAt(1)) || !Character.isDigit(postalCode.charAt(3)) || !Character.isDigit(postalCode.charAt(5))){
                Toast.makeText(CompleteProviderProfile.this,"Postal Code is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Incorrect Building Number
        for(int i = 0; i < buildingNum.length(); i++){
            if(!Character.isDigit(buildingNum.charAt(i))){
                Toast.makeText(CompleteProviderProfile.this,"Building Number is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Incorrect Street Name
        for(int i = 0; i < streetName.length(); i++){
            if(!Character.isLetter(streetName.charAt(i)) && !Character.isWhitespace(streetName.charAt(i))){
                Toast.makeText(CompleteProviderProfile.this,"Street Name is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Incorrect City Name
        for(int i = 0; i < cityName.length(); i++){
            if(!Character.isLetter(cityName.charAt(i))){
                Toast.makeText(CompleteProviderProfile.this,"City Name is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }
        }



        ProviderAddress address = new ProviderAddress(buildingNum, streetName, cityName, province, postalCode);
        dataServiceProv.child("address").setValue(address);
        dataServiceProv.child("companyName").setValue(companyName);

        /*if (checkBoxLicensed.isChecked()) {
            dataServiceProv.child("licensed").setValue(true);
        }else{
            dataServiceProv.child("licensed").setValue(false);
        }*/

        dataServiceProv.child("generalInfo").setValue(generalDescription).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CompleteProviderProfile.this, "Profile Completed!", Toast.LENGTH_SHORT).show();
                    finish();
                } else{
                    Toast.makeText(CompleteProviderProfile.this,"Unable to complete profile! ", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
