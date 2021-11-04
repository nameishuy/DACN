package com.example.myapplication.model.ListFood

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FoodResponse {
    @SerializedName("data")
    @Expose
    var data:Food?=null

    class Food{
        @SerializedName("recipes")
        @Expose
        var listRecipes:List<recipe>?=null
        @SerializedName("food")
        @Expose
        var infoFood:infoFood?=null
    }

    class infoFood{
        @SerializedName("name")
        @Expose
        var name:String?=null
        @SerializedName("image")
        @Expose
        var img:String?=null
        @SerializedName("description")
        @Expose
        var description:String?=null
        @SerializedName("totalLike")
        @Expose
        var totallike:Int?=null
    }
    class recipe {
        @SerializedName("material")
        @Expose
        var material: String? = null

        @SerializedName("quantity")
        @Expose
        var quantity: String? = null
    }
}