package com.example.myapplication.api

import com.example.myapplication.model.UserRequest
import com.example.myapplication.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("authenticate")
    fun login(
        @Body userRequest: UserRequest
    ):Call<UserResponse>
}