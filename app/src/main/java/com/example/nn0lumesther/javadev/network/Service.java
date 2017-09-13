package com.example.nn0lumesther.javadev.network;

import com.example.nn0lumesther.javadev.model.DeveloperList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("/search/users")
    Call<DeveloperList> getDevsInLagos(@Query("q") String developers);
}