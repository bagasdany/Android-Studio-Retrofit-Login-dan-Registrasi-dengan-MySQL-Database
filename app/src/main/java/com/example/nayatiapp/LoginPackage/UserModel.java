package com.example.nayatiapp.LoginPackage;

import com.google.gson.annotations.SerializedName;

public class UserModel {


    /*@SerializedName("kampus")
    String kampususer;
    @SerializedName("username")
    String username;*/
    @SerializedName("email")
    String email;
    @SerializedName("name")
    String name;
    @SerializedName("id_user")
    String id_user;

    public UserModel() {};
    /*public String getKampususer() {
        return kampususer;
    }
    public void setKampususer(String kampususer) {
        this.kampususer = kampususer;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
