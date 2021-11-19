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
import com.example.myapplication.Global
import com.example.myapplication.ListFoodFragment
import com.example.myapplication.R
import com.example.myapplication.model.ListFood.FoodMostLikes
import com.example.myapplication.model.ListFood.FoodTypeResponse
import com.squareup.picasso.Picasso

class RecyclerViewAdapterFoodTypes(private val list: List<FoodTypeResponse.FoodType>, private val glbl:Global) :RecyclerView.Adapter<RecyclerViewAdapterFoodTypes.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_foodtype,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        Picasso.get().load(list[position].image).into(holder.itemImage)
        holder.itemTitle.text = list[position].name
        val id:Int = list[position].id!!
        val type:String = list[position].name.toString()

        //set event click listener
        holder.cardView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val bundle = Bundle()
                bundle.putInt("idChoose",id)
                bundle.putString("type",type)
                bundle.putString("token",glbl.token)
                bundle.putString("fullname",glbl.fullname)
                bundle.putString("id",glbl.id)
                bundle.putString("roleId",glbl.roleId)
                Log.e("data",glbl.roleId.toString())
                val activity=v!!.context as AppCompatActivity
                val ListFood = ListFoodFragment()
                ListFood.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer,ListFood).addToBackStack(Fragment::class.java.simpleName).commit()
            }
        })
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTitle:TextView
        var cardView:CardView
        init {
            itemImage=itemView.findViewById(R.id.iconFoodType)
            itemTitle=itemView.findViewById(R.id.nameFoodType)
            cardView=itemView.findViewById(R.id.cardView)
        }

    }

}