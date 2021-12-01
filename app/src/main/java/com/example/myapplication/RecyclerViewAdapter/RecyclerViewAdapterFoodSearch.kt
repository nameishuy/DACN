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
import com.example.myapplication.R
import com.example.myapplication.model.ListFood.ListFoodSearch
import com.squareup.picasso.Picasso

class RecyclerViewAdapterFoodSearch(private val list: List<ListFoodSearch.Food>,
                                    private val size:Int,
                                    private val idUser:String,
                                    private  val searchChar:String):RecyclerView.Adapter<RecyclerViewAdapterFoodSearch.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterFoodSearch.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_listfood,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterFoodSearch.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        Picasso.get().load(list[position].imageFood).into(holder.imageView)
        holder.textView.text = list[position].nameFood

        //set event click listener for holder
        holder.cardView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val bundle = Bundle()
                var area:String = "FoodSearch"
                bundle.putString("area",area)
                bundle.putString("idFood",list[position].idFood!!.toString())
                bundle.putString("typeFood",list[position].nametypeFood)
                bundle.putString("idUser",idUser)
                bundle.putString("searchChar",searchChar)
                val activity=v!!.context as AppCompatActivity
                val Food = FoodFragment()
                Food.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer,Food).addToBackStack(Fragment::class.java.simpleName).commit()
            }
        })
    }

    override fun getItemCount() = size

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var imageView: ImageView
        var textView: TextView
        var cardView: CardView
        init {
            imageView = itemView.findViewById(R.id.imageFood)
            textView = itemView.findViewById(R.id.nameFood)
            cardView = itemView.findViewById(R.id.card_listfood)
        }
    }
}