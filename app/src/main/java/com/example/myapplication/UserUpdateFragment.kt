package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.User.UpdateInfoUserPost
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user_update.*
import kotlinx.android.synthetic.main.fragment_user_update.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection


class UserUpdateFragment : Fragment() {
    private val CLIENT_ID = "c303cfc3f024564"
    var formatDate= SimpleDateFormat("dd/MM/yyyy")

    var fullNameUser:String? = null
    var idUser:String? = null
    var RoleUser:String? = null
    var PhoneNumUser:String? = null
    var BirthDayUser:String? = null
    var RegionUser:String? = null
    var ImageUser:String? = null

    var LinkImage:String? =null
    var LinkImpur:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_user_update, container, false)
        view.btnCalendarChoose.setOnClickListener {
            DateTimePicker()
        }
        view.btnUpdateImage.setOnClickListener {
            getImageFromGallery()
        }
        view.btnUpdateImageToImgur.setOnClickListener {
            uploadImageToImgur(uriToBitmap(LinkImage!!.toUri()))
        }
        view.btnUpdateNewInfo.setOnClickListener {
            UpdateInfoUser()
        }

        view.btnBackToHome.setOnClickListener {
            ReturnHome()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataUpdate()
    }


    fun DateTimePicker(){
        val getDate= Calendar.getInstance()
        val datePicker= DatePickerDialog(this@UserUpdateFragment.requireContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { dateShowPicker, y, m, d ->

                val SelectDate= Calendar.getInstance()
                SelectDate.set(Calendar.YEAR,y)
                SelectDate.set(Calendar.MONTH,m)
                SelectDate.set(Calendar.DAY_OF_MONTH,d)
                val Date:String =formatDate.format(SelectDate.time)
                inputUpBirthDay.setText(Date)

            },getDate.get(Calendar.YEAR),
            getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }
    //lấy ảnh từ gallery
    fun getImageFromGallery(){
        val itent = Intent(Intent.ACTION_PICK)
        itent.type="image/*"
        startActivityForResult(itent, Image_Request_Code)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Image_Request_Code){
            UpInfoImage.setImageURI(data?.data)
            Log.e("Link ẢNH",":"+data?.data)
            LinkImage =data?.data.toString()
        }
    }
    //Convert the image (Bitmap) to Base64 text.
    private fun getBase64Image(image: Bitmap, complete: (String) -> Unit) {
        GlobalScope.launch {
            val outputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val b = outputStream.toByteArray()
            complete(Base64.encodeToString(b, Base64.DEFAULT))
        }
    }
    //up ảnh lên Imgur
    private fun uploadImageToImgur(image: Bitmap) {
        getBase64Image(image, complete = { base64Image ->
            GlobalScope.launch(Dispatchers.Default) {
                val url = URL("https://api.imgur.com/3/image")

                val boundary = "Boundary-${System.currentTimeMillis()}"

                val httpsURLConnection =
                    withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
                httpsURLConnection.setRequestProperty("Authorization", "Client-ID $CLIENT_ID")
                httpsURLConnection.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary=$boundary"
                )

                httpsURLConnection.requestMethod = "POST"
                httpsURLConnection.doInput = true
                httpsURLConnection.doOutput = true

                var body = ""
                body += "--$boundary\r\n"
                body += "Content-Disposition:form-data; name=\"image\""
                body += "\r\n\r\n$base64Image\r\n"
                body += "--$boundary--\r\n"


                val outputStreamWriter = OutputStreamWriter(httpsURLConnection.outputStream)
                withContext(Dispatchers.IO) {
                    outputStreamWriter.write(body)
                    outputStreamWriter.flush()
                }

                val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                val jsonObject = JSONTokener(response).nextValue() as JSONObject
                val data = jsonObject.getJSONObject("data")
                Log.d("TAG", "Link is : ${data.getString("link")}")
                LinkImpur= data.getString("link")
                Log.e("Link Impur",""+LinkImpur)
            }
        })
    }
    // chuyển từ uri sang bitmap
    private fun uriToBitmap(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
    }

    private fun getDataUpdate(){
        val bundle = arguments

        if(bundle!!.getString("idUpUser") != null){
            idUser = bundle!!.getString("idUpUser")
        }
        if(bundle!!.getString("roleUpIdUser") != null){
            RoleUser = bundle!!.getString("roleUpIdUser")
        }
        if(bundle!!.getString("fullNameUpUser").equals("null")==false){
            fullNameUser = bundle!!.getString("fullNameUpUser")
            inputUpNameInfo.setText(fullNameUser)
        }
        if(bundle!!.getString("phoneNumUpUser").equals("null")==false){
            PhoneNumUser =bundle!!.getString("phoneNumUpUser")
            inputUpPhoneNum.setText(PhoneNumUser)
        }
        if(bundle!!.getString("birthDayUpUser").equals("null")==false){
            BirthDayUser=bundle!!.getString("birthDayUpUser")
            inputUpBirthDay.setText(BirthDayUser)
        }
        if(bundle!!.getString("regionUpUser").equals("null")==false){
            RegionUser =bundle!!.getString("regionUpUser")
            inputUpRegionUser.setText(RegionUser)
        }
        if(bundle!!.getString("imageUpUser") != null){
            ImageUser=bundle!!.getString("imageUpUser")
            Picasso.get().load(ImageUser).into(UpInfoImage)
        }

    }

    private fun UpdateInfoUser(){
        val UpFullName:String = inputUpNameInfo.text.toString().trim()
        val UpPhoneNum:String = inputUpPhoneNum.text.toString().trim()
        val UpBirthDay:String = inputUpBirthDay.text.toString().trim()
        val UpRegion:String = inputUpRegionUser.text.toString().trim()
        var UpImage:String = LinkImpur.toString()
        var flag :Boolean =true
        if(UpFullName.isEmpty()){
            inputFullName.error = "Tên bị bỏ trống"
            inputFullName.requestFocus()
            flag = false
        }
        if(UpPhoneNum.isEmpty()){
            inputUpPhoneNum.error = "Số điện thoại bị bỏ trống"
            inputUpPhoneNum.requestFocus()
            flag = false
        }
        if(UpBirthDay.isEmpty()){
            inputUpBirthDay.error = "Ngày sinh bị bỏ trống"
            inputFullName.requestFocus()
            flag = false
        }
        if(UpRegion.isEmpty()){
            inputUpRegionUser.error = "Địa chỉ bị bỏ trống"
            inputUpRegionUser.requestFocus()
            flag = false
        }
        if(LinkImpur==null){
            UpImage = ImageUser.toString()
        }
        Log.e("Update Name",UpFullName)
        Log.e("Update Image",UpImage)
        Log.e("Update Phone",UpPhoneNum)
        Log.e("Update BirthDay",UpBirthDay)
        Log.e("Update Region",UpRegion)
        if(flag == true){
            val retro = Retro().getRetroClientInstance().create(API::class.java)
            val userUp= UpdateInfoUserPost(""+UpFullName,""+UpImage
                ,""+UpPhoneNum,""+UpBirthDay,""+UpRegion)
            retro.UpdateUser(idUser!!.toInt(),userUp).enqueue(object: Callback<UpdateInfoUserPost>{
                override fun onResponse(
                    call: Call<UpdateInfoUserPost>?,
                    response: Response<UpdateInfoUserPost>?
                ) {
                    if (response != null) {
                        if(response.isSuccessful){
                            Toast.makeText(activity,"Cập Nhật Thành Công"
                                , Toast.LENGTH_LONG).show()
                            fullNameUser = UpFullName
                            ReturnHome()
                        }else{
                            Toast.makeText(activity,"Cập Nhật Thất Bại"
                                , Toast.LENGTH_LONG).show()
                            Log.e("Lỗi 1",":"+response.message())
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateInfoUserPost>?, t: Throwable?) {
                    if (t != null) {
                        Toast.makeText(activity,"Cập Nhật Thất Bại"+t.message, Toast.LENGTH_LONG).show()
                        Log.e("Lỗi 2",":"+t.message)
                    }
                }

            })
        }else{
            Toast.makeText(activity,"Cập Nhật Thất Bại"
                , Toast.LENGTH_LONG).show()
        }

    }
    private fun ReturnHome(){
        val i = Intent(this@UserUpdateFragment.requireContext(),MenuFood::class.java)

        var fullname:String = fullNameUser.toString()
        var id:String = idUser.toString()
        var roleId:String = RoleUser.toString()

        i.putExtra("fullname",fullname)
        i.putExtra("id",id)
        i.putExtra("roleId",roleId)
        startActivity(i)
    }
    companion object {
        val Image_Request_Code = 100
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            UserUpdateFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}