package com.example.myapplication.RecyclerViewAdapter

import android.annotation.SuppressLint
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
import com.example.myapplication.FoodFragment
import com.example.myapplication.ListFoodFragment
import com.example.myapplication.R
import com.example.myapplication.model.ListFood.ListFavorite
import com.squareup.picasso.Picasso

class RecyclerViewAdapterListFavorite(private val list:List<ListFavorite.Favorite>, private var idUser:String):RecyclerView.Adapter<RecyclerViewAdapterListFavorite.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterListFavorite.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_favorite,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        Picasso.get().load(list[position].img).into(holder.foodPicture)
        holder.foodName.text = list[position].nameFood
        holder.totalLike.text = list[position].totalLike.toString()

        //set event click on holder
        holder.cardFavorite.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val bundle = Bundle()
                var area:String = "ListFavorite"
                bundle.putString("area",area)
                bundle.putString("idFood", list[position].foodId!!.toString())
                bundle.putString("idUser",idUser)
                val activity=v!!.context as AppCompatActivity
                val Food = FoodFragment()
                Food.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer,Food).addToBackStack(Fragment::class.java.simpleName).commit()
            }
        })
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var cardFavorite:CardView
        var foodPicture:ImageView
        var foodName:TextView
        var totalLike:TextView
        init {
            cardFavorite = itemView.findViewById(R.id.foodCard)
            foodPicture = itemView.findViewById(R.id.foodPicture)
            foodName = itemView.findViewById(R.id.foodName)
            totalLike = itemView.findViewById(R.id.totalLike)
        }
    }
}