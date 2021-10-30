package com.example.myapplication.model.User

import com.google.gson.annotations.SerializedName

class UpdateInfoUserPost(
    @SerializedName("fullName") val PstUpFullName: String?,
    @SerializedName("image") val PstUpImage: String?,
    @SerializedName("phone") val PstUpPhone: String?,
    @SerializedName("birthday") val PstUpBirthday: String?,
    @SerializedName("region") val PstUpRegion: String?
)