package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.RecyclerViewAdapter.RecyclerViewAdapterFoodTypes
import com.example.myapplication.RecyclerViewAdapter.RecyclerViewAdapterListFood
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.ListFoodResponse
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_list_food.*
import kotlinx.android.synthetic.main.fragment_list_food.noFound
import kotlinx.android.synthetic.main.fragment_list_food.recyclerViewFoods
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Response

class ListFoodFragment : Fragment() {

    val glbl=Global()
    private var layoutManager : RecyclerView.LayoutManager?=null
    private var adapter : RecyclerView.Adapter<RecyclerViewAdapterListFood.ViewHolder>?=null
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

        //set event click listener for button back
        btnBack.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val activity=v!!.context as AppCompatActivity
                val Home = HomeFragment()
                val bundle = Bundle()
                Home.arguments = bundle
                bundle.putString("token",glbl.token)
                bundle.putString("fullname",glbl.fullname)
                bundle.putString("id",glbl.id)
                bundle.putString("roleId",glbl.roleId)
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer,Home).addToBackStack(Fragment::class.java.simpleName).commit()
            }
        })
    }

    fun getData(){
        val bundle = arguments
        val idFoodType = bundle!!.getInt("idChoose")
        val nameFoodType = bundle.getString("type")
        if(bundle!!.getString("token") != null){
            glbl.token = bundle!!.getString("token")
            Log.e("data",glbl.token.toString())
        }
        if(bundle!!.getString("fullname") != null){
            glbl.fullname = bundle!!.getString("fullname")
            Log.e("data",glbl.fullname.toString())
        }
        if(bundle!!.getString("id") != null){
            glbl.id = bundle!!.getString("id")
            Log.e("data",glbl.id.toString())
        }
        if(bundle!!.getString("roleId") != null){
            glbl.roleId = bundle!!.getString("roleId")
            Log.e("data",glbl.roleId.toString())
        }

        val retro = Retro().getRetroClientInstance().create(API::class.java)
        var list:Call<ListFoodResponse> = retro.getList(idFoodType)
        list.enqueue(object :retrofit2.Callback<ListFoodResponse>{
            override fun onResponse(
                call: Call<ListFoodResponse>?,
                response: Response<ListFoodResponse>?
            ) {
                if(response?.body()?.msg!! == "Không tìm thấy"){
                    noFound.isVisible = true
                    recyclerViewFoods.isVisible = false
                }else{
                    layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
                    recyclerViewFoods.layoutManager = layoutManager
                    adapter = RecyclerViewAdapterListFood(response?.body()?.data!!.list!!,glbl,nameFoodType,idFoodType)
                    recyclerViewFoods.adapter = adapter
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