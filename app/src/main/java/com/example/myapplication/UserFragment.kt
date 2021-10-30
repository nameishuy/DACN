package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_user, container, false)
        view.btnUpdateInfo.setOnClickListener {
            TransDataToUserUpdate()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataToHome()
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

                        if(info.data!!.fullName != null){
                            fullNameUser = info.data!!.fullName
                            UserName.text = fullNameUser
                            Log.e("data Name ",""+fullNameUser)
                        }else{
                            UserName.text = "Bạn chưa cập nhật tên"
                            Log.e("data Name ",""+fullNameUser)
                        }

                        if(info.data!!.phoneNumUser != null){
                            PhoneNumUser = info.data!!.phoneNumUser
                            UserPhone.text =PhoneNumUser
                            Log.e("data Phone",""+PhoneNumUser)
                        }else{
                            UserPhone.text = "Bạn chưa cập nhật số điện thoại"
                            Log.e("data Phone",""+PhoneNumUser)
                        }

                        if(info.data!!.birthdayUser != null){
                            BirthDayUser = info.data!!.birthdayUser
                            UserBirtDay.text=BirthDayUser
                            Log.e("data Birth Day",""+BirthDayUser)
                        }else{
                            UserBirtDay.text = "Bạn chưa cập nhật ngày sinh"
                            Log.e("data Birth Day ",""+BirthDayUser)
                        }

                        if(info.data!!.regionUser != null){
                            RegionUser = info.data!!.regionUser
                            UserRegion.text = RegionUser
                            Log.e("data Region",""+RegionUser)
                        }else{
                            UserRegion.text = "Bạn chưa cập nhật ngày sinh"
                            Log.e("data Region ",""+RegionUser)
                        }

                        if(info.data!!.imageUser != null){
                            ImageUser = info.data!!.imageUser
                            Picasso.get().load(ImageUser).into(UserInfoImage)
                            Log.e("data Image",""+ImageUser)
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
        startActivity(i)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            UserFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}