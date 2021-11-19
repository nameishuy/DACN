package com.example.myapplication.RecyclerViewAdapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.myapplication.FoodFragment
import com.example.myapplication.R
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.FoodMostLikes
import com.example.myapplication.model.ListFood.ListFavorite
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_food.*
import retrofit2.Call
import retrofit2.Response

class RecyclerViewAdapterTop6Foods(private val list:List<FoodMostLikes.toplikeFood>, private val idUser:String):RecyclerView.Adapter<RecyclerViewAdapterTop6Foods.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_favorite,parent,false)
        return ViewHolder(v)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        Picasso.get().load(list[position].img).into(holder.foodPicture)
        holder.foodType.text = list[position].nameTypeFood
        holder.foodName.text = list[position].nameFood
        holder.totalLike.text = list[position].totalLike.toString()
        holder.idFood = list[position].idFood
        Log.e("idFood",holder.idFood.toString())


        //Set event click for cardView
        holder.cardFood.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val bundle = Bundle()
                var area:String = "HomeFragment"
                bundle.putString("area",area)
                bundle.putString("idFood", list[position].idFood!!.toString())
                bundle.putString("idUser",idUser)
                bundle.putString("typeFood",list[position].nameTypeFood)
                val activity=v!!.context as AppCompatActivity
                val Food = FoodFragment()
                Food.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer,Food).addToBackStack(Fragment::class.java.simpleName).commit()
            }
        })
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var cardFood: CardView
        var foodPicture: ImageView
        var foodName: TextView
        var totalLike: TextView
        var foodType: TextView
        var iconLike:ImageView
        var idFood:Int?=null
        init {
            cardFood = itemView.findViewById(R.id.foodCard)
            foodPicture = itemView.findViewById(R.id.foodPicture)
            foodName = itemView.findViewById(R.id.foodName)
            totalLike = itemView.findViewById(R.id.totalLike)
            foodType = itemView.findViewById(R.id.foodType)
            iconLike = itemView.findViewById(R.id.totalLikeIcon)
        }
    }
}