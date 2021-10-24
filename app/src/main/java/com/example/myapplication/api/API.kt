package com.example.myapplication.api

import com.example.myapplication.model.FoodTypes.FoodTypeResponse
import com.example.myapplication.model.ListFood.ListFoodResponse
import com.example.myapplication.model.User.UserPost
import com.example.myapplication.model.User.UserRequest
import com.example.myapplication.model.User.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    //Link for this resolve: https://www.youtube.com/watch?v=lE4dXZp6_fA
    @GET("getListFoodByType")
    fun getList(
        @Query("foodId") foodId:Int?=null
    ):Call<ListFoodResponse>
}