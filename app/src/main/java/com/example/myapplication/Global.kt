package com.example.myapplication

import android.app.Application
import com.example.myapplication.model.FoodTypeResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Global:Application() {
    var token:String?=null
    var fullname:String?=null
    var id:String?=null
    var roleId:String?=null
    var listFoodTypes:List<FoodTypeResponse.FoodType>?=null
    var nameFoodType:Array<String>?=null
    var idFoodType:Array<Int>?=null
    var size:Int=0
}