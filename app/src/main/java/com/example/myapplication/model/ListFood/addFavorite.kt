package com.example.myapplication.model.ListFood

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class addFavorite {
    @SerializedName("foodId")
    @Expose
    var foodId:Int?=null

    @SerializedName("accountId")
    @Expose
    var idUser:Int?=null

    @SerializedName("name")
    @Expose
    var name:String?=null
}