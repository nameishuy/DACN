package com.example.myapplication.api

import com.example.myapplication.model.ListFood.FoodResponse
import com.example.myapplication.model.ListFood.FoodTypeResponse
import com.example.myapplication.model.ListFood.ListFoodResponse
import com.example.myapplication.model.User.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface API {

    //Login
    @POST("authenticate")
    fun login(
        @Body userRequest: UserRequest
    ):Call<UserResponse>

    //Register
    @POST("register")
    fun Resgister(
        @Body userPost: UserPost
    ):Call<UserPost>

    //Get List Type Food
    @GET("getListTypeFood")
    fun getData():Call<FoodTypeResponse>

    //Link for this resolve: https://www.youtube.com/watch?v=lE4dXZp6_fA
    @GET("getListFoodByType")
    fun getList(
        @Query("foodId") foodId:Int?=null
    ):Call<ListFoodResponse>

    //Get Food Detail
    @GET("getDetailFood")
    fun getDetail(
        @Query("id") id:Int?=null
    ):Call<FoodResponse>

    //get user info
    @GET("getAccountInfo")
    fun getUserInfo(
        @Query("id") idUser:Int?=null
    ):Call<UserInfoResponse>

    //Update user
    @POST("updateAccount")
    fun UpdateUser(
        @Query("id") idUpUser:Int?=null,
        @Body UpUser: UpdateInfoUserPost
    ):Call<UpdateInfoUserPost>
}