package com.example.myapplication.model.User

import com.google.gson.annotations.SerializedName

class ChangePassPost (
    @SerializedName("oldPassword") val PstOldPass: String?,
    @SerializedName("newPassword") val PstNewPass: String?,
    @SerializedName("confirmNewPassword") val PstConfirmNewPass: String?
        )