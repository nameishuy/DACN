package com.example.myapplication.api

import com.example.myapplication.model.FoodTypes.FoodTypeResponse
import com.example.myapplication.model.ListFood.ListFoodResponse
import com.example.myapplication.model.User.*
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