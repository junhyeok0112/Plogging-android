package org.techtown.plogging_android.Login


import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.common.api.internal.RegisterListenerMethod
import org.techtown.plogging_android.Home.MainActivity
import org.techtown.plogging_android.Login.Retrofit.LoginGet
import org.techtown.plogging_android.Login.Retrofit.LoginRetroInterface
import org.techtown.plogging_android.Login.Retrofit.LoginUser
import org.techtown.plogging_android.Login.Retrofit.User
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.databinding.ActivityLoginBinding
import org.techtown.plogging_android.getRetorfit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) //클래스 객체화
        setContentView(binding.root) // * setContentView에는 binding.root를 꼭 전달


        binding.btnLogin.setOnClickListener {

            val id = binding.loginId.text.toString()
            val pw = binding.loginPw.text.toString()

//            // 쉐어드로부터 저장된 id, pw 가져오기
//            val sharedPreference = getSharedPreferences("file name", Context.MODE_PRIVATE)
//            val savedId = sharedPreference.getString("id", "")
//            val savedPw = sharedPreference.getString("pw", "")

            // 유저가 입력한 id, pw값과 쉐어드로 불러온 id, pw값 비교
            if (id != "" && pw != "") {

                val user = LoginUser(id,pw)
                login(user)

            } else {
                // 로그인 실패 다이얼로그 보여주기
                dialog("fail")
            }

        }

        // 회원가입 버튼
        binding.btnGoRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // 로그인 성공/실패 시 다이얼로그를 띄워주는 메소드
    private fun dialog(type: String) {
        val dialog = AlertDialog.Builder(this)

        if (type == "success") {
            dialog.setTitle("로그인 성공")
            dialog.setMessage("로그인 성공!")
        } else if (type == "fail") {
            dialog.setTitle("로그인 실패")
            dialog.setMessage("아이디와 비밀번호를 입력해주세요")
        }

        val dialog_listener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "로그인 성공")
                }
            }
        }

        dialog.setPositiveButton("확인", dialog_listener)
        dialog.show()
    }

    //로그인 하는 API 호출코드
    fun login(user:LoginUser) {
        val loginRetrofit = getRetorfit().create(LoginRetroInterface::class.java)

        loginRetrofit.login(user).enqueue(object : Callback<LoginGet> {
            override fun onResponse(call: Call<LoginGet>, response: Response<LoginGet>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        MyApplication.jwt = resp.result!!.jwt
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else -> {
                        Log.d("login" , "${resp.message}")
                        Toast.makeText(this@LoginActivity, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginGet>, t: Throwable) {
                Log.d("Login", "통신 실패 ${t}")
            }
        })
    }


}



