package org.techtown.plogging_android.Mycrew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.Mycrew.Retrofit.MyCrewResult
import org.techtown.plogging_android.Mycrew.Retrofit.MyCrewRetroInterface
import org.techtown.plogging_android.Mycrew.Retrofit.QuitResponse
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentMyCrewintroBinding
import org.techtown.plogging_android.getRetorfit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCrewIntroFragment(val myCrew:MyCrewResult , val crewIdx : Int) : Fragment() {

    lateinit var binding : FragmentMyCrewintroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCrewintroBinding.inflate(inflater, container,false)
        setListener()
        setInit()


        return binding.root
    }


    fun setListener(){
        //탈퇴하기 API
        binding.myIntroQuitBtn.setOnClickListener {
            quit()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container_fl , MyCrewFragment())
                .addToBackStack(null).commitAllowingStateLoss()
        }
    }

    fun setInit(){
        binding.recruitment.text = myCrew.name
        binding.introView.text ="환영합니다."
        //binding.activeDate.text = "활동일 : ${myCrew.targetDay.year}-${myCrew.targetDay.month}-${myCrew.targetDay.day}"
        binding.activeDate.text = myCrew.targetDay.substring(0,10)
        binding.recruitMem.text = "모집현황 : ${myCrew.currentNum}/${myCrew.howmany}"
        binding.phoneNum.text = "010-8793-2422"
    }

    fun quit(){
        val quitService = getRetorfit().create(MyCrewRetroInterface::class.java)
        quitService.deleteCrew(MyApplication.jwt!! , crewIdx).enqueue(object : Callback<QuitResponse>{
            override fun onResponse(call: Call<QuitResponse>, response: Response<QuitResponse>) {
                Toast.makeText(requireContext(),"탈퇴 완료", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<QuitResponse>, t: Throwable) {

            }
        })
    }

}