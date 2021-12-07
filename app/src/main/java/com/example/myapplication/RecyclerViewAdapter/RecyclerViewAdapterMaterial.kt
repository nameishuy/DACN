package com.example.myapplication.RecyclerViewAdapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.FoodFragment
import com.example.myapplication.Global
import com.example.myapplication.R
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.MaterialPost
import com.example.myapplication.model.ListFood.MaterialResponse
import com.example.myapplication.model.ListFood.addFoodPost
import kotlinx.android.synthetic.main.fragment_new_add_food.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerViewAdapterMaterial(private val list:ArrayList<Int>,private var glbl: Global?,val context:Context,var addNewMaterial: TextView)
    :RecyclerView.Adapter<RecyclerViewAdapterMaterial.ViewHolder>(){

    private var SelectedMaterialSpinner:String=""
    private var ChooseMaterialId:Int = 0
    private var ChooseMaterialName:String? =null
    private var NewMaterialName:String? = null
    private var inputQuatity:String? = null
    private var flag:Boolean = false
    private var ChoosePostion:Int =0

    private var ListvtMaterial = mutableListOf<Int>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterMaterial.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.card_listmaterial,parent,false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: RecyclerViewAdapterMaterial.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.itemNumber.text = list[position].toString()
        getMaterial(holder.SpinerMaterial,list[position],holder.inputQuatity)
        ChoosePostion =position
        Log.e("position",""+position)
        holder.inputQuatity.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputQuatity = holder.inputQuatity.text.toString().trim()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        holder.menuItemMaterial.setOnClickListener {
            popupMaterial(it,position,holder.SpinerMaterial,holder.inputQuatity)
            flag =false
        }
    }

    override fun getItemCount():Int{
        return list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemNumber: TextView
        var menuItemMaterial: Button
        var SpinerMaterial:Spinner
        var inputQuatity:EditText
        init {
            itemNumber =itemView.findViewById(R.id.NumberItemMaterial)
            menuItemMaterial =itemView.findViewById(R.id.MenuItemMaterial)
            SpinerMaterial =itemView.findViewById(R.id.SpinnerAMaterial)
            inputQuatity =itemView.findViewById(R.id.inputQuantity)
            addNewMaterial.setOnClickListener {
                ListvtMaterial.clear()
                addNewMaterial(SpinerMaterial,adapterPosition,inputQuatity)
                inputQuatity.setText("")
            }
        }
    }


    private fun popupMaterial(v :View,pos:Int,SpinnerNew: Spinner,Quatity:EditText){
        val popupMenuMaterial = PopupMenu(context,v)
        popupMenuMaterial.menu.add(Menu.NONE,0,0,"Hoàn Thành")
        popupMenuMaterial.menu.add(Menu.NONE,1,1,"Cập Nhật")
        popupMenuMaterial.menu.add(Menu.NONE,2,2,"Thêm Mới Nguyên Liệu")
        popupMenuMaterial.menu.add(Menu.NONE,3,3,"Xoá Nguyên Liệu")
        popupMenuMaterial.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId
            if(id==0){
                var check:Boolean =false
                for(item in glbl!!.ListMaterialQuatity){
                    if(item.pstMaterialId!!.equals(ChooseMaterialId) && item.pstQuantity.equals(inputQuatity)){
                        check =true
                        break
                    }
                }
                if(check ==false){
                    val newFood = addFoodPost.recipe()
                    newFood.pstMaterialId =ChooseMaterialId
                    newFood.pstQuantity = inputQuatity
                    glbl?.ListMaterialQuatity?.add(newFood)
                    glbl!!.ListQuatity.add(inputQuatity.toString())
                    glbl!!.ListNameChooseMaterial.add(ChooseMaterialName.toString())
                    Toast.makeText(context,"Thêm nguyên liêu hoàn tất",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Nguyên liêu đã tồn tại",Toast.LENGTH_LONG).show()
                    check =false
                }
            }
            if(id==1){
                try {
                    Log.e("Pos",": "+pos)
                    val newFood = addFoodPost.recipe()
                    newFood.pstMaterialId =ChooseMaterialId
                    newFood.pstQuantity = inputQuatity
                    glbl?.ListMaterialQuatity?.set(pos,newFood)
                    glbl?.ListQuatity?.set(pos,inputQuatity.toString())
                    glbl!!.ListNameChooseMaterial.set(pos,ChooseMaterialName.toString())
                    Toast.makeText(context,"Cập nhật hoàn tất"
                        ,Toast.LENGTH_LONG).show()
                }catch (e:IndexOutOfBoundsException){
                    Toast.makeText(context,"Vui lòng chọn hoàn thành trước khi cập nhật"
                        ,Toast.LENGTH_LONG).show()
                }
            }
            if(id==2){
                openPopupAddNewFood(Gravity.CENTER,SpinnerNew,Quatity)
            }
            if(id==3){
                ListvtMaterial.clear()
                Log.e("Pos Del",": "+ChoosePostion)
                glbl?.listMaterialRecyclerView?.removeAt(pos)
                try {
                    Log.e("Pos Del",": "+pos)
                    glbl?.ListMaterialQuatity?.removeAt(pos)
                    glbl?.ListQuatity?.removeAt(pos)
                    glbl!!.ListNameChooseMaterial.removeAt(pos)
                }catch (e:IndexOutOfBoundsException){
                }
                Log.e("list Now",": "+list.size)
                flag =true
                getMaterial(SpinnerNew,pos,Quatity)
                notifyDataSetChanged()
            }
            false
        }
        popupMenuMaterial.show()
    }

    private fun addNewMaterial(SpinnerNew: Spinner,Pos: Int,Quatity: EditText){
        var NewItem:Int =1
        var check:Boolean =false
        for (item in list){
            if(item == NewItem){
                NewItem++
            }else{
                check =true
                glbl?.listMaterialRecyclerView?.add(NewItem)
                saptang(glbl!!.listMaterialRecyclerView)
                flag =true
                getMaterial(SpinnerNew,Pos,Quatity)
                notifyDataSetChanged()
                break
            }
        }
        if(check ==false){
            glbl?.listMaterialRecyclerView?.add(NewItem)
            saptang(glbl!!.listMaterialRecyclerView)
            flag =true
            getMaterial(SpinnerNew,Pos,Quatity)
            notifyDataSetChanged()
        }
    }

    private fun openPopupAddNewFood(gravity:Int,SpinnerNew: Spinner,Quatity: EditText){
        var dialog: Dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_popup_addnew_material)

        var window: Window = dialog.window!!
        if(window !=null){
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            var windowAttributes: WindowManager.LayoutParams =window.attributes
            windowAttributes.gravity =gravity
            window.attributes = windowAttributes
        }
        if(Gravity.CENTER ==gravity){
            dialog.setCancelable(true)
        }else{
            dialog.setCancelable(false)
        }

        var NameMaterial:EditText =dialog.findViewById(R.id.inputANewMaterial)
        var btnAdd:Button = dialog.findViewById(R.id.btnAddANewMaterial)
        var btnExit:Button =dialog.findViewById(R.id.btnExitAddNewMaterial)

        btnExit.setOnClickListener {
            dialog.dismiss()
        }
        btnAdd.setOnClickListener {
            NewMaterialName =NameMaterial.text.toString().trim()
            AddNewMaterial(SpinnerNew,Quatity)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun getMaterial(spinnerMaterial:Spinner,Pos:Int,Quatity: EditText){
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        retro.getDataMaterial().enqueue(object : Callback<MaterialResponse> {
            override fun onResponse(
                call: Call<MaterialResponse>?,
                response: Response<MaterialResponse>?
            ) {
                if(response!=null){
                    if(response.isSuccessful){
                        for(item in response.body().listMaterial!!){
                            glbl?.ListMaterial?.add(item.name!!)
                            glbl?.ListMaterialId?.add(item.id!!)
                        }
                        val adapter = ArrayAdapter(context
                            ,android.R.layout.simple_spinner_item, glbl!!.ListMaterial)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerMaterial.adapter = adapter
                        if(NewMaterialName.equals("")==false){
                            var PositionNewMaterial = adapter.getPosition(NewMaterialName)
                            spinnerMaterial.setSelection(PositionNewMaterial)
                        }
                        for (item in glbl!!.listMaterialRecyclerView){
                            Log.e("List Material",""+item)
                        }
                        if(flag ==true){
                            var Dem:Int =0
                            for (item in ListvtMaterial){
                                if(item == Dem){
                                    break
                                }
                                Dem++
                            }
                            Log.e("POS",""+Pos)
                            Log.e("Dem",""+Dem)
                            try {
                                var PositionMaterial:Int = adapter.getPosition(glbl!!.ListNameChooseMaterial.elementAt(Dem))
                                Log.e("Material Name",""+glbl!!.ListNameChooseMaterial.elementAt(Dem))
                                Log.e("Vt trong Spinner",""+PositionMaterial)
                                spinnerMaterial.setSelection(PositionMaterial)
                                Quatity.setText(glbl!!.ListQuatity.elementAt(Dem))
                            }catch (e:IndexOutOfBoundsException){

                            }
                            if(glbl!!.listMaterialRecyclerView.size == Dem+1){
                                flag =false
                            }
                        }
                        spinnerMaterial.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
                            ) {
                                val selectedItem = parent!!.getItemAtPosition(position)
                                SelectedMaterialSpinner = selectedItem.toString()
                                ChooseMaterialId = glbl!!.ListMaterialId.elementAt(position)
                                ChooseMaterialName =glbl!!.ListMaterial.elementAt(position)
                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
                        }
                    }else{
                        Log.e("Lỗi 1",":"+ response.message())
                    }
                }
            }

            override fun onFailure(call: Call<MaterialResponse>?, t: Throwable?) {
                Log.e("Lỗi 2", t!!.message!!)
            }

        })
    }

    private fun AddNewMaterial(SpinnerNew:Spinner,Quatity: EditText){
        if(NewMaterialName!!.isEmpty()){
            Toast.makeText(context,"Bạn Chưa Đặt Tên Gia Vị"
                ,Toast.LENGTH_LONG).show()
        }else{
            val retro = Retro().getRetroClientInstance().create(API::class.java)
            val NewMaterialPost = MaterialPost(""+NewMaterialName)
            retro.addNewMaterial(NewMaterialPost).enqueue(object : Callback<MaterialPost> {
                override fun onResponse(
                    call: Call<MaterialPost>?,
                    response: Response<MaterialPost>?
                ) {
                    if (response != null) {
                        if(response.isSuccessful){
                            Toast.makeText(context,"Thêm Mới Thành Công"
                                , Toast.LENGTH_LONG).show()
                            getMaterial(SpinnerNew,-1,Quatity)
                            notifyDataSetChanged()
                            Log.e("Thành Công","")
                        }else{
                            Log.e("Lỗi 1",":"+response.message())
                        }
                    }
                }

                override fun onFailure(call: Call<MaterialPost>?, t: Throwable?) {
                    if (t != null) {
                        Log.e("Lỗi 2",":"+t.message)
                    }
                }

            })
        }
    }

    private fun saptang(list:ArrayList<Int>){
        for (i in 0 until list.size) {
            for (j in i + 1 until list.size) {
                if (list[i] > list[j]) {
                    val temp: Int = list.get(i)
                    list[i] = list[j]
                    list[j] = temp
                }
            }
        }
    }

}