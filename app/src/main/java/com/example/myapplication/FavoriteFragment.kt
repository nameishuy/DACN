package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.RecyclerViewAdapter.RecyclerViewAdapterListFavorite
import com.example.myapplication.RecyclerViewAdapter.RecyclerViewAdapterListFood
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.ListFavorite
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_list_food.*
import retrofit2.Call
import retrofit2.Response

class FavoriteFragment : Fragment() {

    val glbl = Global() //init global object
    private var layoutManager : RecyclerView.LayoutManager?=null
    private var adapter : RecyclerView.Adapter<RecyclerViewAdapterListFavorite.ViewHolder>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData() //get data user from Menu Food Activity and call list favorite of user then show up on recycler view


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FavoriteFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun getData(){
        val bundle = arguments
        glbl.id = bundle!!.getString("id")
        //Call API
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        var listFavorite:Call<ListFavorite> = retro.getListFavorite(glbl.id!!.toInt())
        listFavorite.enqueue(object :retrofit2.Callback<ListFavorite>{
            override fun onResponse(call: Call<ListFavorite>, response: Response<ListFavorite>) {
                //Get data from Menu Food Activity

                layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
                recyclerViewListFavorite.layoutManager = layoutManager
                adapter = RecyclerViewAdapterListFavorite(response.body().data!!.list!!,glbl.id!!)
                recyclerViewListFavorite.adapter = adapter
            }

            override fun onFailure(call: Call<ListFavorite>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }
}