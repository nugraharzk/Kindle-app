package edu.upi.mobprogproject.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rizki on 11/29/2017.
 */

public class ApiClient {

    // Base URL for Retrofit
    public static final String BASE_URL = "http://mobprog.atwebpages.com/";
    private static Retrofit retrofit = null;

    // getClient Method
    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
