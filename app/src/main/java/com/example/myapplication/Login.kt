package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.api.Retro
import com.example.myapplication.api.UserApi
import com.example.myapplication.model.UserRequest
import com.example.myapplication.model.UserResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initAction()
    }

    fun initAction(){
        // Change Register Layout
        register.setOnClickListener {
            val i: Intent = Intent(this, Register::class.java)
            startActivity(i)
            finish()
        }
        login()
    }

    fun login(){
        btnLogin.setOnClickListener(){
            // values on textfield
            val nameAccount = inputNameAccount.text.toString().trim()
            val Pass = inputPassword.text.toString().trim()

            // Check empty values
            if(nameAccount.isEmpty()){
                inputNameAccount.error = "Account Name is Required"
                inputNameAccount.requestFocus()
                return@setOnClickListener
            }
            if(Pass.isEmpty()){
                inputPassword.error = "Password is Required"
                inputPassword.requestFocus()
                return@setOnClickListener
            }

            // If not empty all -> try to request api
            val request = UserRequest()
            request.username = nameAccount
            request.pass = Pass
            val retro = Retro().getRetroClientInstance().create(UserApi::class.java)
            retro.login(request).enqueue(object : retrofit2.Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    val user = response.body()
                    Log.e("token", user!!.data?.token!!)
                    Log.e("fullname", user!!.data?.account!!.id.toString())
                    Log.e("fullname", user!!.data?.account!!.fullname.toString())
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("Error", t.message!!)
                }
            })
        }
    }
}