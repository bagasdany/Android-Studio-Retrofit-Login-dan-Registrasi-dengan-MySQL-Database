package com.example.nayatiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.nayatiapp.TrackingDatang.MainActivity;

public class SubTracking extends AppCompatActivity {

    ImageView trackpulang, trackdatang;
    ViewFlipper v_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_tracking);
        getSupportActionBar().hide();

        trackpulang = findViewById(R.id.trackingPulang);
        trackdatang = findViewById(R.id.trackingDatang);
        v_flipper = findViewById(R.id.vfliper);

        int images [] = {R.drawable.nayatipicture, R.drawable.nayati2, R.drawable.nayati3};

        for(int image: images){
            flipperImage(image);
        }

        trackdatang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubTracking.this, MainActivity.class);
                startActivity(i);
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
}
