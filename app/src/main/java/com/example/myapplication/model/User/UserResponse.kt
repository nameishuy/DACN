package com.example.myapplication.model.User

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("message")
    @Expose
    var msg:String? = null
    @SerializedName("data")
    @Expose
    var data: User? = null

    class User{
        @SerializedName("token")
        @Expose
        var token:String? = null
        var account: Account? = null
        class Account{
            @SerializedName("id")
            @Expose
            var id:Int? = null
            @SerializedName("fullName")
            @Expose
            var fullName:String? = null
            @SerializedName("roleId")
            @Expose
            var roleId:Int? = null
        }
    }
}