package com.example.myapplication.model.ListFood

import com.google.gson.annotations.SerializedName

class FoodTypePost(
    @SerializedName("name") val PstNameFoodType: String?,
    @SerializedName("image") val PstImageFoodType: String?
)