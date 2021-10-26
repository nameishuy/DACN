package com.example.myapplication.RecyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.ListFood.ListFoodResponse
import com.squareup.picasso.Picasso

class RecyclerViewAdapterListFood(private val list:List<ListFoodResponse.Food>):RecyclerView.Adapter<RecyclerViewAdapterListFood.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_listfood,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(list[position].imageFood).into(holder.imageView)
        holder.textView.text = list[position].nameFood
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