package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.nfc.NfcAdapter
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.net.toUri
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.*
import kotlinx.android.synthetic.main.fragment_add_food.*
import kotlinx.android.synthetic.main.fragment_add_food.view.*
import kotlinx.android.synthetic.main.fragment_user_update.*
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

class AddFoodFragment : Fragment() {
    private val ListFoodNameType = mutableListOf<String>()
    private val ListFoodTypeId= mutableListOf<Int>()
    private val ListMaterial = mutableListOf<String>()
    private val ListMaterialId= mutableListOf<Int>()
    private val ListMaterialQuatity= ArrayList<addFoodPost.recipe>()
    private val CLIENT_ID = "c303cfc3f024564"

    private val ListStep = ArrayList<String>()
    private val ListNumStep= ArrayList<String>()
    private val ListChooseMaterial= ArrayList<Int>()
    private val ListMaterialAndQuantity =ArrayList<String>()
    private val ListQuatity = ArrayList<String>()

    private var SelectedMaterialSpinner:String=""
    private var CountStep:Int = 1
    private var CountMaterial:Int = 1
    private var ChooseNumMaterialSpinner:Int= -1
    private var MakingFoodStep:String =""
    private var ShowMaterialAndQuantity=""
    private var Step:String=""
    private var NumPositions:Int = -1
    private var NumMaterialPositions:Int = -1
    private var Flag:Boolean = false
    private var Flag2:Boolean = false
    private var FlagAddFoodType:Boolean =false
    private var FlagAddMaterial:Boolean =false
    private var FlagToUpImageFood:Boolean =false
    private var ChooseFoodTypeId:Int = 0
    private var ChooseMaterialId:Int = 0
    private var fullNameUser:String? = null
    private var idUser:String? = null
    private var RoleUser:String? = null
    private var Quatity:String = ""
    private var NewMaterialName:String =""
    private var NewFoodTypeName:String =""

    private var LinkImage:String? =null
    private var LinkImpur:String? =null

    private var LinkNewFoodTypeImage:String? =null
    private var LinkNewFoodTypeImpur:String? =null

    // Global
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
        val view = inflater.inflate(R.layout.fragment_add_food, container, false)
        view.btnAddNewStep.setOnClickListener {
            btnStepOK()
        }
        view.btnBackToLastStep.setOnClickListener {
            StepFoodMaking()
            NumPositions=-1
            Flag =false
        }
        view.btnAddNewMaterialRecipe.setOnClickListener {
            btnAddMaterialOK()
        }
        view.btnBackToLastMaterial.setOnClickListener {
            NumMaterialPositions=-1
            Flag2=false
        }
        view.btnUpdateFoodImage.setOnClickListener {
            getImageFromGallery()
            FlagToUpImageFood =true
        }
        view.btnNewFoodTypeImage.setOnClickListener {
            getImageFromGallery()
        }
        view.btnAddNewFoodRecipes.setOnClickListener {
            Log.e("btnAddNewFood","Click OK")
            AddNewFood()
        }
        view.btnBackToHomeFromAddFragment.setOnClickListener {
            ReturnHome()
        }
        view.btnPostNewMaterial.setOnClickListener {
            AddNewMaterial()
            BackToSpinnerMaterial()
        }
        view.btnPostNewFoodType.setOnClickListener {
            AddNewFoodType()
            BackToSpinnerFoodType()
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.RLSpinnerFoodType.setVisibility(View.VISIBLE)
        view.SpinnerNameFoodType.setVisibility(View.VISIBLE)
        view.inputNewFoodType.setVisibility(View.INVISIBLE)
        view.btnPostNewFoodType.setVisibility(View.INVISIBLE)

        view.LinearFoodTypeImage.setVisibility(View.INVISIBLE)
        view.RelativeFoodTypeImage.setVisibility(View.INVISIBLE)

        view.RLSpinnerMaterial.setVisibility(View.VISIBLE)
        view.SpinnerMaterial.setVisibility(View.VISIBLE)
        view.inputNewMaterial.setVisibility(View.INVISIBLE)
        view.btnPostNewMaterial.setVisibility(View.INVISIBLE)

        view.btnAddNewFoodType.setOnClickListener {
            btnAddNewFoodType()
        }
        view.btnBackToSpinnerFoodType.setOnClickListener {
            BackToSpinnerFoodType()
        }
        view.btnAddNewMaterial.setOnClickListener {
            btnAddNewMaterial()
        }
        view.btnBackToSpinnerMaterial.setOnClickListener {
            BackToSpinnerMaterial()
        }
        getNameFoodType()
        getMaterial()
        StepFoodMaking()
        getDataDefault()
    }

