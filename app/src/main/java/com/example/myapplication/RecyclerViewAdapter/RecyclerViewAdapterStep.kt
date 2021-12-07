package com.example.myapplication.RecyclerViewAdapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Global
import com.example.myapplication.R
import com.example.myapplication.model.ListFood.addFoodPost
import kotlin.contracts.Returns

class RecyclerViewAdapterStep(private val listStepNum:ArrayList<Int>, private var glbl: Global?, val context: Context, var addNewStep:TextView)
    :RecyclerView.Adapter<RecyclerViewAdapterStep.ViewHolder>() {

    private var chooseStep:String? = null
    private var flag:Boolean =false
    private var ListPos:ArrayList<Int> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterStep.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.card_liststep,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterStep.ViewHolder, position: Int) {
        holder.itemNumberStep.text = listStepNum[position].toString()
        Log.e("Postion",""+position)
        holder.inputStep.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                chooseStep = holder.inputStep.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        if(flag == true){
            try {
                holder.inputStep.setText(glbl!!.ListStep.elementAt(position))
            }catch (e:IndexOutOfBoundsException){

            }
        }
    }

    override fun getItemCount(): Int {
        return listStepNum.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var inputStep:EditText
        var btnMenuStep: Button
        var itemNumberStep: TextView
        init {
            inputStep =itemView.findViewById(R.id.inputStep)
            itemNumberStep=itemView.findViewById(R.id.NumberItemStep)
            btnMenuStep =itemView.findViewById(R.id.MenuItemStep)
            btnMenuStep.setOnClickListener { popupStep(it,adapterPosition) }
            addNewStep.setOnClickListener { addNewStep() }

        }
    }

    private fun popupStep(v :View,pos:Int){
        val popupMenuStep = PopupMenu(context,v)
        popupMenuStep.menu.add(Menu.NONE,0,0,"Hoàn Thành")
        popupMenuStep.menu.add(Menu.NONE,1,1,"Cập Nhật")
        popupMenuStep.menu.add(Menu.NONE,2,2,"Xoá Bước")
        popupMenuStep.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId
            if(id==0){
                var check:Boolean =false
                for(item in glbl!!.ListStep){
                    if(item.equals(chooseStep)){
                        check =true
                        break
                    }
                }
                if(check ==false){
                    glbl!!.ListStep.add(chooseStep.toString())
                    ListPos.clear()
                    for (i in 0 until glbl!!.ListStep.size) {
                        ListPos.add(i+1)
                    }
                    Toast.makeText(context,"Thêm bước hoàn tất",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Bước đã được thêm",Toast.LENGTH_LONG).show()
                    check =false
                }
            }
            if(id==1){
                try {
                    glbl!!.ListStep.set(pos,chooseStep.toString())
                    ListPos.clear()
                    for (i in 0 until glbl!!.ListStep.size) {
                        ListPos.add(i+1)
                    }
                    Toast.makeText(context,"Cập nhật hoàn tất"
                        ,Toast.LENGTH_LONG).show()
                }catch (e:IndexOutOfBoundsException){
                    Toast.makeText(context,"Vui lòng chọn hoàn thành trước khi cập nhật"
                        ,Toast.LENGTH_LONG).show()
                }
            }
            if(id==2){
                glbl?.listStepRecyclerView?.removeAt(pos)
                glbl!!.ListStep.removeAt(pos)
                ListPos.clear()
                for (i in 0 until glbl!!.ListStep.size) {
                    ListPos.add(i+1)
                }
                flag =true
                notifyDataSetChanged()
                flag =false
            }
            false
        }
        popupMenuStep.show()
    }
    private fun addNewStep(){
        var NewItem:Int =1
        var check:Boolean =false
        for (item in listStepNum){
            if(item == NewItem){
                NewItem++
            }else{
                check =true
                glbl?.listStepRecyclerView?.add(NewItem)
                saptang(glbl!!.listStepRecyclerView)
                flag =true
                notifyDataSetChanged()
                flag =false
                break
            }
        }
        if(check ==false){
            glbl?.listStepRecyclerView?.add(NewItem)
            saptang(glbl!!.listStepRecyclerView)
            flag =true
            notifyDataSetChanged()
            flag =false
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