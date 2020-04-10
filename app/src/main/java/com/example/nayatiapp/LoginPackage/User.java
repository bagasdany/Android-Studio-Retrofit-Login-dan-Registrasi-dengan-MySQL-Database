package com.example.nayatiapp.LoginPackage;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("response")
    private String Response;

    @SerializedName("email")
    private String Email;

    public String getResponse() {
        return Response;
    }

    public String getEmail() {
        return Email;
    }
}
