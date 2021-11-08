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
import com.example.myapplication.model.ListFood.FoodResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_food.*
import kotlinx.android.synthetic.main.fragment_food.btnBack
import kotlinx.android.synthetic.main.fragment_list_food.*
import retrofit2.Call
import retrofit2.Response


class FoodFragment : Fragment() {

    var idFood:Int?=null
    var nameFoodType:String?=null
    var idFoodType:Int?=null
    var flag:Boolean = false
    val glbl = Global()

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
            }
        })
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        var food:Call<FoodResponse> = retro.getDetail(idFood)
        food.enqueue(object :retrofit2.Callback<FoodResponse>{
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                val data = response.body()
                Picasso.get().load(data.data!!.infoFood!!.img.toString()).into(imgFood)
                nameFood.text = data.data!!.infoFood!!.name.toString()
                titleFavoriteButton.text = "Thích (" + data.data!!.infoFood!!.totallike!! + ")"
                description.text = data.data!!.infoFood!!.description
                for(item:FoodResponse.recipe in data.data!!.listRecipes!!){
                    if(item == data.data!!.listRecipes!![0]){
                        recipe.append("\u2022 " + item.material + ":\t\t" + item.quantity)
                    }else{
                        recipe.append("\n\u2022 " + item.material + ":\t\t" + item.quantity)
                    }
                }

                // set event click of like button
                favoriteButton.setOnClickListener {
                    if(flag == false){
                        favoriteIcon.setColorFilter(resources.getColor(R.color.red))
                        titleFavoriteButton.text = "Đã Thích (" + (data.data!!.infoFood!!.totallike!!+1) + ")"
                        titleFavoriteButton.setTextColor(resources.getColor(R.color.red))
                        flag = true
                    }else{
                        favoriteIcon.setColorFilter(null)
                        titleFavoriteButton.text = "Thích (" + (data.data!!.infoFood!!.totallike!! - 1) + ")"
                                titleFavoriteButton.setTextColor(resources.getColor(R.color.black))
                        flag = false
                    }
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {

            }
        })
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
        glbl.id = bundle?.getString("idUser")!!.toIntOrNull().toString()
        Log.e("data2",glbl.id.toString())
        glbl.token = bundle?.getString("token")
        Log.e("data2",glbl.token.toString())
        glbl.fullname = bundle?.getString("fullname")
        Log.e("data2",glbl.fullname.toString())
        glbl.roleId = bundle?.getString("roleId")
        Log.e("data2",glbl.roleId.toString())
        idFoodType = bundle?.getInt("idFoodType")
        Log.e("data2",idFoodType.toString())
        idFood = bundle?.getString("idFood")!!.toIntOrNull()
        Log.e("data2",idFood.toString())
        nameFoodType = bundle?.getString("typeFood")
        typeFood.text = nameFoodType
    }
}