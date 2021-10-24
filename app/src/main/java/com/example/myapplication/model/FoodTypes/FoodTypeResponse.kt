package com.example.myapplication.model.FoodTypes

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FoodTypeResponse {
    @SerializedName("foodTypes")
    @Expose
    var listFoodTypes:List<FoodType>?=null

    class FoodType{
        @SerializedName("id")
        @Expose
        var id:Int?=null
        @SerializedName("name")
        @Expose
        var name:String?=null
        @SerializedName("image")
        @Expose
        var image:String?=null
    }
}