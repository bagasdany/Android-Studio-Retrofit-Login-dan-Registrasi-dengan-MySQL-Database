package com.example.nayatiapp.AddCustomer;

import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("customer_id")
    private int customer_id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("countrycd")
    private int countrycd;
    @SerializedName("country")
    private int country;
    @SerializedName("city")
    private String city;
    @SerializedName("phone")
    private String phone;
    @SerializedName("fax")
    private String fax;
    @SerializedName("email")
    private String email;
    @SerializedName("top_code")
    private int top_code;
    @SerializedName("currency")
    private int currency;
    @SerializedName("maxdisc")
    private String maxdisc;
    @SerializedName("person")
    private String person;
    @SerializedName("user")
    private String user;
    @SerializedName("catcode")
    private int catcode;
    @SerializedName("picture")
    private String picture;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("love")
    private Boolean love;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCountrycd() {
        return countrycd;
    }

    public void setCountrycd(int countrycd) {
        this.countrycd = countrycd;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTop_code() {
        return top_code;
    }

    public void setTop_code(int top_code) {
        this.top_code = top_code;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getMaxdisc() {
        return maxdisc;
    }

    public void setMaxdisc(String maxdisc) {
        this.maxdisc = maxdisc;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getCatcode() {
        return catcode;
    }

    public void setCatcode(int catcode) {
        this.catcode = catcode;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Boolean getLove() {
        return love;
    }

    public void setLove(Boolean love) {
        this.love = love;
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
