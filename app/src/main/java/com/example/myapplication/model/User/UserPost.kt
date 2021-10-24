package com.example.myapplication.model.User

import com.google.gson.annotations.SerializedName

class UserPost (
    @SerializedName("userName") val PstuserName: String?,
    @SerializedName("password") val Pstpassword: String?,
    @SerializedName("confirmPassword") val PstConPassword: String?,
    @SerializedName("fullName") val PstFullName: String?
        )