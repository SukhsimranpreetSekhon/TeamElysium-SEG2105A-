package com.example.vekshan.myapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListOfServices extends ArrayAdapter<Service> {
    private Activity context;
    List<Service> services;

    public ListOfServices(Activity context, List<Service> services){
        super(context, R.layout.layout_service_list, services);
        this.context = context;
        this.services = services;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();

        View listItem = layoutInflater.inflate(R.layout.layout_service_list, null, true);


        TextView textViewServiceName = listItem.findViewById(R.id.txt_view_ServiceName);
        TextView textViewServicePrice = listItem.findViewById(R.id.txt_view_ServicePrice);

        Service service = services.get(position);

        textViewServiceName.setText(service.getServiceName());
        textViewServicePrice.setText(String.valueOf(service.getServicePrice()));

        return listItem;
    }

}
