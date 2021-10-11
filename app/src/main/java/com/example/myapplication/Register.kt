package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.inputNameAccount
import kotlinx.android.synthetic.main.activity_register.inputPassword

const val BASE_URL = "https://cookingapp-api.herokuapp.com/api/"
class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //getMyData()
        //Set event click back to Login Layout
        btnBack.setOnClickListener{
            val i:Intent= Intent(this,Login::class.java)
            startActivity(i)
            finish()
        }

        // Check empty values
        btnRegister.setOnClickListener(){
            val nameAccount = inputNameAccount.text.toString().trim()
            val Pass = inputPassword.text.toString().trim()
            val confirmPass = inputConfirmPassword.text.toString().trim()

            if (nameAccount.isEmpty()){
                inputNameAccount.error = "Account Name is Required"
                inputNameAccount.requestFocus()
                return@setOnClickListener
            }
            if (Pass.isEmpty()){
                inputPassword.error = "Password is Required"
                inputPassword.requestFocus()
                return@setOnClickListener
            }
            if (confirmPass.isEmpty()){
                inputConfirmPassword.error = "Confirm Password is Required"
                inputConfirmPassword.requestFocus()
                return@setOnClickListener
            }
        }
    }
}