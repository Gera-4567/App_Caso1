package com.example.app_caso1.interfaces;

import com.example.app_caso1.models.Presas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IPresas {

    @GET("api/Presas")
    Call<List<Presas>> getPresas();

}
