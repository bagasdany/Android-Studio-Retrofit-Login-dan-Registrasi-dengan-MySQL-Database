package com.example.nayatiapp.APIPackage;

import com.example.nayatiapp.AddCustomer.Customer;
import com.example.nayatiapp.LoginPackage.ResponseApiModel;
import com.example.nayatiapp.LoginPackage.User;
import com.example.nayatiapp.TrackingDatang.Track;
import com.example.nayatiapp.TrackingPulang.Pulang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("register.php")
    Call<User> performRegistration(@Query("name") String Name, @Query("email") String Email, @Query("password") String Password);

    @GET("get_track.php/data/{id_user}")
    Call<List<Track>> getPets(
            @Path("id_user") int id
    );

    @FormUrlEncoded
    @POST("add_track.php")
    Call<Track> insertTrack(
            @Field("key") String key,
            @Field("id_user") int id_user,
            @Field("user") String user,
            @Field("name") String name,
            @Field("catatan") String catatan,
            @Field("lokasi") String lokasi,
            @Field("gender") int gender,
            @Field("tanggal") String tanggal,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_track.php")
    Call<Track> updateTrack(
            @Field("key") String key,
            @Field("id") int id,
            @Field("id_user") int id_user,
            @Field("user") String user,
            @Field("name") String name,
            @Field("catatan") String catatan,
            @Field("lokasi") String lokasi,
            @Field("gender") int gender,
            @Field("tanggal") String tanggal,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("delete_track.php")
    Call<Track> deleteTrack(
            @Field("key") String key,
            @Field("id") int id,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_love.php")
    Call<Track> updateLove(
            @Field("key") String key,
            @Field("id") int id,
            @Field("love") boolean love);

    //pulang

    @GET("get_trackP.php/data/{id_user}")
    Call<List<Pulang>> getPetsPulang(
            @Path("id_user") int pulang
    );

    @FormUrlEncoded
    @POST("add_trackP.php")
    Call<Pulang> insertTrackPulang(
            @Field("key") String key,
            @Field("id_user") int id_user,
            @Field("userP") String userP,
            @Field("nameP") String nameP,
            @Field("catatanP") String catatanP,
            @Field("lokasiP") String lokasiP,
            @Field("genderP") int genderP,
            @Field("tanggalP") String tanggalP,
            @Field("pictureP") String pictureP);

    @FormUrlEncoded
    @POST("update_trackP.php")
    Call<Pulang> updateTrackPulang(
            @Field("key") String key,
            @Field("id") int id,
            @Field("id_user") int id_user,
            @Field("userP") String userP,
            @Field("nameP") String nameP,
            @Field("catatanP") String catatanP,
            @Field("lokasiP") String lokasiP,
            @Field("genderP") int genderP,
            @Field("tanggalP") String tanggalP,
            @Field("pictureP") String pictureP);

    @FormUrlEncoded
    @POST("delete_trackP.php")
    Call<Pulang> deleteTrackPulang(
            @Field("key") String key,
            @Field("id") int id,
            @Field("pictureP") String pictureP);

    @FormUrlEncoded
    @POST("update_loveP.php")
    Call<Pulang> updateLovePulang(
            @Field("key") String key,
            @Field("id") int id,
            @Field("loveP") boolean loveP);


    //add customer

    @FormUrlEncoded
    @POST("add_customer.php")
    Call<Customer> insertCustomer(
            @Field("key") String key,
            @Field("code") String code,
            @Field("name") String name,
            @Field("address") String address,
            @Field("countrycd") int countrycd,
            @Field("country") int country,
            @Field("city") String city,
            @Field("phone") String phone,
            @Field("fax") String fax,
            @Field("email") String email,
            @Field("top_code") int top_code,
            @Field("currency") int currency,
            @Field("maxdisc") String maxdisc,
            @Field("person") String person,
            @Field("user") String user,
            @Field("tanggal") String tanggal,
            @Field("catcode")  int catcode,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_loveCustomer.php")
    Call<Customer> updateLoveCustomer(
            @Field("key") String key,
            @Field("customer_id") int customer_id,
            @Field("love") boolean love);

    @FormUrlEncoded
    @POST("update_customer.php")
    Call<Customer> updateCustomer(
            @Field("key") String key,
            @Field("customer_id") int customer_id,
            @Field("code") String code,
            @Field("name") String name,
            @Field("address") String address,
            @Field("countrycd") int countrycd,
            @Field("country") int country,
            @Field("city") String city,
            @Field("phone") String phone,
            @Field("fax") String fax,
            @Field("email") String email,
            @Field("top_code") int top_code,
            @Field("currency") int currency,
            @Field("maxdisc") String maxdisc,
            @Field("person") String person,
            @Field("user") String user,
            @Field("tanggal") String tanggal,
            @Field("catcode") int catcode,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("delete_customer.php")
    Call<Customer> deleteCustomer(
            @Field("key") String key,
            @Field("customer_id") int customer_id,
            @Field("picture") String picture);

    @GET("get_customer.php")
    Call<List<Customer>> getPetsCust();

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseApiModel> login (@Field("email") String email,
                                  @Field("password") String password);





}
