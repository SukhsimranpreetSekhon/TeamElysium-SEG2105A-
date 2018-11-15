package com.example.vekshan.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListOfServices extends ArrayAdapter<Service> {
    private Activity activity;
    List<Service> services;

    public ListOfServices(Activity context, List<Service> services){
        super(context, R.layout.layout_service_list, services);
        this.activity = context;
        this.services = services;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.layout_service_list, null, true);


        TextView txt_view_ServiceName = view.findViewById(R.id.txt_view_ServiceName);
        TextView txt_view_ServicePrice = view.findViewById(R.id.txt_view_ServicePrice);

        Service service = services.get(position);

        txt_view_ServiceName.setText(service.getServiceName());
        txt_view_ServicePrice.setText(String.valueOf(service.getServicePrice()));

        return view;
    }

}
