package com.example.myapplication.model.ListFood

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListFoodResponse {
    @SerializedName("message")
    @Expose
    var msg:String?=null
    @SerializedName("data")
    @Expose
    var data: listFood?=null

    class listFood{
        @SerializedName("foods")
        @Expose
        var list:List<Food>?=null
    }
    class Food{
        @SerializedName("id")
        @Expose
        var idFood:Int? =null
        @SerializedName("name")
        @Expose
        var nameFood:String? =null
        @SerializedName("image")
        @Expose
        var imageFood:String? =null
    }
}