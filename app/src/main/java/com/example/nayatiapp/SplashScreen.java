package com.example.nayatiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.nayatiapp.LoginPackage.InfoActivity;
import com.example.nayatiapp.LoginPackage.LoginActivity;
import com.example.nayatiapp.LoginPackage.SessionManager;

public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        Thread thread = new Thread() {
            public void run() {
                //thread adalah suatu proses struktural dimana suatu fungsi dieksekusi dengan berbagai method
                try {
                    sleep(4000); //melakukan pending selama 4 detik
                } catch (InterruptedException e) {
                    e.printStackTrace(); //menjalankan aktivitas
                } finally {
                    startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
                    finish();
                }
                //try dan catch merupakan suatu fitur pada java yang dikhususkan untuk menangani suatu kesalahan dalam listing program
            }
        };
        thread.start();
        //thread dimulai
    }
}
