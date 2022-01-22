package org.techtown.plogging_android.Login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.number.NumberFormatter.with
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.techtown.plogging_android.Login.Retrofit.LoginRetroInterface
import org.techtown.plogging_android.Login.Retrofit.SignUpGet
import org.techtown.plogging_android.Login.Retrofit.User
import org.techtown.plogging_android.databinding.ActivityRegisterBinding
import org.techtown.plogging_android.getRetorfit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setListener()
        setContentView(binding.root) // * setContentView에는 binding.root를 꼭 전달
    }

    fun setListener(){
        binding.btnRegister.setOnClickListener {
            Log.d(TAG, "회원가입 버튼 클릭")

            val id = binding.registerId.text.toString()
            val pw = binding.registerPw.text.toString()
            val intro = binding.editIntro.text.toString()

            // 유저가 항목을 다 채우지 않았을 경우
            Log.d("id" , "id")
            if (id.isEmpty() || pw.isEmpty() || intro.isEmpty()) {
                dialog("빠짐없이 기입해 주세요.")
            } else {
                val user = User(intro,id,pw)
                postUser(user)
            }

        }

        binding.signupProfileIv.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent,30)

        }
    }
    // 회원가입 실패시 다이얼로그를 띄워주는 메소드
    private fun dialog(type: String) {
        val dialog = AlertDialog.Builder(this)

        dialog.setTitle("회원가입 실패")
        dialog.setMessage("입력란을 모두 작성해주세요")

        val dialog_listener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "다이얼로그")
                }
            }
        }

        dialog.setPositiveButton("확인", dialog_listener)
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 30 && resultCode == Activity.RESULT_OK){
            try{
                var inputStream = contentResolver.openInputStream(data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream , null ,null)
                inputStream!!.close()
                inputStream = null
                bitmap?.let{
                    binding.signupProfileIv.setImageBitmap(bitmap)
                } ?: let{
                    Log.d("Profile", "불러오기 실패")
                }
            } catch(e: Exception){
               e.printStackTrace()
             }

        }
    }

    fun postUser(user: User){     //회원 가입 코드
        val userRetorfit = getRetorfit().create(LoginRetroInterface::class.java)

        userRetorfit.postUser(user).enqueue(object : Callback<SignUpGet> {
            override fun onResponse(call: Call<SignUpGet>, response: Response<SignUpGet>) {
                val resp = response.body()!!
                when(resp.code){
                    1000 -> {
                        // 회원가입 성공 토스트 메세지 띄우기
                        Toast.makeText(this@RegisterActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        // 로그인 화면으로 이동
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    2018 -> {
                        Toast.makeText(this@RegisterActivity , "중복된 닉네임 입니다" , Toast.LENGTH_SHORT).show()
                    }
                    else ->{
                        Log.d("signup" , "${resp.message}")
                    }
                }
            }

            override fun onFailure(call: Call<SignUpGet>, t: Throwable) {
                Log.d("postUser" , "통신 실패 : ${t}")
            }
        })
    }



}

