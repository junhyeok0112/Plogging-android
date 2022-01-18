package org.techtown.plogging_android.Plogging

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import org.techtown.plogging_android.MainActivity
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentCompletePloggingBinding
import org.techtown.plogging_android.util.dateToString
import java.io.File
import java.util.*

class CompletePloggingFragment : Fragment() {

    lateinit var binding : FragmentCompletePloggingBinding
    lateinit var registerLauncher: ActivityResultLauncher<Intent>       //startActivityForResult 대신
    lateinit var filePath:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletePloggingBinding.inflate(inflater , container, false)
        setLauncher()
        setListener()
        return binding.root
    }

    fun setListener(){
        binding.completeAuthIv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)             //갤러리에서 사진 가져오기 실행
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            registerLauncher.launch(intent)                     //이미지 불러와서 이미지 뷰에 로딩시켜줌
            binding.completeAksTv.visibility = View.GONE
        }

        binding.completeSaveBtn.setOnClickListener {
            saveStore()
        }
    }


    //다른 액티비티에서 받아온 결과 처리
    fun setLauncher(){          //Glide 다음에 공부해보기
        //이미지 뷰에 가져온 이미지 로딩
        registerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->

            Glide
                .with(requireContext())
                .load(result.data?.data)
                .apply(RequestOptions().transforms(CenterCrop()))
                .into(binding.completeAuthIv)

            //이미지 uri 가져오는 부분
            val cursor = requireActivity().contentResolver.query(result.data?.data as Uri,        //실질적으로 쿼리하는 코드 ,API 인자에 우리가 찾고자 하는 데이터 정보를 넣음 , 리턴 타입은 cursor 이것을 이용해서 데이터 확인가능
                arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null);
            cursor?.moveToFirst().let { //커서로 순회 해서 uri 가져옴
                filePath=cursor?.getString(0) as String
            }
        }
    }

    fun saveStore(){        //입력된 정보를 store에 올리기
        val data = mapOf(
            "id" to MyApplication.id,          //MyApplication에 있는 현재 유저의 아이디를 가져옴
            "content" to binding.completeInfoTv.text.toString(),        //플로깅 결과 정보
            "date" to dateToString(Date())          //현재 날짜
        )

        MyApplication.db.collection("new")
            .add(data)
            .addOnSuccessListener {
                //collection 새로 생성되면서 document도 생성하면서 안에 data 넣음 documentRef 리턴
                //store에 먼저 저장 후 스토리지에 documentID 이용해서 이미지 저장
                uploadImage(it.id)
            }.addOnFailureListener {
                Log.d("jun", "data 저장 실패" ,it)
            }
    }

    fun uploadImage(docId: String){     //문서 id 전달 받아서 올려야함
        //스토리지에 업로드하기
        val storage = MyApplication.storage
        Log.d("docId: " , "${docId}")
        //storageRef
        val storageRef = storage.reference
        //imgaes 폴더에 docId.jpg로 저장
        val imgRef = storageRef.child("images/${docId}.jpg")
        val file = Uri.fromFile(File(filePath))         //갤러리에서 가져온 파일경로를 이용해서 파일의 URi 전달
        Log.d("filePath: " , "${filePath}")
        imgRef.putFile(file)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "데이터가 저장 되었습니다." , Toast.LENGTH_SHORT).show()
                //프래그먼트 종료되는 함수 필요 .
            }
            .addOnFailureListener{
                Log.d("jun" , "저장 실패 "+ it)
            }

    }
}