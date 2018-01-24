package edu.upi.mobprogproject.rest;

import java.util.List;

import edu.upi.mobprogproject.model.Accounts;
import edu.upi.mobprogproject.model.Events;
import edu.upi.mobprogproject.model.Notifications;
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

    // Interface for Accounts
    // URI Accounts
    //                      Base URL               / URI
    // complete URL : http://mobprog.atwebpages.com/accounts
    // Same as the others
    // GET Method
    @GET("accounts")
    Call<List<Accounts>> getUserLogin(@Query("username") String username, @Query("password") String password);

    // POST Method
    @FormUrlEncoded
    @POST("accounts")
    Call<Accounts> postAccount(@Field("username") String username, @Field("password") String password, @Field("email") String email);

    // PUT Method
    @FormUrlEncoded
    @PUT("accounts")
    Call<Accounts> putAccount(@Field("username") String username,
                              @Field("password") String password,
                              @Field("email") String email);

    /************************************************************************************************************/

    @GET("users")
    Call<List<Users>> getUsersList();

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
                        @Field("pekerjaan") String pekerjaan,
                        @Field("lat") String lat,
                        @Field("lng") String lng,
                        @Field("jabatan") String jabatan);

    /************************************************************************************************************/

    @GET("status")
    Call<List<Status>> getStatusList();

    @FormUrlEncoded
    @POST("status")
    Call<Status> postStatus(@Field("username") String username,
                            @Field("status") String status,
                            @Field("waktu") String waktu,
                            @Field("liked") String liked);

    /************************************************************************************************************/

    @GET("event")
    Call<List<Events>> getEventList();

    @FormUrlEncoded
    @POST("event")
    Call<Events> postEvent(@Field("judul") String judul,
                           @Field("username") String username,
                           @Field("waktu") String waktu,
                           @Field("priority") String priority,
                           @Field("deskripsi") String deskripsi,
                           @Field("lat") String lat,
                           @Field("lng") String lng,
                           @Field("konfirmasi") String konfirmasi);

    /************************************************************************************************************/

    @GET("notifications")
    Call<List<Notifications>> getNotifList();

    @FormUrlEncoded
    @POST("notifications")
    Call<Notifications> postNotif(@Field("username") String username,
                           @Field("pesan") String pesan,
                           @Field("urgensi") String urgensi);
}
