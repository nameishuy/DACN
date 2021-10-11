package com.example.myapplication.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
    fun getRetroClientInstance():Retrofit{
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("https://cookingapp-api.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}