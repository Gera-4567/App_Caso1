package com.example.app_caso1.interfaces;

import com.example.app_caso1.models.Depositos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IDepositos {

    @GET("api/Depositos")
    Call<List<Depositos>> getDepositos();

}
