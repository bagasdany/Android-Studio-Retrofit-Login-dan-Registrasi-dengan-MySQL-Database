package com.example.nayatiapp.LoginPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nayatiapp.R;
import com.example.nayatiapp.TrackingPulang.EditorPulangActivity;

import java.util.HashMap;

public class InfoActivity extends AppCompatActivity {

    TextView email, nama, id_user;
    SessionManager sm;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        email = findViewById(R.id.tvemail);
        nama = findViewById(R.id.tvnama);
        id_user = findViewById(R.id.tvid);
        logout = findViewById(R.id.btnLogout);
        sm = new SessionManager(InfoActivity.this);

        HashMap<String, String> map = sm.getDetailLogin();
        nama.setText(map.get(sm.KEY_name));
        email.setText(map.get(sm.KEY_email));
        id_user.setText(map.get(sm.KEY_id));


        sm.checkLogin();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(InfoActivity.this);
                dialog.setMessage("Are You Sure to Logout?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        sm.logout();
                        sm.checkLogin();
                    }
                });
                dialog.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });



    }
}
