package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.User.UserInfoResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*
import kotlinx.android.synthetic.main.fragment_user_update.*
import kotlinx.android.synthetic.main.fragment_user_update.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFragment : Fragment() {
    var PhoneNumUser:String? = null
    var BirthDayUser:String? = null
    var RegionUser:String? = null
    var ImageUser:String? = null
    var fullNameUser:String? = null
    var idUser:String? = null
    var RoleUser:String? = null
    var FlagUpdateInfo:Boolean =false
    var FlagAddNewFood:Boolean =false
    var FlagChangePass:Boolean =false

    private val rotateOpen:Animation by lazy { AnimationUtils.loadAnimation(this.context,R.anim.rotate_open_anim) }
    private val rotateClose:Animation by lazy { AnimationUtils.loadAnimation(this.context,R.anim.rotate_close_anim) }
    private val fromBottom:Animation by lazy { AnimationUtils.loadAnimation(this.context,R.anim.from_bottom_anim) }
    private val fromTop:Animation by lazy { AnimationUtils.loadAnimation(this.context,R.anim.from_top_anim) }
    private val fromLeft:Animation by lazy { AnimationUtils.loadAnimation(this.context,R.anim.from_left_anim) }
    private val toBottom:Animation by lazy { AnimationUtils.loadAnimation(this.context,R.anim.to_bottom_anim) }
    private val toTop:Animation by lazy { AnimationUtils.loadAnimation(this.context,R.anim.to_top_anim) }
    private val toLeft:Animation by lazy { AnimationUtils.loadAnimation(this.context,R.anim.to_left_anim) }
    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_user, container, false)
        view.btnUpdateInfo.setOnClickListener {
            FlagUpdateInfo =true
            TransDataToUserUpdate()
        }
        view.btnAddFood.setOnClickListener {
            FlagAddNewFood =true
            TransDataToUserUpdate()
        }
        view.btnChangePassWord.setOnClickListener {
            FlagChangePass =true
            TransDataToUserUpdate()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataToHome()

        //Set event click for floating Menu Button
        floatingButtonMenu.setOnClickListener {
            onAddButtonClicked()
        }

        //Set event click for Exit Button.
        btnExit.setOnClickListener{
            val i: Intent = Intent(this.context, Login::class.java)
            startActivity(i)
        }
    }
    private fun getDataToHome(){
        val bundle = arguments

        if(bundle!!.getString("id") != null){
            idUser = bundle!!.getString("id")
            Log.e("data id User",""+idUser)
        }
        if(bundle!!.getString("roleId") != null){
            RoleUser = bundle!!.getString("roleId")
            Log.e("data Role",""+RoleUser)
        }

        val retro = Retro().getRetroClientInstance().create(API::class.java)
        retro.getUserInfo(idUser!!.toInt()).enqueue(object: Callback<UserInfoResponse>{
            override fun onResponse(
                call: Call<UserInfoResponse>?,
                response: Response<UserInfoResponse>?
            ) {
                val info = response!!.body()

                if (response != null) {
                    if(response.isSuccessful){

                        if(info.data!!.fullName.equals("null")==false){
                            fullNameUser = info.data!!.fullName
                            UserName.text = fullNameUser
                            Log.e("data Name ",""+fullNameUser)
                        }else{
                            UserName.text = "Bạn chưa cập nhật tên"
                            Log.e("data Name ",""+fullNameUser)
                        }

                        if(info.data!!.phoneNumUser.equals("null")==false){
                            PhoneNumUser = info.data!!.phoneNumUser
                            UserPhone.text =PhoneNumUser
                            Log.e("data Phone",""+PhoneNumUser)
                        }else{
                            UserPhone.text = "Bạn chưa cập nhật số điện thoại"
                            Log.e("data Phone",""+PhoneNumUser)
                        }

                        if(info.data!!.birthdayUser.equals("null")==false){
                            BirthDayUser = info.data!!.birthdayUser
                            UserBirtDay.text=BirthDayUser
                            Log.e("data Birth Day",""+BirthDayUser)
                        }else{
                            UserBirtDay.text = "Bạn chưa cập nhật ngày sinh"
                            Log.e("data Birth Day ",""+BirthDayUser)
                        }

                        if(info.data!!.regionUser.equals("null")==false){
                            RegionUser = info.data!!.regionUser
                            UserRegion.text = RegionUser
                            Log.e("data Region  1",""+RegionUser)
                        }else{
                            UserRegion.text = "Bạn chưa cập nhật địa chỉ"
                            Log.e("data Region 2",""+RegionUser)
                        }

                        if(info.data!!.imageUser.equals("null")==false){
                            ImageUser = info.data!!.imageUser
                            Picasso.get().load(ImageUser).into(UserInfoImage)
                            Log.e("data Image",""+ImageUser)
                        }else{
                            UserInfoImage.setImageResource(R.drawable.logo)
                        }


                    }else{
                        Log.e("Lỗi 1",":"+response.message())
                    }
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>?, t: Throwable?) {
                if (t != null) {
                    Toast.makeText(activity,"Đăng Ký Thất Bại"+t.message, Toast.LENGTH_LONG).show()
                    Log.e("Lỗi 2",":"+t.message)
                }
            }

        })

    }

    private fun TransDataToUserUpdate(){
        val i = Intent(this@UserFragment.requireContext(),User::class.java)
        i.putExtra("TransId",idUser)
        i.putExtra("TransRole",RoleUser)
        i.putExtra("TransFullName",fullNameUser)
        i.putExtra("TransPhoneNum",PhoneNumUser)
        i.putExtra("TransBirthDay",BirthDayUser)
        i.putExtra("TransRegion",RegionUser)
        i.putExtra("TransImage",ImageUser)
        i.putExtra("TransFlagAddUpdateInfo",FlagUpdateInfo.toString())
        i.putExtra("TransFlagAddFood",FlagAddNewFood.toString())
        i.putExtra("TransFlagChangePass",FlagChangePass.toString())
        startActivity(i)
    }
    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            UserFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private fun onAddButtonClicked(){
        setVisibility(clicked)
        setAnim(clicked)
        setClickable(clicked)
        clicked=!clicked
    }

    private fun setVisibility(clicked:Boolean){
        if(!clicked){
            btnChangePassWord.visibility = View.VISIBLE
            btnUpdateInfo.visibility = View.VISIBLE
            // Thay đổi ở đây (Chỉ hiện thêm addFood button khi RoleUser là 1 )
            if(RoleUser!!.toInt() == 2){
                btnAddFood.visibility = View.VISIBLE
            }else{
                btnAddFood.visibility = View.INVISIBLE
            }
        }else{
            btnChangePassWord.visibility = View.INVISIBLE
            btnUpdateInfo.visibility = View.INVISIBLE
            btnAddFood.visibility = View.INVISIBLE
        }
    }

    private fun setAnim(clicked: Boolean){
        if(!clicked){
            btnChangePassWord.startAnimation(fromBottom)
            btnUpdateInfo.startAnimation(fromLeft)
            btnAddFood.startAnimation(fromTop)
            floatingButtonMenu.startAnimation(rotateOpen)
        }else{
            btnChangePassWord.startAnimation(toBottom)
            btnUpdateInfo.startAnimation(toLeft)
            btnAddFood.startAnimation(toTop)
            floatingButtonMenu.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean){
        if(!clicked){
            btnChangePassWord.isClickable = true
            btnUpdateInfo.isClickable = true
            btnAddFood.isClickable = true
        }else{
            btnChangePassWord.isClickable = false
            btnUpdateInfo.isClickable = false
            btnAddFood.isClickable = false
        }
    }
}