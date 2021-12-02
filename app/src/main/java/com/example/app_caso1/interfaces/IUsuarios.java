package com.example.app_caso1.interfaces;

import com.example.app_caso1.models.Usuarios;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUsuarios {

    @POST("api/Usuarios")
    Call<Usuarios> createPost(@Body Usuarios usuarios);

}
