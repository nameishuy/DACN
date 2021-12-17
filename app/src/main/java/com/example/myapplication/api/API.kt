package com.example.myapplication.api

import com.example.myapplication.model.ListFood.*
import com.example.myapplication.model.User.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface API {

    //Login
    @POST("authenticate")
    fun login(
        @Body userRequest: UserRequest
    ):Call<UserResponse>

    //Register
    @POST("register")
    fun Resgister(
        @Body userPost: UserPost
    ):Call<UserPost>

    //Get List Type Food
    @GET("getListTypeFood")
    fun getData():Call<FoodTypeResponse>

    //Link for this resolve: https://www.youtube.com/watch?v=lE4dXZp6_fA
    @GET("getListFoodByType")
    fun getList(
        @Query("foodId") foodId:Int?=null
    ):Call<ListFoodResponse>

    //Get Food Detail
    @GET("getDetailFood")
    fun getDetail(
        @Query("id") id:Int?=null
    ):Call<FoodResponse>

    //Update Total Like
    @POST("updateTotalLike")
    fun updateTotalLike(
        @Query("id") id:Int?=null,
        @Query("totallike") totallike:Int?=null
    ):Call<ResponseSuccess>

    //get user info
    @GET("getAccountInfo")
    fun getUserInfo(
        @Query("id") idUser:Int?=null
    ):Call<UserInfoResponse>

    //Update user
    @POST("updateAccount")
    fun UpdateUser(
        @Query("id") idUpUser:Int?=null,
        @Body UpUser: UpdateInfoUserPost
    ):Call<UpdateInfoUserPost>

    //Get List Favorite
    @GET("getDetailFavorite")
    fun getListFavorite(
        @Query("id") id:Int?=null
    ):Call<ListFavorite>

    //Add Favorite
    @POST("addFavorite")
    fun addFavorite(
        @Body add:addFavorite
    ):Call<ResponseSuccess>

    //Remove Favorite
    @POST("removeFavorite")
    fun removeFavorite(
        @Query("foodId") foodId:Int?=null,
        @Query("accountId") idUser:Int?=null
    ):Call<ResponseSuccess>

    //Add FoodPost
    @POST("addFood")
    fun addNewFood(
        @Body addFood:addFoodPost
    ):Call<addFoodPost>

    //add Type Food
    @POST("addTypeFood")
    fun addNewFoodType(
        @Body addFoodType:FoodTypePost
    ):Call<FoodTypePost>

    //add Material
    @POST("addMaterial")
    fun addNewMaterial(
        @Body addMaterial:MaterialPost
    ):Call<MaterialPost>

    //get list Material
    @GET("getListMaterial")
    fun getDataMaterial():Call<MaterialResponse>

    //Change Pass
    @POST("changePassword")
    fun ChangePass(
        @Query("id") PstIdUser:Int? =null,
        @Body changePass:ChangePassPost
    ):Call<ChangePassPost>

    //Top 6 Most Likes
    @GET("get6Foods")
    fun getTop6():Call<FoodMostLikes>

    //Search Food
    @GET("searchByName")
    fun getListFood(
        @Query("txtSearchName") searchChar:String?=null
    ):Call<ListFoodSearch>
}