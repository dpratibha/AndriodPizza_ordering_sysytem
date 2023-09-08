package com.example.dmc_pizzaapp.utils;


import com.example.dmc_pizzaapp.entity.Users;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    String BASE_URL ="http://192.168.222.94:4001/";

    @GET("/user/{id}")
    Call<JsonObject> getUserById(@Path("id") int id);
    @POST("/user/login")
    Call<JsonObject> loginUser(@Body Users user);

}