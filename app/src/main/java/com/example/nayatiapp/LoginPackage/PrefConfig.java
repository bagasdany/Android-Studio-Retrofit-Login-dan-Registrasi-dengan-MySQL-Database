package com.example.nayatiapp.LoginPackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.nayatiapp.R;

public class PrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig (Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file),Context.MODE_PRIVATE);
    }
//    public void writeLoginStatus(boolean status)
//    {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(context.getString(R.string.pref_login_status),status);
//        editor.commit();
//    }
    public boolean readLoginStatus()
    {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }
//    public void  writeEmail (String email)
//    {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(context.getString(R.string.pref_email),email);
//        editor.commit();
//    }
//    public String readEmail()
//    {
//        return sharedPreferences.getString(context.getString(R.string.pref_email),"User");
//    }
//
    public void displayToast(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
