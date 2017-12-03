package edu.upi.mobprogproject.rest;

import java.util.List;

import edu.upi.mobprogproject.model.Accounts;
import edu.upi.mobprogproject.model.Users;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rizki on 11/29/2017.
 */

public interface ApiInterface {

    @GET("users")
    Call<List<Users>> getUsersList();

    @GET("users/{username}")
    Call<List<Users>> getUsersUsername(@Query("username") String username);

    @GET("accounts")
    Call<List<Accounts>> getAccountsList();
}
