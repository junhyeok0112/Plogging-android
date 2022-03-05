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
    private var docId : String = ""                                             //현재 저장하는 doc의 Id를 저장- >이걸로 url 가져옴
    private var namelList = ArrayList<String>()
    //코루틴을 위한 Scope 생성

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletePloggingBinding.inflate(inflater, container, false)

        imageSliderAdapter = ImageSliderAdapter(this)       //어뎁터 전역으로 선언
        imageList.clear()                                           //이미지 리스트 초기화
        urlList.clear()                                             //url 리스트  초기화

        setViewpager()
        setListener()

        arguments?.clear()          //값 쓰고 나서 초기화 ->안하면 오류남

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

        //저장 버튼 눌렀을 경우
        binding.completeSaveBtn.setOnClickListener {
            imageList = imageSliderAdapter.getBitmapList()          //이미지 리스트들 전달받음\
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
        Log.d("startActivity", "실행")
        activity?.startActivityForResult(intent, 10)

    }

    //찍어온 사진 ViewPager에 올리기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            Log.d("startActivityForResult", "실행")
            val options = BitmapFactory.Options()
            options.inSampleSize = 2
            val bitmap = BitmapFactory.decodeFile(filePath, options)
            //val bitmap = data?.getExtras()?.get("data") as Bitmap
            Log.d("changeFragment", "${bitmap}")
            bitmap?.let {
                Log.d("changeFragment", "실행")
                imageSliderAdapter.changeFragment(bitmap)

            }
        }
    }

    //suspend는 비동기 실행을 위한 중단 지점을 의미합니다.
    //입력된 정보를 store에 올리기
    fun saveStore() {
        val data = mapOf(
            "id" to MyApplication.id,          //MyApplication에 있는 현재 유저의 아이디를 가져옴
            "date" to dateToString(Date()),          //현재 날짜
            "kcal" to binding.completeKcalNum.text.toString(),        //칼로리
            "distance" to binding.completeDistanceNum.text.toString(),   //이동거리
            "time" to binding.completeTimeNum.text.toString()            //운동 시간
        )

        //파이어 스토어에 정보 올리기
        MyApplication.db.collection("new")
            .add(data)
            .addOnSuccessListener {
                //collection 새로 생성되면서 document도 생성하면서 안에 data 넣음 documentRef 리턴
                //store에 먼저 저장 후 스토리지에 documentID 이용해서 이미지 저장
                docId = it.id
                uploadImage(it.id)
            }.addOnFailureListener {
                Log.d("jun", "data 저장 실패", it)
            }
    }

    //imageList에 있는 Bitmap 이미지 2장 파이어스토리지에 올리기.
    fun uploadImage(docId: String) {     //문서 id 전달 받아서 올려야함
        //스토리지에 업로드하기
        val storage = MyApplication.storage
        Log.d("docId: ", "${docId}")
        //storageRef
        val storageRef = storage.reference
        //imgaes 폴더에 docId.jpg로 저장  지도 캡처시진 저장
        var imgRef = storageRef.child("images/${docId}_map.jpg")
        val file_map = getImageUri(requireContext(), imageList[0])    //지도 Bitmap으로 uri 만들기
        val mapUploadTask = imgRef.putFile(file_map)
        namelList.add("images/${docId}_auth.jpg")
        namelList.add("images/${docId}_map.jpg")
        //Task가 성공적으로 처리되는지.
        val mapUrlTask = mapUploadTask.continueWith { task->
            if(!task.isSuccessful){
                task.exception?.let{
                    Log.d("uploadImage", "인증 저장 실패 " + it)
                    throw it
                }
            }
            imgRef.downloadUrl
        }.addOnCompleteListener { task->
            if(task.isSuccessful){
                urlList.add(task.result.toString())
                Log.d("getUrl", "${task.result}")
                Log.d("getUrl" , "Map url 받기 성공")
            }else{
                Log.d("getUrl" , "Map Url 받기 실패")
            }
        }

        //인증 사진 저장
        val file_auth = getImageUri(requireContext(), imageList[1])
        imgRef = storageRef.child("images/${docId}_auth.jpg")
        imgRef.putFile(file_auth).continueWith { task->
            if(!task.isSuccessful){
                task.exception?.let{
                    Log.d("uploadImage", "인증 저장 실패 " + it)
                    throw it
                }
            }
            imgRef.downloadUrl
        }.addOnCompleteListener { task->
            if(task.isSuccessful){
                urlList.add(task.result.toString())
                Log.d("getUrl","Auth url 받기 성공")
            } else{
                Log.d("getUrl","Auth url 받기 실패")
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

    //uploadTask 리스너 이용하면 굳이 밑에 함수 필요없음
    //Bitmap을 Uri로 만드는 코드 ,
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
                  1000 -> {Toast.makeText(requireContext(),"저장 성공" ,Toast.LENGTH_SHORT).show()}
                  else -> {}
                }

            }

            override fun onFailure(call: Call<PloggingResponse>, t: Throwable) {

            }
        })
    }


}