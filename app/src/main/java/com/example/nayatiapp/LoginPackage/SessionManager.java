package com.example.nayatiapp.LoginPackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public static final String KEY_email = "email";
    public static final String KEY_name = "name";
    public static final String KEY_id = "id_user";
    private static final String is_login = "logginstatus";
    private static final String SHARE_NAME = "logginsession";
    private static final int MODE_PRIVATE = 0;
    private Context _context;


    @SuppressLint("CommitPrefEdits")
    public SessionManager (Context context)
    {
        this._context=context;
        sp =_context.getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor = sp.edit();
    }
    //ada us
    public void storeLogin(String email, String name, String id_user)
    {
        editor.putBoolean(is_login,true);
        editor.putString(KEY_email, email);
        editor.putString(KEY_name, name);
        editor.putString(KEY_id, id_user);
        editor.commit();
    }


    public HashMap getDetailLogin()
    {
        HashMap<String,String> map = new HashMap<>();
        map.put(KEY_email,sp.getString(KEY_email,null));
        map.put(KEY_name,sp.getString(KEY_name,null));
        map.put(KEY_id,sp.getString(KEY_id,null));

        return map;
    }
    public void checkLogin()
    {
        if (!this.Loggin())
        {
            Intent login = new Intent(_context, LoginActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(login);
        }
    }
    public void logout()
    {
        editor.clear();
        editor.commit();
    }
    public Boolean Loggin()
    {
        return sp.getBoolean(is_login,false);
    }
}
