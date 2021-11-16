package com.example.myapplication.model.ListFood

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MaterialResponse {
    @SerializedName("materials")
    @Expose
    var listMaterial:List<Material>?=null

    class Material{
        @SerializedName("id")
        @Expose
        var id:Int?=null
        @SerializedName("name")
        @Expose
        var name:String?=null
    }
}