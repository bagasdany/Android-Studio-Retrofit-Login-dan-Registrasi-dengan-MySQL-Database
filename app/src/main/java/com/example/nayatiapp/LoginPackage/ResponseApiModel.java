package com.example.nayatiapp.LoginPackage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseApiModel {

    @SerializedName("kode")
    String kode;
    @SerializedName("pesan")
    String pesan;
    @SerializedName("result")
    List<UserModel> result;

    public ResponseApiModel () {}

    public List<UserModel> getResult() {
        return result;
    }
    public  void setResult(List<UserModel> result) {
        this.result = result;
    }
    public String getKode(){
        return kode;
    }
    public void setKode(String kode) {
        this.kode = kode;
    }
    public String getPesan() {
        return pesan;
    }
    public void setPesan(String pesan) {
        this.pesan=pesan;
    }

}