    companion object {
        val Image_Request_Code = 100
        fun newInstance() =
            AddFoodFragment().apply {
                arguments = Bundle().apply {}
            }
    }
    // set Visible cho Spinner
    private fun btnAddNewFoodType(){
        RLSpinnerFoodType.setVisibility(View.INVISIBLE)
        SpinnerNameFoodType.setVisibility(View.GONE)
        inputNewFoodType.setVisibility(View.VISIBLE)
        LinearFoodTypeImage.setVisibility(View.VISIBLE)
        RelativeFoodTypeImage.setVisibility(View.VISIBLE)
        btnPostNewFoodType.setVisibility(View.VISIBLE)
        FlagAddFoodType =true
    }
    private fun BackToSpinnerFoodType(){
        RLSpinnerFoodType.setVisibility(View.VISIBLE)
        SpinnerNameFoodType.setVisibility(View.VISIBLE)
        inputNewFoodType.setVisibility(View.INVISIBLE)
        LinearFoodTypeImage.setVisibility(View.INVISIBLE)
        RelativeFoodTypeImage.setVisibility(View.INVISIBLE)
        btnPostNewFoodType.setVisibility(View.INVISIBLE)
        FlagAddFoodType =false
    }
    private fun btnAddNewMaterial(){
        RLSpinnerMaterial.setVisibility(View.INVISIBLE)
        SpinnerMaterial.setVisibility(View.GONE)
        inputNewMaterial.setVisibility(View.VISIBLE)
        btnPostNewMaterial.setVisibility(View.VISIBLE)
        FlagAddMaterial=true
    }
    private fun BackToSpinnerMaterial(){
        RLSpinnerMaterial.setVisibility(View.VISIBLE)
        SpinnerMaterial.setVisibility(View.VISIBLE)
        inputNewMaterial.setVisibility(View.INVISIBLE)
        btnPostNewMaterial.setVisibility(View.INVISIBLE)
        FlagAddMaterial=false
    }
    // Lấy Tên các loại món ăn
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
                        val adapter = ArrayAdapter(this@AddFoodFragment.requireContext()
                            ,android.R.layout.simple_spinner_item,ListFoodNameType)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        SpinnerNameFoodType.adapter = adapter
                        if(NewFoodTypeName.equals("")==false){
                            var PositionNewFoodType = adapter.getPosition(NewFoodTypeName)
                            SpinnerNameFoodType.setSelection(PositionNewFoodType)
                        }
                        SpinnerNameFoodType.onItemSelectedListener =object : AdapterView.OnItemSelectedListener{
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
    // Thêm các bước thực hiện món ăn
    private fun StepFoodMaking(){
        Step="Bước "+CountStep+": "
        inputRecipesStep.setText(Step)
    }
    private fun btnStepOK(){
        MakingFoodStep =""
        Log.e("Material Id",""+ChooseMaterialId)
        Log.e("Quatity",""+Quatity)
        Log.e("FoodType",""+ChooseFoodTypeId)
        if(NumPositions == -1){
            val RescipeStep =inputRecipesStep.text.toString().trim()
            ListNumStep.add(Step)
            if(RescipeStep.isEmpty()){
                inputRecipesStep.error = "Bước nấu ăn bị trống"
                inputRecipesStep.requestFocus()
            }else{
                ListStep.add(RescipeStep)
            }
            for(item in ListStep){
                MakingFoodStep =MakingFoodStep+"\r\n"+item
            }
            inputEreaStep.setText(MakingFoodStep)
            Log.e("Step Data",""+MakingFoodStep)
            inputTotalStep.setText(CountStep.toString())
            CountStep++
            StepFoodMaking()
            SpinnerStep()
        }else{
            val RescipeStep =inputRecipesStep.text.toString().trim()
            if(RescipeStep.isEmpty()){
                inputRecipesStep.error = "Account Name is Required"
                inputRecipesStep.requestFocus()
            }else{
                ListStep.set(NumPositions,RescipeStep)
            }
            for(item in ListStep){
                MakingFoodStep =MakingFoodStep+"\r\n"+item
            }
            inputEreaStep.setText(MakingFoodStep)
            Log.e("Step Data",""+MakingFoodStep)
        }
    }

    private fun SpinnerStep(){
        val adapter = ArrayAdapter(this@AddFoodFragment.requireContext()
            ,android.R.layout.simple_spinner_item,ListNumStep)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerChooseStep.adapter = adapter

        SpinnerChooseStep.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if(position==0 && Flag ==false){
                    StepFoodMaking()
                }else{
                    NumPositions =position
                    inputRecipesStep.setText(ListStep.elementAt(NumPositions))
                    Flag =true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
    // Lấy các toàn bộ nguyên liệu
    private fun getMaterial(){
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        retro.getDataMaterial().enqueue(object : Callback<MaterialResponse> {
            override fun onResponse(
                call: Call<MaterialResponse>?,
                response: Response<MaterialResponse>?
            ) {
                if(response!=null){
                    if(response.isSuccessful){
                        for(item in response.body().listMaterial!!){
                            ListMaterial.add(item.name!!)
                            ListMaterialId.add(item.id!!)
                            Log.e("Data",""+item.name)
                        }
                        val adapter = ArrayAdapter(this@AddFoodFragment.requireContext()
                            ,android.R.layout.simple_spinner_item,ListMaterial)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        SpinnerMaterial.adapter = adapter
                        if(NewMaterialName.equals("")==false){
                            var PositionNewMaterial = adapter.getPosition(NewMaterialName)
                            SpinnerMaterial.setSelection(PositionNewMaterial)
                        }
                        SpinnerMaterial.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
                            ) {
                                val selectedItem = parent!!.getItemAtPosition(position)
                                SelectedMaterialSpinner = selectedItem.toString()
                                ChooseMaterialId =ListMaterialId.elementAt(position)
                                Log.e("Data",""+selectedItem)
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
    // Thêm nguyên liệu
    private fun btnAddMaterialOK(){
        ShowMaterialAndQuantity=""
        if(NumMaterialPositions ==-1){
            Quatity = inputQuantity.text.toString().trim()
            if(Quatity.isEmpty()){
                inputQuantity.error = "Chưa Thêm Định Lượng"
                inputQuantity.requestFocus()
            }else{
                ListMaterial.add(SelectedMaterialSpinner)
                ListQuatity.add(Quatity)
                val newFood = addFoodPost.recipe()
                newFood.pstMaterialId =ChooseMaterialId
                newFood.pstQuantity =Quatity
                ListMaterialQuatity!!.add(newFood)

                val str =CountMaterial.toString()+"."+SelectedMaterialSpinner+": "+Quatity
                ListMaterialAndQuantity.add(str)

                if(ChooseNumMaterialSpinner>-1){
                    ListChooseMaterial.add(ChooseNumMaterialSpinner)
                }
                for (item in ListMaterialAndQuantity){
                    ShowMaterialAndQuantity =ShowMaterialAndQuantity+"\r\n"+item
                }
                inputEreaMaterial.setText(ShowMaterialAndQuantity)
                CountMaterial++
                SpinnerChooseMaterial()
            }
        }else{
            val Quatity = inputQuantity.text.toString().trim()
            if(Quatity.isEmpty()){
                inputQuantity.error = "Chưa Thêm Định Lượng"
                inputQuantity.requestFocus()
            }else{
                ListQuatity.set(NumPositions,Quatity)
                val newFood =addFoodPost.recipe()
                newFood.pstMaterialId =ChooseMaterialId
                newFood.pstQuantity =Quatity
                ListMaterialQuatity!!.set(NumPositions,newFood)

                val str =CountMaterial.toString()+"."+SelectedMaterialSpinner+": "+Quatity
                ListMaterialAndQuantity.set(NumMaterialPositions,str)
            }
            for (item in ListMaterialAndQuantity){
                ShowMaterialAndQuantity =ShowMaterialAndQuantity+"\r\n"+item
            }
            inputEreaMaterial.setText(ShowMaterialAndQuantity)
        }
    }
    private fun SpinnerChooseMaterial(){
        val adapter = ArrayAdapter(this@AddFoodFragment.requireContext()
            ,android.R.layout.simple_spinner_item,ListMaterialAndQuantity)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerChooseMaterial.adapter = adapter

        SpinnerChooseMaterial.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if(position==0 && Flag2 ==false){

                }else{
                    NumMaterialPositions =position
                    SpinnerMaterial.setSelection(ListChooseMaterial.elementAt(position))
                    inputQuantity.setText(ListQuatity.elementAt(NumMaterialPositions))
                    Log.e("Element",""+ListMaterial.elementAt(NumMaterialPositions))
                    Flag2 =true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
    // Lấy ảnh từ gallery
    fun getImageFromGallery(){
        val itent = Intent(Intent.ACTION_PICK)
        itent.type="image/*"
        startActivityForResult(itent, AddFoodFragment.Image_Request_Code)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == AddFoodFragment.Image_Request_Code && FlagAddFoodType ==false){
            NewFoodImage.setImageURI(data?.data)
            Log.e("Link ẢNH",":"+data?.data)
            LinkImage =data?.data.toString()
        }
        if(requestCode == AddFoodFragment.Image_Request_Code && FlagAddFoodType ==true){
            FoodTypeImage.setImageURI(data?.data)
            Log.e("Link ẢNH",":"+data?.data)
            LinkNewFoodTypeImage =data?.data.toString()
        }
    }
    // Thêm mới Food Type
    private fun AddNewFoodType(){
        if(FlagAddFoodType==true){
            var Flag:Boolean =true
            NewFoodTypeName =inputNewFoodType.text.toString().trim()
            if(NewFoodTypeName.isEmpty()){
                inputNewFoodType.error = "Chưa Thêm Tên Loại Công Thức"
                inputNewFoodType.requestFocus()
                Flag=false
            }
            if(LinkNewFoodTypeImage !=null){
                do{
                    if(FlagAddFoodType==true){
                        uploadImageToImgurNewFood(uriToBitmap(LinkNewFoodTypeImage!!.toUri()))
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
                                getNameFoodType()
                                Log.e("Thành Công","")
                            }else{
                                Log.e("Lỗi 1",":"+response.message())
                            }
                        }
                    }

                    override fun onFailure(call: Call<FoodTypePost>?, t: Throwable?) {
                        if (t != null) {
                            Log.e("Lỗi 2",":"+t.message)
                        }
                    }

                })
            }else{
                Toast.makeText(activity,"Thêm Loại Món Thất Bại", Toast.LENGTH_LONG).show()
            }
        }
    }
    //Thêm mới Nguyên Liệu
    private fun AddNewMaterial(){
        if(FlagAddMaterial == true){
            NewMaterialName =inputNewMaterial.text.toString().trim()
            if(NewMaterialName.isEmpty()){
                inputNewMaterial.error = "Chưa Thêm Tên Gia Vị"
                inputNewMaterial.requestFocus()
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
                                Toast.makeText(activity,"Thêm Mới Thành Công"
                                    , Toast.LENGTH_LONG).show()
                                getMaterial()
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

    }
    // Thêm mới công thức món ăn
    private fun AddNewFood(){
        var Flag:Boolean = true
        val NameFood= inputNameRecipe.text.toString().trim()
        glbl.listRecipes=ListMaterialQuatity
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
            inputNameRecipe.error = "Chưa Thêm Tên Món Ăn"
            inputNameRecipe.requestFocus()
            Flag =false
        }
        if(LinkImpur==null){
            Flag =false
        }
        if(MakingFoodStep==null){
            Flag =false
        }
        if(ChooseFoodTypeId==-1){
            Flag =false
        }
        if(ListMaterialId.size ==0){
            Flag =false
        }
        if(ListQuatity.size ==0){
            Flag =false
        }
        Log.e("Flag",""+Flag)
        if(Flag ==true){
            val retro = Retro().getRetroClientInstance().create(API::class.java)
            val NewFood = addFoodPost()
            Log.e("DataNameFood",""+NameFood)
            Log.e("Link Image",""+LinkImpur)
            Log.e("Link Step",""+MakingFoodStep)
            Log.e("Link Food Type Id",""+ChooseFoodTypeId)
            for(item in ListMaterialQuatity!!){
                Log.e("Data truoc Add",""+item.pstMaterialId+": "+item.pstQuantity)
            }
            Log.e("////////////////////////","/////////////////////////////////////////")
            NewFood.FoodAdd.pstName = NameFood
            Log.e("DataNameFood",""+ NewFood.FoodAdd?.pstName)

            NewFood.FoodAdd.pstImg= LinkImpur
            Log.e("Link Image",""+NewFood.FoodAdd?.pstImg)

            NewFood.FoodAdd.pstDescription= MakingFoodStep
            Log.e("Link Step",""+NewFood.FoodAdd?.pstDescription)

            NewFood.FoodAdd.pstTypeFoodId = ChooseFoodTypeId
            Log.e("Link Food Type Id",""+NewFood.FoodAdd?.pstTypeFoodId)

            NewFood.listRecipes = glbl.listRecipes

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
                        }else{
                            Toast.makeText(activity,"Thêm Thất Bại",Toast.LENGTH_LONG).show()
                            Log.e("Lỗi 1",":"+response.message())
                        }
                    }
                }
                override fun onFailure(call: Call<addFoodPost>?, t: Throwable?) {
                    if (t != null) {
                        Toast.makeText(activity,"Thêm Thất Bại",Toast.LENGTH_LONG).show()
                        Log.e("Lỗi 2",":"+t.message)
                    }
                }

            })
        }else{
            Toast.makeText(activity,"Thêm Thất Bại",Toast.LENGTH_LONG).show()
        }
    }
    private fun ReturnHome(){
        val i = Intent(this@AddFoodFragment.requireContext(),MenuFood::class.java)
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
    private fun uploadImageToImgurNewFood(image: Bitmap) {
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
}