package com.example.myapplication.api

import com.example.myapplication.model.FoodTypeResponse
import com.example.myapplication.model.UserPost
import com.example.myapplication.model.UserRequest
import com.example.myapplication.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    @POST("authenticate")
    fun login(
        @Body userRequest: UserRequest
    ):Call<UserResponse>

    @POST("register")
    fun Resgister(
        @Body userPost: UserPost
    ):Call<UserPost>

    @GET("getListTypeFood")
    fun getData():Call<FoodTypeResponse>
}