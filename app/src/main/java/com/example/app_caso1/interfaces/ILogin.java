package com.example.app_caso1.interfaces;

import com.example.app_caso1.models.Login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ILogin {

    /*
    String email="gerajimenez4567@gmail.com";
    @GET("api/Usuarios?email="+email+"")
    Call<Login> userLogin();
     */
    @GET("api/Usuarios")
    Call<Login> find(@Query("email")String email);

}
