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
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.RecyclerViewAdapter.RecyclerViewAdapterFoodSearch
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.ListFoodSearch
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.recyclerViewFoods
import retrofit2.Response

class SearchFragment : Fragment() {

    val glbl = Global()
    var searchChar:String?=null
    private var layoutManager : RecyclerView.LayoutManager?=null
    private var adapter : RecyclerView.Adapter<RecyclerViewAdapterFoodSearch.ViewHolder>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData() //get Data from Home Fragment

        //Set hint for search text.
        search_txt.hint = searchChar

        //Set event click for button back.
        btn_back.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val bundle = Bundle()
                bundle.putString("id",glbl.id)
                bundle.putString("roleId",glbl.roleId)
                val activity=v!!.context as AppCompatActivity
                val Home = HomeFragment()
                Home.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer,Home).addToBackStack(Fragment::class.java.simpleName).commit()
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    //Get Data From Home Fragment.
    fun getData(){
        val bundle = arguments
        if(bundle!!.getString("id") != null){
            glbl.id = bundle!!.getString("id")
            Log.e("data",glbl.id.toString())
        }
        if(bundle!!.getString("roleId") != null){
            glbl.roleId = bundle!!.getString("roleId")
            Log.e("data",glbl.roleId.toString())
        }
        if(bundle!!.getString("searchChar") != null){
            searchChar = bundle!!.getString("searchChar")
            Log.e("data",searchChar.toString())
        }

        //Call API to get List Food
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        var searchtext:retrofit2.Call<ListFoodSearch> = retro.getListFood(searchChar)
        searchtext.enqueue(object :retrofit2.Callback<ListFoodSearch>{
            override fun onResponse(
                call: retrofit2.Call<ListFoodSearch>,
                response: Response<ListFoodSearch>
            ) {
                val data = response.body()
                if(data.data!!.list!!.size == 0){
                    noFound.isVisible = true
                    recyclerViewFoods.isVisible = false
                }else{
                    layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
                    recyclerViewFoods.layoutManager = layoutManager
                    adapter = RecyclerViewAdapterFoodSearch(data!!.data!!.list!!,data.data!!.list!!.size, glbl.id.toString(),
                        searchChar.toString()
                    )
                    recyclerViewFoods.adapter = adapter
                }
            }

            override fun onFailure(call: retrofit2.Call<ListFoodSearch>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }
}