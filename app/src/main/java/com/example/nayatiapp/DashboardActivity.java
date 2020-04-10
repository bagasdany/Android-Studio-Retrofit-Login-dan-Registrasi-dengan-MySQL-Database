package com.example.nayatiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.nayatiapp.AddCustomer.CustomerActivity;
import com.example.nayatiapp.LoginPackage.InfoActivity;
import com.example.nayatiapp.LoginPackage.SessionManager;
import com.example.nayatiapp.TrackingDatang.EditorActivity;
import com.example.nayatiapp.TrackingDatang.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardActivity extends AppCompatActivity implements ModalBottomSheet.ActionListener {

    ImageView tracking, menu, offer, katalog, info;
    ViewFlipper v_flipper;
    FloatingActionButton mFABexit;
    SessionManager sm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();


        int images [] = {R.drawable.nayatipicture, R.drawable.nayati2, R.drawable.nayati3};

        tracking = findViewById(R.id.tracking);
        menu = findViewById(R.id.menu);
        offer = findViewById(R.id.offer);
        katalog = findViewById(R.id.katalog);
        v_flipper = findViewById(R.id.vfliper);
        info = findViewById(R.id.info);
        sm = new SessionManager(DashboardActivity.this);
        sm.checkLogin();

        for(int image: images){
            flipperImage(image);
        }

        tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, EditorActivity.class);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, CustomerActivity.class);
                startActivity(i);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, InfoActivity.class);
                startActivity(i);

            }
        });

        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(DashboardActivity.this, SpecialRequest.class);
                startActivity(i);
            }
        });

        katalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(i);
            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*ModalBottomSheet modalBottomSheet = new ModalBottomSheet();
                modalBottomSheet.show(getSupportFragmentManager(), "modalmenu");*/
                moveTaskToBack(true);
            }
        });
    }

    public void flipperImage(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setInAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    public void onButtonClick(int id) {
        if (id == R.id.buttonA)
            moveTaskToBack(true);

    }
}
