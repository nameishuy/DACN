package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_food.*
import kotlinx.android.synthetic.main.fragment_food.btnBack
import retrofit2.Call
import retrofit2.Response


class FoodFragment : Fragment() {

    var idFood:Int?=null
    var nameFoodType:String?=null
    var idFoodType:Int?=null
    var flag:Boolean = false
    val glbl = Global()
    var area:String?=null
    var searchChar:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()

        //set event click button back
        btnBack.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                //Check area to put correct data.
                if(area == "ListFood"){
                    val activity=v!!.context as AppCompatActivity
                    val ListFood = ListFoodFragment()
                    val bundle = Bundle()
                    ListFood.arguments = bundle
                    bundle.putInt("idChoose", idFoodType!!)
                    bundle.putString("type",nameFoodType)
                    bundle.putString("token",glbl.token)
                    bundle.putString("fullname",glbl.fullname)
                    bundle.putString("id",glbl.id)
                    bundle.putString("roleId",glbl.roleId)
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,ListFood).addToBackStack(Fragment::class.java.simpleName).commit()
                }else if(area =="ListFavorite"){
                    val activity=v!!.context as AppCompatActivity
                    val Favorite = FavoriteFragment()
                    val bundle = Bundle()
                    Favorite.arguments = bundle
                    bundle.putString("id",glbl.id)
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,Favorite).addToBackStack(Fragment::class.java.simpleName).commit()
                }else if(area =="HomeFragment"){
                    val activity=v!!.context as AppCompatActivity
                    val Home = HomeFragment()
                    val bundle = Bundle()
                    Home.arguments = bundle
                    bundle.putString("id",glbl.id)
                    bundle.putString("roleId",glbl.roleId)
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,Home).addToBackStack(Fragment::class.java.simpleName).commit()
                }else if(area == "FoodSearch"){
                    val activity=v!!.context as AppCompatActivity
                    val Search = SearchFragment()
                    val bundle = Bundle()
                    Search.arguments = bundle
                    bundle.putString("id",glbl.id)
                    bundle.putString("roleId",glbl.roleId)
                    bundle.putString("searchChar",searchChar)
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,Search).addToBackStack(Fragment::class.java.simpleName).commit()
                }
            }
        })
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        var food:Call<FoodResponse> = retro.getDetail(idFood)
        food.enqueue(object :retrofit2.Callback<FoodResponse>{
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                val data = response.body()
                Picasso.get().load(data.data!!.infoFood!!.img.toString()).into(imgFood)
                nameFood.text = data.data!!.infoFood!!.name.toString()
                favoriteIcon.setColorFilter(null)
                titleFavoriteButton.text = "Thích (" + data.data!!.infoFood!!.totallike!! + ")"
                titleFavoriteButton.setTextColor(resources.getColor(R.color.black))

                //Call API List Favorite to take id Food in that list
                var listFavorite:Call<ListFavorite> = retro.getListFavorite(glbl.id!!.toInt())
                listFavorite.enqueue(object :retrofit2.Callback<ListFavorite>{
                    override fun onResponse(
                        call: Call<ListFavorite>,
                        response: Response<ListFavorite>
                    ) {
                        val dataList = response.body()
                        for(item:ListFavorite.Favorite in dataList.data!!.list!!){
                            if(idFood == item.foodId){
                                favoriteIcon.setColorFilter(resources.getColor(R.color.orange))
                                titleFavoriteButton.text = "Đã Thích (" + data.data!!.infoFood!!.totallike!! + ")"
                                titleFavoriteButton.setTextColor(resources.getColor(R.color.orange))
                                flag = true
                            }
                        }
                    }

                    override fun onFailure(call: Call<ListFavorite>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })

                description.text = data.data!!.infoFood!!.description
                for(item:FoodResponse.recipe in data.data!!.listRecipes!!){
                    if(item == data.data!!.listRecipes!![0]){
                        recipe.append("\u2022 " + item.material + ":\t\t" + item.quantity)
                    }else{
                        recipe.append("\n\u2022 " + item.material + ":\t\t" + item.quantity)
                    }
                }

                //Get id video of food
                lifecycle.addObserver(videoFood)
                videoFood.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.loadOrCueVideo(lifecycle,data.data!!.infoFood!!.videoId!!,0F)
                    }
                })

            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {

            }
        })
        // set event click of like button
        favoriteButton.setOnClickListener {
            if(flag == false){
                var food:Call<FoodResponse> = retro.getDetail(idFood)
                food.enqueue(object :retrofit2.Callback<FoodResponse>{
                    override fun onResponse(
                        call: Call<FoodResponse>,
                        response: Response<FoodResponse>
                    ) {
                        favoriteIcon.setColorFilter(resources.getColor(R.color.orange))
                        var likeChange:Int = response.body().data!!.infoFood!!.totallike!!+1
                        titleFavoriteButton.text = "Đã Thích (" + likeChange + ")"
                        titleFavoriteButton.setTextColor(resources.getColor(R.color.orange))

                        //Call API to update Total Like of this Food
                        var success:Call<ResponseSuccess> = retro.updateTotalLike(idFood,likeChange)
                        success.enqueue(object :retrofit2.Callback<ResponseSuccess>{
                            override fun onResponse(
                                call: Call<ResponseSuccess>,
                                responseSuccess: Response<ResponseSuccess>
                            ) {
                                Log.e("announce",responseSuccess.body().msg.toString())
                            }

                            override fun onFailure(
                                call: Call<ResponseSuccess>,
                                t: Throwable
                            ) {
                                TODO("Not yet implemented")
                            }
                        })
                    }

                    override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })


                //Call API to add Food in List Favorite of User.
                val add = addFavorite()
                add.foodId = idFood
                add.idUser = glbl.id!!.toInt()
                add.name = null
                retro.addFavorite(add).enqueue(object :retrofit2.Callback<ResponseSuccess>{
                    override fun onResponse(
                        call: Call<ResponseSuccess>,
                        response: Response<ResponseSuccess>
                    ) {
                        Log.e("announce",response.body().msg.toString())
                    }

                    override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })

                flag = true
            }else{
                var food:Call<FoodResponse> = retro.getDetail(idFood)
                food.enqueue(object :retrofit2.Callback<FoodResponse>{
                    override fun onResponse(
                        call: Call<FoodResponse>,
                        response: Response<FoodResponse>
                    ) {
                        favoriteIcon.setColorFilter(null)
                        var likeChange:Int = response.body().data!!.infoFood!!.totallike!!-1
                        titleFavoriteButton.text = "Thích (" + likeChange + ")"
                        titleFavoriteButton.setTextColor(resources.getColor(R.color.black))

                        //Call API to update Total Like of this Food
                        var success:Call<ResponseSuccess> = retro.updateTotalLike(idFood,likeChange)
                        success.enqueue(object :retrofit2.Callback<ResponseSuccess>{
                            override fun onResponse(
                                call: Call<ResponseSuccess>,
                                responseSuccess: Response<ResponseSuccess>
                            ) {
                                Log.e("announce",responseSuccess.body().msg.toString())
                            }

                            override fun onFailure(
                                call: Call<ResponseSuccess>,
                                t: Throwable
                            ) {
                                TODO("Not yet implemented")
                            }
                        })
                    }

                    override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })

                //Call API to Remove Food in Favorite.
                var remove:Call<ResponseSuccess> = retro.removeFavorite(idFood,glbl.id!!.toInt())
                remove.enqueue(object :retrofit2.Callback<ResponseSuccess>{
                    override fun onResponse(
                        call: Call<ResponseSuccess>,
                        response: Response<ResponseSuccess>
                    ) {
                        Log.e("announce",response.body().msg.toString())
                    }

                    override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
                flag = false
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FoodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FoodFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun getData(){
        val bundle = arguments
        area = bundle?.getString("area")
        idFood = bundle?.getString("idFood")!!.toIntOrNull()
        Log.e("data2",idFood.toString())
        nameFoodType = bundle?.getString("typeFood")
        typeFood.text = nameFoodType
        glbl.id = bundle?.getString("idUser")!!.toIntOrNull().toString()
        Log.e("data2",glbl.id.toString())

        if(area == "FoodSearch"){
            searchChar = bundle.getString("searchChar")
        }

        if(area == "ListFood"){
            idFoodType = bundle?.getInt("idFoodType")
            Log.e("data2",idFoodType.toString())
            glbl.token = bundle?.getString("token")
            Log.e("data2",glbl.token.toString())
            glbl.fullname = bundle?.getString("fullname")
            Log.e("data2",glbl.fullname.toString())
            glbl.roleId = bundle?.getString("roleId")
            Log.e("data2",glbl.roleId.toString())
        }
    }
}


