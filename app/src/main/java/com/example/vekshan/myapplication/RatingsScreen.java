package com.example.vekshan.myapplication;

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

import java.util.ArrayList;
import java.util.List;

public class RatingsScreen extends AppCompatActivity implements View.OnClickListener{

    private Spinner spinnerRatings;

    private EditText ratingsComment;

    private Button btn_submitRating;

    private ListView listViewPastServices;

    private List<ServiceProvider> pastServicesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_screen);

        //Database

        spinnerRatings = findViewById(R.id.spinnerRatings);
        ratingsComment = findViewById(R.id.edit_txt_ratingsComment);
        btn_submitRating = findViewById(R.id.btnSubmitRating);

        //Setting Button Listeners
        btn_submitRating.setOnClickListener(this);

        //Creating List for Services
        listViewPastServices = findViewById(R.id.pastServicesList);
        pastServicesList = new ArrayList<>();

        //Long Click Listener
        listViewPastServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ServiceProvider pastService = pastServicesList.get(position);
                //openRatingsDialog(pastService.getId(), pastService.getRatingsScore, pastService.getRatingsComment());
                return true;
            }
        });
    }

    private void openRatingsDialog(final String pastServiceId, final String ratingScore, final String ratingComment) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.ratings_dialog, null);

        final AlertDialog ratingsDialog = new AlertDialog.Builder(this).create();
        ratingsDialog.setView(view);
        ratingsDialog.setTitle("Add Rating");
        ratingsDialog.show();

        final Spinner spinnerRatings = view.findViewById(R.id.spinnerRatings);
        final EditText edit_txt_ratingsComment = view.findViewById(R.id.edit_txt_ratingsComment);

        final Button btnSubmitRating = view.findViewById(R.id.btnSubmitRating);

       /* btnSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void OnClick(View v) {

                String ratingScore = spinnerRatings.getSelectedItem().toString().trim();
                String ratingsComment = edit_txt_ratingsComment.getText().toString().trim();

                if (!TextUtils.isEmpty(ratingScore) && !TextUtils.isEmpty(ratingsComment)){
                    addRating();
                    ratingsDialog.dismiss();

                } else if (TextUtils.isEmpty(ratingScore) && !TextUtils.isEmpty(ratingsComment)){
                    addRating();
                    ratingsDialog.dismiss();

                } else {
                    addRating();
                    ratingsDialog.dismiss();
                }
            }
        });*/

    }

    private void addRating() {
        //Database Reference stuff


    }

    @Override
    public void onClick(View v) {

    }
}
