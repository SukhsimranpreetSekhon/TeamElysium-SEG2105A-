package com.example.vekshan.myapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListOfAvailabilities extends ArrayAdapter<Availability> {
    private Activity activity;
    List<Availability> availabilities;

    public ListOfAvailabilities(Activity context, List<Availability> availabilities){
        super(context, R.layout.layout_availability_list, availabilities);
        this.activity = context;
        this.availabilities = availabilities;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.layout_availability_list, null, true);

        TextView txt_view_AvailabilityDay = view.findViewById(R.id.txt_view_AvailabilityDay);
        TextView txt_view_AvailabilityTime = view.findViewById(R.id.txt_view_AvailabilityTime);

        Availability availability = availabilities.get(position);

        txt_view_AvailabilityDay.setText(availability.getDay());
        txt_view_AvailabilityTime.setText(availability.getTimeslot());

        return view;

    }

}
