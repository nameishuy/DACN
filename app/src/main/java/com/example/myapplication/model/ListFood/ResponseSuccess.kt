package com.example.myapplication.model.ListFood

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseSuccess {
    @SerializedName("message")
    @Expose
    var msg:String?=null
}