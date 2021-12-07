package com.example.myapplication

import android.app.Application
import com.example.myapplication.model.ListFood.FoodTypeResponse
import com.example.myapplication.model.ListFood.addFoodPost

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

    //-------------------------------------------//
    var listRecipes:List<addFoodPost.recipe>?=null
    var listMaterialRecyclerView:ArrayList<Int> = arrayListOf(1)
    var listStepRecyclerView:ArrayList<Int> = arrayListOf(1)
    var ListMaterial = mutableListOf<String>()
    var ListMaterialId= mutableListOf<Int>()
    var ListMaterialQuatity= ArrayList<addFoodPost.recipe>()
    var ListNameChooseMaterial= mutableListOf<String>()
    var ListStep:ArrayList<String> = arrayListOf()
    var ListQuatity = mutableListOf<String>()
}