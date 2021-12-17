package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.RecyclerViewAdapter.RecyclerViewAdapterMaterial
import com.example.myapplication.RecyclerViewAdapter.RecyclerViewAdapterStep
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.FoodTypePost
import com.example.myapplication.model.ListFood.FoodTypeResponse
import com.example.myapplication.model.ListFood.addFoodPost
import kotlinx.android.synthetic.main.card_listmaterial.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_new_add_food.*
import kotlinx.android.synthetic.main.fragment_new_add_food.view.*
import kotlinx.android.synthetic.main.layout_popup_addnew_typefood.*
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
import javax.net.ssl.HttpsURLConnection

class NewAddFoodFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapterMaterial: RecyclerView.Adapter<RecyclerViewAdapterMaterial.ViewHolder>?=null
    private var adapterStep: RecyclerView.Adapter<RecyclerViewAdapterStep.ViewHolder>?=null
    private val CLIENT_ID = "c303cfc3f024564"

    private var ListFoodNameType = mutableListOf<String>()
    private var ListFoodTypeId= mutableListOf<Int>()
    private var fullNameUser:String? = null
    private var idUser:String? = null
    private var RoleUser:String? = null

    private var FlagAddFoodType:Boolean =false
    private var FlagToUpImageFood:Boolean =false
    private var InputTextNewFoodType:String =""
    private var ChooseFoodTypeId:Int = 0

    private var ChooseImageFoodType:ImageView? =null
    private var LinkImage:String? =null
    private var LinkImpur:String? =null
    private var LinkNewFoodTypeImage:String? =null
    private var LinkNewFoodTypeImpur:String? =null

    val glbl = Global()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_new_add_food, container, false)
        view.btnMenuFoodType.setOnClickListener {
            MenuFoodType()
        }
        view.btnAddANewFood.setOnClickListener {
            for (item in glbl!!.ListNameChooseMaterial){
                Log.e("Name Choose Material",""+item)
            }
            for (item in glbl!!.ListQuatity){
                Log.e("Quatiy",""+item)
            }
            for (item in glbl.ListStep){
                Log.e("Step",""+item)
            }
            AddNewFood()
        }
        view.btnChooseImage.setOnClickListener {
            FlagToUpImageFood =true
            getImageFromGallery()
        }
        view.btnBackToHomeFromNewAddFragment.setOnClickListener {
            ReturnHome()
        }
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runRecyclerViewMaterial()
        runRecyclerViewStep()
        getNameFoodType()
        getDataDefault()
        ProgressMain.visibility = View.INVISIBLE
    }

    private fun runRecyclerViewMaterial(){
        layoutManager = LinearLayoutManager(this@NewAddFoodFragment.requireContext())
        recyclerViewMaterial.layoutManager =layoutManager
        adapterMaterial =RecyclerViewAdapterMaterial(glbl.listMaterialRecyclerView,glbl,this@NewAddFoodFragment.requireContext(),btnNewMaterial)
        recyclerViewMaterial.adapter =adapterMaterial
    }

    private fun runRecyclerViewStep(){
        layoutManager = LinearLayoutManager(this@NewAddFoodFragment.requireContext())
        recyclerViewStep.layoutManager =layoutManager
        adapterStep =RecyclerViewAdapterStep(glbl.listStepRecyclerView,glbl,this@NewAddFoodFragment.requireContext(),btnAddANewStep)
        recyclerViewStep.adapter =adapterStep
    }

    private fun MenuFoodType(){
        val popupMenuFoodType = PopupMenu(this@NewAddFoodFragment.requireContext(),btnMenuFoodType)
        popupMenuFoodType.menu.add(Menu.NONE,0,0,"Thêm Mới")
        popupMenuFoodType.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId
            if(id==0){
                openPopupAddNewFood(Gravity.CENTER)
            }
            false
        }
        popupMenuFoodType.show()
    }

    private fun openPopupAddNewFood(gravity:Int){
        var dialog:Dialog = Dialog(this@NewAddFoodFragment.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_popup_addnew_typefood)

        var window:Window = dialog.window!!
        if(window !=null){
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            var windowAttributes:WindowManager.LayoutParams =window.attributes
            windowAttributes.gravity =gravity
            window.attributes = windowAttributes
        }
        if(Gravity.CENTER ==gravity){
            dialog.setCancelable(true)
        }else{
            dialog.setCancelable(false)
        }

        var btnImageFoodType:TextView = dialog.findViewById(R.id.btnAddImageFoodType)
        var ImageFoodType:ImageView =dialog.findViewById(R.id.NewFoodTypeImage)
        var NameFoodType:EditText =dialog.findViewById(R.id.inputANewFoodType)
        var btnAdd:Button = dialog.findViewById(R.id.btnAddANewFoodType)
        var btnExit:Button =dialog.findViewById(R.id.btnExitAddNewFoodType)
        var progressBarFoodType:ProgressBar =dialog.findViewById(R.id.ProgressFoodType)

        progressBarFoodType.visibility =View.INVISIBLE
        btnExit.setOnClickListener {
            dialog.dismiss()
        }

        btnAdd.setOnClickListener {
            AddNewFoodType(NameFoodType.text.toString(),progressBarFoodType)
            InputTextNewFoodType = NameFoodType.text.toString().trim()
            getNameFoodType()
            dialog.dismiss()
        }
        btnImageFoodType.setOnClickListener {
            FlagAddFoodType =true
            ChooseImageFoodType =ImageFoodType
            getImageFromGallery()
        }
        dialog.show()
    }


    companion object {
        val Image_Request_Code = 100
        @JvmStatic
        fun newInstance() =
            NewAddFoodFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private fun getNameFoodType(){
        //Call API List Food Types
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        retro.getData().enqueue(object : retrofit2.Callback<FoodTypeResponse>{
            override fun onResponse(
                call: Call<FoodTypeResponse>,
                response: Response<FoodTypeResponse>
            ) {
                if (response != null) {
                    if(response.isSuccessful){
                        for(item in response.body().listFoodTypes!!){
                            ListFoodNameType.add(item.name!!)
                            ListFoodTypeId.add(item.id!!)
                            Log.e("Data",""+item.name)
                        }

                        val adapter = ArrayAdapter(this@NewAddFoodFragment.requireContext()
                            ,android.R.layout.simple_spinner_item,ListFoodNameType)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        SpinnerNameAFoodType.adapter = adapter

                        if(InputTextNewFoodType.equals("")==false){
                            var PositionNewFoodType = adapter.getPosition(InputTextNewFoodType)
                            SpinnerNameAFoodType.setSelection(PositionNewFoodType)
                        }

                        SpinnerNameAFoodType.onItemSelectedListener =object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
                            ) {
                                val selectedItem = parent!!.getItemAtPosition(position)
                                ChooseFoodTypeId =ListFoodTypeId.elementAt(position)
                                Log.e("Data",""+selectedItem)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }

                        }
                    }else{
                        Log.e("Lỗi 1",":"+response.message())
                    }
                }
            }

            override fun onFailure(call: Call<FoodTypeResponse>, t: Throwable) {
                Log.e("Lỗi 2", t.message!!)
            }
        })
    }

    private fun ReturnHome(){
        val i = Intent(this@NewAddFoodFragment.requireContext(),MenuFood::class.java)
        var fullname:String = fullNameUser.toString()
        var id:String = idUser.toString()
        var roleId:String = RoleUser.toString()

        i.putExtra("fullname",fullname)
        i.putExtra("id",id)
        i.putExtra("roleId",roleId)
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
    // Lấy ảnh từ gallery
    fun getImageFromGallery(){
        val itent = Intent(Intent.ACTION_PICK)
        itent.type="image/*"
        startActivityForResult(itent, NewAddFoodFragment.Image_Request_Code)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == NewAddFoodFragment.Image_Request_Code && FlagAddFoodType ==false){
            NewAFoodImage.setImageURI(data?.data)
            Log.e("Link ẢNH",":"+data?.data)
            LinkImage =data?.data.toString()
        }
        if(requestCode == NewAddFoodFragment.Image_Request_Code && FlagAddFoodType ==true){
            ChooseImageFoodType?.setImageURI(data?.data)
            Log.e("Link ẢNH",":"+data?.data)
            LinkNewFoodTypeImage =data?.data.toString()
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
    //up ảnh món ăn lên Imgur
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
                LinkImpur=data.getString("link")
            }
        })
    }
    //up ảnh loại món ăn lên Imgur
    private fun uploadImageToImgurFoodType(image: Bitmap) {
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
                LinkNewFoodTypeImpur= data.getString("link")
            }
        })
    }
    // chuyển từ uri sang bitmap
    private fun uriToBitmap(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
    }

    private fun AddNewFoodType(NewFoodTypeName:String,progressBar: ProgressBar){
        if(FlagAddFoodType==true){
            var Flag:Boolean =true
            progressBar.visibility =View.VISIBLE
            if(NewFoodTypeName.isEmpty()){
                inputANewFoodType.error = "Chưa Thêm Tên Loại Công Thức"
                inputANewFoodType.requestFocus()
                Flag=false
            }
            if(LinkNewFoodTypeImage !=null){
                do{
                    if(FlagAddFoodType==true){
                        uploadImageToImgurFoodType(uriToBitmap(LinkNewFoodTypeImage!!.toUri()))
                    }
                    FlagAddFoodType=false
                }while (LinkNewFoodTypeImpur==null)
            }else{
                Toast.makeText(activity,"Chưa Chọn ảnh loại món ăn",Toast.LENGTH_LONG).show()
            }
            Log.e("Link ImageFur",""+LinkNewFoodTypeImpur)
            if(LinkNewFoodTypeImpur ==null){
                Flag=false
            }
            Log.e("Flag",""+Flag)
            if(Flag ==true){
                val retro = Retro().getRetroClientInstance().create(API::class.java)
                val NewFoodTypePost = FoodTypePost(""+NewFoodTypeName
                    ,""+LinkNewFoodTypeImpur)
                retro.addNewFoodType(NewFoodTypePost).enqueue(object : Callback<FoodTypePost> {
                    override fun onResponse(
                        call: Call<FoodTypePost>?,
                        response: Response<FoodTypePost>?
                    ) {
                        if (response != null) {
                            if(response.isSuccessful){
                                Toast.makeText(activity,"Thêm Loại Thành Công"
                                    , Toast.LENGTH_LONG).show()
                                progressBar.visibility =View.INVISIBLE
                                getNameFoodType()
                                Log.e("Thành Công","")
                            }else{
                                progressBar.visibility =View.INVISIBLE
                                Log.e("Lỗi 1",":"+response.message())
                            }
                        }
                    }

                    override fun onFailure(call: Call<FoodTypePost>?, t: Throwable?) {
                        if (t != null) {
                            progressBar.visibility =View.INVISIBLE
                            Log.e("Lỗi 2",":"+t.message)
                        }
                    }

                })
            }else{
                progressBar.visibility =View.INVISIBLE
                Toast.makeText(activity,"Thêm Loại Món Thất Bại", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun AddNewFood(){
        ProgressMain.visibility =View.VISIBLE
        var Flag:Boolean = true
        val NameFood= inputNewNameRecipe.text.toString().trim()
        val VideoId = inputIdVideo.text.toString().trim()
        if(LinkImage !=null){
            do {
                if(FlagToUpImageFood==true){
                    uploadImageToImgur(uriToBitmap(LinkImage!!.toUri()))
                }
                FlagToUpImageFood=false
            }while (LinkImpur == null)
        }else{
            Toast.makeText(activity,"Chưa Chọn ảnh món ăn",Toast.LENGTH_LONG).show()
        }
        Log.e("Link ImageFur",""+LinkImpur)

        if(NameFood.isEmpty()){
            inputNewNameRecipe.error = "Chưa Thêm Tên Món Ăn"
            inputNewNameRecipe.requestFocus()
            Flag =false
        }
        if(LinkImpur==null){
            Flag =false
        }
        if(glbl.ListStep.size <= 0){
            Flag =false
        }
        if(ChooseFoodTypeId==-1){
            Flag =false
        }
        if(glbl.ListMaterialQuatity.size <= 0){
            Flag =false
        }
        if(VideoId.isEmpty()){
            inputIdVideo.error ="Chưa Thêm Id Video trên Youtube"
            inputIdVideo.requestFocus()
            Flag =false
        }
        Log.e("Flag",""+Flag)
        if(Flag ==true){
            var Step:String =""
            for(item in glbl.ListStep){
                Step = Step+"\r\n"+item
            }
            val retro = Retro().getRetroClientInstance().create(API::class.java)
            val NewFood = addFoodPost()
            Log.e("DataNameFood",""+NameFood)
            Log.e("Link Image",""+LinkImpur)
            Log.e("Link Step",""+Step)
            Log.e("Link Food Type Id",""+ChooseFoodTypeId)
            for(item in glbl.ListMaterialQuatity){
                Log.e("Data truoc Add",""+item.pstMaterialId+": "+item.pstQuantity)
            }
            Log.e("////////////////////////","/////////////////////////////////////////")
            NewFood.FoodAdd.pstName = NameFood
            Log.e("DataNameFood",""+ NewFood.FoodAdd?.pstName)

            NewFood.FoodAdd.pstImg= LinkImpur
            Log.e("Link Image",""+NewFood.FoodAdd?.pstImg)

            NewFood.FoodAdd.pstDescription= Step
            Log.e("Link Step",""+NewFood.FoodAdd?.pstDescription)

            NewFood.FoodAdd.pstTypeFoodId = ChooseFoodTypeId
            Log.e("Link Food Type Id",""+NewFood.FoodAdd?.pstTypeFoodId)

            NewFood.FoodAdd.pstVideoId= VideoId

            NewFood.listRecipes = glbl.ListMaterialQuatity
            Log.e("Link Food Type Id",""+VideoId)

            for(item in NewFood.listRecipes!!){
                Log.e("Data truoc Add",""+item.pstMaterialId+": "+item.pstQuantity)
            }
            retro.addNewFood(NewFood).enqueue(object : Callback<addFoodPost> {
                override fun onResponse(
                    call: Call<addFoodPost>?,
                    response: Response<addFoodPost>?
                ) {
                    if (response != null) {
                        if(response.isSuccessful){
                            Toast.makeText(activity,"Thêm Thành Công"
                                , Toast.LENGTH_LONG).show()
                            ReturnHome()
                            ProgressMain.visibility =View.INVISIBLE
                        }else{
                            Toast.makeText(activity,"Thêm Thất Bại",Toast.LENGTH_LONG).show()
                            ProgressMain.visibility =View.INVISIBLE
                            Log.e("Lỗi 1",":"+response.message())
                        }
                    }
                }

                override fun onFailure(call: Call<addFoodPost>?, t: Throwable?) {
                    if (t != null) {
                        Toast.makeText(activity,"Thêm Thất Bại",Toast.LENGTH_LONG).show()
                        ProgressMain.visibility =View.INVISIBLE
                        Log.e("Lỗi 2",":"+t.message)
                    }
                }

            })
        }else{
            Toast.makeText(activity,"Thêm Thất Bại",Toast.LENGTH_LONG).show()
            ProgressMain.visibility =View.INVISIBLE
        }
    }
}