package com.example.nayatiapp.TrackingPulang;

import com.google.gson.annotations.SerializedName;

public class Pulang {

    @SerializedName("id")
    private int id;
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("userP")
    private String userP;
    @SerializedName("nameP")
    private String nameP;
    @SerializedName("catatanP")
    private String catatanP;
    @SerializedName("lokasiP")
    private String lokasiP;
    @SerializedName("genderP")
    private int genderP;
    @SerializedName("tanggalP")
    private String tanggalP;
    @SerializedName("pictureP")
    private String pictureP;
    @SerializedName("loveP")
    private Boolean loveP;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_userP(int id_user) {
        this.id_user = id_user;
    }

    public String getUserP() {
        return userP;
    }

    public void setUserP(String userP) {
        this.userP = userP;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public String getCatatanP() {
        return catatanP;
    }

    public void setCatatanP(String catatanP) {
        this.catatanP = catatanP;
    }

    public String getLokasiP() {
        return lokasiP;
    }

    public void setLokasiP(String lokasiP) {
        this.lokasiP = lokasiP;
    }

    public int getGenderP() {
        return genderP;
    }

    public void setGenderP(int genderP) {
        this.genderP = genderP;
    }

    public String getTanggalP() {
        return tanggalP;
    }

    public void setTanggalP(String tanggalP) {
        this.tanggalP = tanggalP;
    }

    public String getPictureP() {
        return pictureP;
    }

    public void setPictureP(String pictureP) {
        this.pictureP = pictureP;
    }

    public Boolean getLoveP() {
        return loveP;
    }

    public void setLoveP(Boolean loveP) {
        this.loveP = loveP;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

}
