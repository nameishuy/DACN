package com.example.myapplication.model.User

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserInfoResponse {
    @SerializedName("message")
    @Expose
    var msg:String? = null
    @SerializedName("data")
    @Expose
    var data: UserInfo? = null

    class UserInfo {
        @SerializedName("fullName")
        @Expose
        var fullName:String? = null

        @SerializedName("image")
        @Expose
        var imageUser: String? = null

        @SerializedName("phone")
        @Expose
        var phoneNumUser: String? = null

        @SerializedName("birthday")
        @Expose
        var birthdayUser: String? = null

        @SerializedName("region")
        @Expose
        var regionUser: String? = null
    }
}