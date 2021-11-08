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
import com.example.myapplication.Global
import com.example.myapplication.R
import com.example.myapplication.model.ListFood.ListFoodResponse
import com.squareup.picasso.Picasso

class RecyclerViewAdapterListFood(private val list:List<ListFoodResponse.Food>,private var glbl:Global?,private val nameFoodType:String?,private val idFoodType:Int?):RecyclerView.Adapter<RecyclerViewAdapterListFood.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_listfood,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        Picasso.get().load(list[position].imageFood).into(holder.imageView)
        holder.textView.text = list[position].nameFood

        //set event click listener for holder
        holder.cardView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val bundle = Bundle()
                bundle.putString("idFood",list[position].idFood.toString())
                Log.e("data1",bundle.getString("idFood").toString())
                bundle.putString("idUser",glbl!!.id.toString())
                Log.e("data1",bundle.getString("idUser").toString())
                bundle.putString("token", glbl!!.token)
                bundle.putString("fullname", glbl!!.fullname)
                bundle.putString("roleId", glbl!!.roleId)
                bundle.putInt("idFoodType", idFoodType!!)
                Log.e("data1",bundle.getInt("idFoodType").toString())
                bundle.putString("typeFood",nameFoodType)
                Log.e("data1",bundle.getString("typeFood").toString())
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
        var imageView:ImageView
        var textView:TextView
        var cardView:CardView
        init {
            imageView = itemView.findViewById(R.id.imageFood)
            textView = itemView.findViewById(R.id.nameFood)
            cardView = itemView.findViewById(R.id.card_listfood)
        }
    }
}