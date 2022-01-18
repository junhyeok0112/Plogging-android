package org.techtown.plogging_android.Login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.icu.number.NumberFormatter.with
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.techtown.plogging_android.databinding.ActivityRegisterBinding


class RegisterActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root) // * setContentView에는 binding.root를 꼭 전달

        binding.btnRegister.setOnClickListener {
            Log.d(TAG, "회원가입 버튼 클릭")



            val id = binding.registerId.text.toString()
            val pw = binding.registerPw.text.toString()
            val intro = binding.editIntro.text.toString()



            // 유저가 항목을 다 채우지 않았을 경우


            Log.d("id" , "id")
            if (id.isEmpty()) {
                dialog("빠짐없이 기입해 주세요.")
            } else {


                // 회원가입 성공 토스트 메세지 띄우기
                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()

                // 유저가 입력한 id, pw를 쉐어드에 저장한다. 스틱코드 참고
                val sharedPreference = getSharedPreferences("file name", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putString("id", id)
                editor.putString("pw", pw)
                editor.apply()
                

                // 로그인 화면으로 이동
                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()
            }

        }
    }

    // 회원가입 실패시 다이얼로그를 띄워주는 메소드
    private fun dialog(type: String) {
        val dialog = AlertDialog.Builder(this)

        // 작성 안한 항목이 있을 경우
        if (type.equals("blank")) {
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("입력란을 모두 작성해주세요")
        }
        // 입력한 비밀번호가 다를 경우
        else if (type.equals("not same")) {
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("비밀번호가 다릅니다")
        }

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

}

