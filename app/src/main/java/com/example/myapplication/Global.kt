package com.example.myapplication

import android.app.Application
import com.example.myapplication.model.ListFood.FoodTypeResponse

class Global:Application() {
    var token:String?=null
    var fullname:String?=null
    var id:String?=null
    var roleId:String?=null
    var imgUser:String?=null
    var listFoodTypes:List<FoodTypeResponse.FoodType>?=null
    var nameFoodType:Array<String>?=null
    var idFoodType:Array<Int>?=null
    var size:Int=0
}