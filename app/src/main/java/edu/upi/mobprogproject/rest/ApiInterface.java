package edu.upi.mobprogproject.rest;

import java.util.List;

import edu.upi.mobprogproject.model.Accounts;
import edu.upi.mobprogproject.model.Events;
import edu.upi.mobprogproject.model.Status;
import edu.upi.mobprogproject.model.Users;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<List<Accounts>> getUserLogin(@Query("username") String username, @Query("password") String password);
//    @GET("accounts")
//    Call<List<Accounts>> getAccountsList();

    @FormUrlEncoded
    @POST("accounts")
    Call<Accounts> postAccount(@Field("username") String username, @Field("password") String password, @Field("email") String email);

    @FormUrlEncoded
    @POST("users")
    Call<Users> postUser(@Field("username") String username, @Field("nama") String nama);

    @FormUrlEncoded
    @PUT("users")
    Call<Users> putUser(@Field("username") String username,
                        @Field("nama") String nama,
                        @Field("ttl") String ttl,
                        @Field("alamat") String alamat,
                        @Field("rt") String rt,
                        @Field("rw") String rw,
                        @Field("desa") String desa,
                        @Field("telepon") String telepon,
                        @Field("pekerjaan") String pekerjaan);

    @FormUrlEncoded
    @PUT("accounts")
    Call<Accounts> putAccount(@Field("username") String username,
                              @Field("password") String password,
                              @Field("email") String email);


    @GET("status")
    Call<List<Status>> getStatusList();


    @GET("event")
    Call<List<Events>> getEventList();
}
