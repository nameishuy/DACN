package com.example.myapplication.model.ListFood

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListFavorite {
    @SerializedName("data")
    @Expose
    var data:listFavorite?=null

    class listFavorite{
        @SerializedName("favorite")
        @Expose
        var list:List<Favorite>?=null
    }

    class Favorite{
        @SerializedName("foodId")
        @Expose
        var foodId:Int?=null

        @SerializedName("name")
        @Expose
        var nameFood:String?=null

        @SerializedName("image")
        @Expose
        var img:String?=null

        @SerializedName("totalLike")
        @Expose
        var totalLike:Int?=null
    }
}