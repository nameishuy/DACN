package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.User.ChangePassPost
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_change_pass.*
import kotlinx.android.synthetic.main.fragment_change_pass.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassFragment : Fragment() {
    private var FlagChangePass:Boolean =false
    var fullNameUser:String? = null
    var idUser:String? = null
    var RoleUser:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_change_pass, container, false)
        view.btnChangePass.setOnClickListener {
            ChangePass()
        }
        view.btnBackToHomeFromChangeFragment.setOnClickListener {
            FlagChangePass =true
            TransDataToUserUpdate()
        }
        return view
    }

    companion object {

        fun newInstance() =
            ChangePassFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataDefault()
    }
    private fun ChangePass(){
        var Flag:Boolean =true
        val OldPass = inputOldPassword.text.toString().trim()
        val NewPass = inputNewPassword.text.toString().trim()
        val ConfirmNewPass=inputNewPasswordConfirm.text.toString().trim()
        // Kiểm tra ràng buộc
        if (OldPass.isEmpty()){
            inputOldPassword.error = "Vui lòng nhập mật khẩu củ"
            inputOldPassword.requestFocus()
            YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(inputOldPassword)
            Flag =false
        }
        if (NewPass.isEmpty()){
            inputNewPassword.error = "Vui lòng nhập mật khẩu mới"
            inputNewPassword.requestFocus()
            YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(inputNewPassword)
            Flag =false
        }
        if (ConfirmNewPass.isEmpty()){
            inputNewPasswordConfirm.error = "Vui lòng xác nhận mật khẩu mới"
            inputNewPasswordConfirm.requestFocus()
            YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(inputNewPasswordConfirm)
            Flag =false
        }
        if(NewPass.equals(ConfirmNewPass)==false){
            inputNewPasswordConfirm.error = "Mật khẩu xác nhận không trùng khớp"
            inputNewPasswordConfirm.requestFocus()
            YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(inputNewPassword)
            YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(inputNewPasswordConfirm)
            Flag =false
        }
        Log.e("IdUser",""+ idUser!!.toInt())
        if(Flag==true){
            val retro = Retro().getRetroClientInstance().create(API::class.java)
            val newPass =ChangePassPost(""+OldPass,""+NewPass,""+ConfirmNewPass)
            retro.ChangePass(idUser!!.toInt(),newPass).enqueue(object :Callback<ChangePassPost>{
                override fun onResponse(
                    call: Call<ChangePassPost>?,
                    response: Response<ChangePassPost>?
                ) {
                    if(response!=null){
                        if(response.isSuccessful){
                            Toast.makeText(activity,"Thay Đổi Thành Công"
                                , Toast.LENGTH_LONG).show()
                            val i: Intent = Intent(this@ChangePassFragment.requireContext(), Login::class.java)
                            startActivity(i)
                        }else{
                            Toast.makeText(activity,"Thay Đổi Thất Bại", Toast.LENGTH_LONG).show()
                            Log.e("Lỗi 1",":"+response.message())
                        }
                    }
                }

                override fun onFailure(call: Call<ChangePassPost>?, t: Throwable?) {
                    if (t != null) {
                        Toast.makeText(activity,"Thay Đổi Thất Bại"+t.message, Toast.LENGTH_LONG).show()
                        Log.e("Lỗi 2",":"+t.message)
                    }
                }
            })
        }else{
            Toast.makeText(activity,"Thay Đổi Thất Bại",Toast.LENGTH_LONG).show()
        }
    }
    private fun TransDataToUserUpdate(){
        val i = Intent(this@ChangePassFragment.requireContext(),MenuFood::class.java)
        var fullname:String = fullNameUser.toString()
        var id:String = idUser.toString()
        var roleId:String = RoleUser.toString()

        i.putExtra("fullname",fullname)
        i.putExtra("id",id)
        i.putExtra("roleId",roleId)
        i.putExtra("TransFlagChangePassToMenuFood",FlagChangePass.toString())
        startActivity(i)
    }

    private fun getDataDefault(){
        val bundle = arguments

        if(bundle!!.getString("idUpUser") != null){
            idUser = bundle!!.getString("idUpUser")
        }
        if(bundle!!.getString("roleUpIdUser") != null){
            RoleUser = bundle!!.getString("roleUpIdUser")
        }
        if(bundle!!.getString("fullNameUpUser").equals("null")==false){
            fullNameUser = bundle!!.getString("fullNameUpUser")
        }
    }
}