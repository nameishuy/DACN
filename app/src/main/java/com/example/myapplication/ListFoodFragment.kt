package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.ListFoodResponse
import retrofit2.Call
import retrofit2.Response

class ListFoodFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    fun getData(){
        val bundle = arguments
        val idFoodType = bundle!!.getInt("idChoose")
        val nameFoodType = bundle.getString("type")
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        var list:Call<ListFoodResponse> = retro.getList(idFoodType)
        list.enqueue(object :retrofit2.Callback<ListFoodResponse>{
            override fun onResponse(
                call: Call<ListFoodResponse>,
                response: Response<ListFoodResponse>
            ) {
                val data = response!!.body()
                for (item:ListFoodResponse.Food in data.data!!.list!!){
                    Log.e("data",item.nameFood.toString())
                }
            }

            override fun onFailure(call: Call<ListFoodResponse>, t: Throwable) {
                Log.e("Error", t.message!!)
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
         * @return A new instance of fragment ListFoodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFoodFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}