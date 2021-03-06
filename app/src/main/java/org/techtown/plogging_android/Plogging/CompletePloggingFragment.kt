package org.techtown.plogging_android.Plogging

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getExternalFilesDirs
import androidx.core.content.FileProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.google.common.collect.BiMap
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import org.techtown.plogging_android.Home.HomeFragment
import org.techtown.plogging_android.Home.MainActivity
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.Plogging.Adapter.ImageFragment
import org.techtown.plogging_android.Plogging.Adapter.ImageSliderAdapter
import org.techtown.plogging_android.Plogging.Retrofit.PloggingData
import org.techtown.plogging_android.Plogging.Retrofit.PloggingResponse
import org.techtown.plogging_android.Plogging.Retrofit.PloggingRetrofitInterface
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentCompletePloggingBinding
import org.techtown.plogging_android.getRetorfit
import org.techtown.plogging_android.util.dateToString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class CompletePloggingFragment : Fragment() {

    private lateinit var binding: FragmentCompletePloggingBinding
    private lateinit var filePath: String                                        //capture path
    private lateinit var file: File
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private var imageList = ArrayList<Bitmap>()
    private var urlList = ArrayList<String>()
    private var docId : String = ""                                             //?????? ???????????? doc??? Id??? ??????- >????????? url ?????????
    private var namelList = ArrayList<String>()
    //???????????? ?????? Scope ??????

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletePloggingBinding.inflate(inflater, container, false)

        imageSliderAdapter = ImageSliderAdapter(this)       //????????? ???????????? ??????
        imageList.clear()                                           //????????? ????????? ?????????
        urlList.clear()                                             //url ?????????  ?????????

        setViewpager()
        setListener()

        arguments?.clear()          //??? ?????? ?????? ????????? ->????????? ?????????

        //Camera App setting
        val sdcard = Environment.getExternalStorageDirectory();
        val imageFileName = "capture.jpg";
        file = File(sdcard, imageFileName);


        return binding.root
    }
        //showLoadingDialog -> dismissLoadging
    //Viewpager setting
    fun setViewpager() {

        //first viewPager setting
        //val imageSliderAdapter = ImageSliderAdapter(this)
        imageSliderAdapter.addFragment(ImageFragment(arguments?.getParcelable("snapshot")!!))
        imageSliderAdapter.addFragment(ImageFragment(null))               //basicSetting
        imageList.add(arguments?.getParcelable("snapshot")!!)               //List setting
        Log.d("completeMap", "${arguments?.getParcelable<Bitmap>("snapshot")}")
        binding.completeMapVp.adapter = imageSliderAdapter
        binding.completeMapVp.offscreenPageLimit = 2

        binding.completeIndicators.setViewPager(binding.completeMapVp)
        binding.completeIndicators.createIndicators(2, 0)

        binding.completeMapVp.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.completeIndicators.animatePageSelected(position)
            }
        })

        binding.completeKcalNum.text = arguments?.getString("cal")
        binding.completeTimeNum.text = arguments?.getString("time")
        binding.completeDistanceNum.text = arguments?.getString("dis")

    }


    fun setListener() {
        binding.completeCameraBtn.setOnClickListener {
            capture()
        }

        //?????? ?????? ????????? ??????
        binding.completeSaveBtn.setOnClickListener {
            imageList = imageSliderAdapter.getBitmapList()          //????????? ???????????? ????????????\
            saveStore()
        }
        binding.completeNoSaveBtn.setOnClickListener {

        }
    }

    private fun capture() {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}",
            ".jpg",
            storageDir
        )
        filePath = file.absolutePath

        val photoUri: Uri = FileProvider.getUriForFile(
            context as MainActivity,
            "org.techtown.plogging_android", file
        )
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        Log.d("startActivity", "??????")
        activity?.startActivityForResult(intent, 10)

    }

    //????????? ?????? ViewPager??? ?????????
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            Log.d("startActivityForResult", "??????")
            val options = BitmapFactory.Options()
            options.inSampleSize = 2
            val bitmap = BitmapFactory.decodeFile(filePath, options)
            //val bitmap = data?.getExtras()?.get("data") as Bitmap
            Log.d("changeFragment", "${bitmap}")
            bitmap?.let {
                Log.d("changeFragment", "??????")
                imageSliderAdapter.changeFragment(bitmap)

            }
        }
    }

    //suspend??? ????????? ????????? ?????? ?????? ????????? ???????????????.
    //????????? ????????? store??? ?????????
    fun saveStore() {
        val data = mapOf(
            "id" to MyApplication.id,          //MyApplication??? ?????? ?????? ????????? ???????????? ?????????
            "date" to dateToString(Date()),          //?????? ??????
            "kcal" to binding.completeKcalNum.text.toString(),        //?????????
            "distance" to binding.completeDistanceNum.text.toString(),   //????????????
            "time" to binding.completeTimeNum.text.toString()            //?????? ??????
        )

        //????????? ???????????? ?????? ?????????
        MyApplication.db.collection("new")
            .add(data)
            .addOnSuccessListener {
                //collection ?????? ??????????????? document??? ??????????????? ?????? data ?????? documentRef ??????
                //store??? ?????? ?????? ??? ??????????????? documentID ???????????? ????????? ??????
                docId = it.id
                uploadImage(it.id)
            }.addOnFailureListener {
                Log.d("jun", "data ?????? ??????", it)
            }
    }

    //imageList??? ?????? Bitmap ????????? 2??? ???????????????????????? ?????????.
    fun uploadImage(docId: String) {     //?????? id ?????? ????????? ????????????
        //??????????????? ???????????????
        val storage = MyApplication.storage
        Log.d("docId: ", "${docId}")
        //storageRef
        val storageRef = storage.reference
        //imgaes ????????? docId.jpg??? ??????  ?????? ???????????? ??????
        var imgRef = storageRef.child("images/${docId}_map.jpg")
        val file_map = getImageUri(requireContext(), imageList[0])    //?????? Bitmap?????? uri ?????????
        val mapUploadTask = imgRef.putFile(file_map)
        namelList.add("images/${docId}_auth.jpg")
        namelList.add("images/${docId}_map.jpg")
        //Task??? ??????????????? ???????????????.
        val mapUrlTask = mapUploadTask.continueWith { task->
            if(!task.isSuccessful){
                task.exception?.let{
                    Log.d("uploadImage", "?????? ?????? ?????? " + it)
                    throw it
                }
            }
            imgRef.downloadUrl
        }.addOnCompleteListener { task->
            if(task.isSuccessful){
                urlList.add(task.result.toString())
                Log.d("getUrl", "${task.result}")
                Log.d("getUrl" , "Map url ?????? ??????")
            }else{
                Log.d("getUrl" , "Map Url ?????? ??????")
            }
        }

        //?????? ?????? ??????
        val file_auth = getImageUri(requireContext(), imageList[1])
        imgRef = storageRef.child("images/${docId}_auth.jpg")
        imgRef.putFile(file_auth).continueWith { task->
            if(!task.isSuccessful){
                task.exception?.let{
                    Log.d("uploadImage", "?????? ?????? ?????? " + it)
                    throw it
                }
            }
            imgRef.downloadUrl
        }.addOnCompleteListener { task->
            if(task.isSuccessful){
                urlList.add(task.result.toString())
                Log.d("getUrl","Auth url ?????? ??????")
            } else{
                Log.d("getUrl","Auth url ?????? ??????")
            }
        }

        val mHandler = Handler()
        mHandler.postDelayed(Runnable {
            var plog = PloggingData(binding.completeKcalNum.text.toString(),
                binding.completeDistanceNum.text.toString(),
                binding.completeTimeNum.text.toString(),
                namelList)
            savePlog(plog)
        },1000)
    }

    //uploadTask ????????? ???????????? ?????? ?????? ?????? ????????????
    //Bitmap??? Uri??? ????????? ?????? ,
    fun getImageUri(inContext: Context?, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        if (inImage != null) {
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        }

        val path = MediaStore.Images.Media.insertImage(
            inContext?.getContentResolver(),
            inImage,
            "Title" + " - " + Calendar.getInstance().getTime(),
            null
        )
        return Uri.parse(path)
    }

    fun savePlog(plog : PloggingData){
        val saveService = getRetorfit().create(PloggingRetrofitInterface::class.java)
        saveService.savePlog(MyApplication.jwt!! , plog).enqueue(object : Callback<PloggingResponse>{
            override fun onResponse(
                call: Call<PloggingResponse>,
                response: Response<PloggingResponse>
            ) {
                val resp = response.body()!!
                when(resp.code){
                  1000 -> {Toast.makeText(requireContext(),"?????? ??????" ,Toast.LENGTH_SHORT).show()}
                  else -> {}
                }

            }

            override fun onFailure(call: Call<PloggingResponse>, t: Throwable) {

            }
        })
    }


}