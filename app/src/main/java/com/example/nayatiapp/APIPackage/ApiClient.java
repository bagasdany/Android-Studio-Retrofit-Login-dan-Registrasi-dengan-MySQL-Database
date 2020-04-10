package com.example.nayatiapp.APIPackage;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

        private static final String BASE_URL = "http://192.168.1.5/nayati_mobile/";
    private static Retrofit retrofit = null;

    public static Retrofit getApiClient(){

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

    public static ApiInterface getRequestService() {
        return getApiClient().create(ApiInterface.class);
    }
}
