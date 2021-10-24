package com.example.myapplication.model.User

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserRequest {
    @SerializedName("userName")
    @Expose
    var username:String? =null
    @SerializedName("password")
    @Expose
    var pass:String? =null
}