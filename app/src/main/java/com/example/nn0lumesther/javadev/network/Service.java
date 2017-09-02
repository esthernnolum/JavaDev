package com.example.nn0lumesther.javadev.network;

import com.example.nn0lumesther.javadev.model.Developer;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/search/users")
    Call<ArrayList<Developer>> getDevsInLagos(@Query("q") String developers);
}
