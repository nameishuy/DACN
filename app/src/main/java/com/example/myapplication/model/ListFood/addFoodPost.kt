package com.example.myapplication.model.ListFood

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class addFoodPost {
    @SerializedName("food")
    @Expose
    var FoodAdd =FoodPost()

    class FoodPost{
        @SerializedName("name")
        @Expose
        var pstName:String? =null

        @SerializedName("image")
        @Expose
        var pstImg:String? =null

        @SerializedName("description")
        @Expose
        var pstDescription:String? =null

        @SerializedName("typeFoodId")
        @Expose
        var pstTypeFoodId:Int? = 0
    }

    @SerializedName("recipes")
    @Expose
    var listRecipes:List<recipe>?=null

    class recipe {
        @SerializedName("materialId")
        @Expose
        var pstMaterialId: Int? = 0

        @SerializedName("quantity")
        @Expose
        var pstQuantity: String? = null
    }
}