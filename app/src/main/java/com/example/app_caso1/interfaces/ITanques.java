package com.example.app_caso1.interfaces;

import com.example.app_caso1.models.Tanques;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ITanques {

    @GET("api/Tanques")
    Call<List<Tanques>> getTanques();

}
