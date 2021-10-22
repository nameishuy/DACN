package com.example.myapplication

import android.app.Application
import com.example.myapplication.model.FoodTypeResponse

class Global:Application() {
    var token:String?=null
    var fullname:String?=null
    var id:String?=null
    var roleId:String?=null
    var listFoodTypes:List<FoodTypeResponse.FoodType>?=null
    var size:Int=0
}