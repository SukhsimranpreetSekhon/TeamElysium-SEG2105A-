package com.example.vekshan.myapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListOfServiceProviders extends ArrayAdapter<ServiceProvider> {
    private Activity activity;
    List<ServiceProvider> serviceProviders;

    public ListOfServiceProviders(Activity context, List<ServiceProvider> serviceProviders){
        super(context, R.layout.layout_service_provider, serviceProviders);
        this.activity = context;
        this.serviceProviders = serviceProviders;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.layout_service_provider, null, true);

        TextView txt_view_ServiceProviderFirstName = view.findViewById(R.id.txt_view_ServiceProvFirstName);
        //TextView txt_view_ServiceProviderLastName = view.findViewById(R.id.txt_view_ServiceProvLastName);
        //TextView txt_view_ServiceProvEmail = view.findViewById(R.id.txt_view_ServiceProvEmail);
        TextView txt_view_ServiceProvPhoneNumber = view.findViewById(R.id.txt_view_ServiceProvPhoneNumber);

        ServiceProvider serviceProv = serviceProviders.get(position);

        txt_view_ServiceProviderFirstName.setText(serviceProv.getFirstName());
        //txt_view_ServiceProviderLastName.setText(serviceProv.getLastName());
        //txt_view_ServiceProvEmail.setText(serviceProv.getEmail());
        txt_view_ServiceProvPhoneNumber.setText(serviceProv.getPhoneNumber());

        return view;
    }


}
