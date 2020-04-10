package com.example.nayatiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nayatiapp.APIPackage.ApiClient;
import com.example.nayatiapp.APIPackage.ApiInterface;
import com.example.nayatiapp.LoginPackage.PrefConfig;
import com.example.nayatiapp.LoginPackage.RegistrationFragment;

public class MainActivity2 extends AppCompatActivity implements LoginFragment.OnLoginFormActivityListener {
    public static PrefConfig prefConfig;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        if (findViewById(R.id.fragment_container)!=null);
        {
            if (savedInstanceState!=null)
            {
                return;
            }
            if (prefConfig.readLoginStatus())
            {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new WelcomeFragment()).commit();
            }else
            {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new RegistrationFragment()).commit();
            }
        }
    }

    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new RegistrationFragment()).addToBackStack(null).commit();
    }

    @Override
    public void performLogin(String email) {

    }
}
