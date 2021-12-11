package com.example.myapplication.model.ListFood

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FoodMostLikes {
    @SerializedName("foods")
    @Expose
        var top6:List<toplikeFood>?=null

    class toplikeFood{
        @SerializedName("id")
        @Expose
            var idFood:Int?=null
        @SerializedName("name")
        @Expose
            var nameFood:String?=null
        @SerializedName("image")
        @Expose
            var img:String?=null
        @SerializedName("totalLike")
        @Expose
            var totalLike:Int?=null
        @SerializedName("nameTypeFood")
        @Expose
            var nameTypeFood:String?=null
    }
}