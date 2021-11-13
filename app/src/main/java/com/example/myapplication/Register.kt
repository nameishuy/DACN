package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.myapplication.api.Retro
import com.example.myapplication.api.API
import com.example.myapplication.model.User.UserPost
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.inputNameAccount
import kotlinx.android.synthetic.main.activity_register.inputPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Set animation for View.
        YoYo.with(Techniques.FadeIn)
            .duration(700)
            .playOn(registerLayout)


        initAction()
    }
    //getMyData()
    //Set event click back to Login Layout
    fun initAction(){
        btnBack.setOnClickListener{
            val i:Intent= Intent(this,Login::class.java)
            startActivity(i)
            finish()
        }
        Register()
    }

    fun Register(){
        btnRegister.setOnClickListener(){
            val nameAccount = inputNameAccount.text.toString().trim()
            val Pass = inputPassword.text.toString().trim()
            val confirmPass = inputConfirmPassword.text.toString().trim()
            val fullNameFrm=inputFullName.text.toString().trim()
            val i:Intent= Intent(this,Login::class.java)
            // Kiểm tra ràng buộc
            if (nameAccount.isEmpty()){
                inputNameAccount.error = "Account Name is Required"
                inputNameAccount.requestFocus()
                YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(inputNameAccount)
                return@setOnClickListener
            }
            if (Pass.isEmpty()){
                inputPassword.error = "Password is Required"
                inputPassword.requestFocus()
                YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(inputPassword)
                return@setOnClickListener
            }
            if (confirmPass.isEmpty()){
                inputConfirmPassword.error = "Confirm Password is Required"
                inputConfirmPassword.requestFocus()
                YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(inputConfirmPassword)
                return@setOnClickListener
            }
            if(Pass.equals(confirmPass)==false){
                inputConfirmPassword.error = "Mật khẩu nhập lại không trùng khớp"
                inputConfirmPassword.requestFocus()
                YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(inputPassword)
                YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(inputConfirmPassword)
                return@setOnClickListener
            }
            if(fullNameFrm.isEmpty()){
                inputFullName.error="Tên bị bỏ trống"
                inputFullName.requestFocus()
                YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(inputFullName)
                return@setOnClickListener
            }
            //Phần đăng ký tài khoản
            val retro = Retro().getRetroClientInstance().create(API::class.java)
            val userPost= UserPost(""+nameAccount,""+Pass,
                ""+confirmPass,""+fullNameFrm)

            val call=retro.Resgister(userPost)
            call.enqueue(object: Callback<UserPost> {
                override fun onResponse(call: Call<UserPost>?, response: Response<UserPost>?) {
                    if (response != null) {
                        if(response.isSuccessful){
                            Toast.makeText(applicationContext,"Đăng Ký Thành Công"
                                , Toast.LENGTH_LONG).show()
                            startActivity(i)
                        }else{
                            Toast.makeText(applicationContext,"Đăng Ký Thất Bại"
                                , Toast.LENGTH_LONG).show()
                            Log.e("Lỗi 1",":"+response.message())
                        }
                    }
                }
                override fun onFailure(call: Call<UserPost>?, t: Throwable?) {
                    if (t != null) {
                        Toast.makeText(applicationContext,"Đăng Ký Thất Bại"+t.message, Toast.LENGTH_LONG).show()
                        Log.e("Lỗi 2",":"+t.message)
                    }
                }

            })
        }
    }
}