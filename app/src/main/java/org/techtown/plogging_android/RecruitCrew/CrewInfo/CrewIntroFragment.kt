package org.techtown.plogging_android.RecruitCrew.CrewInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.agatha_plog.RecruitFragment
import org.techtown.plogging_android.Home.HomeFragment
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.Mycrew.MyCrewFragment
import org.techtown.plogging_android.R
import org.techtown.plogging_android.RecruitCrew.Retrofit.CrewInfoResult
import org.techtown.plogging_android.RecruitCrew.Retrofit.RecruitRetrofitInterface
import org.techtown.plogging_android.RecruitCrew.Retrofit.SignUpResponse
import org.techtown.plogging_android.databinding.FragmentCrewintroBinding
import org.techtown.plogging_android.getRetorfit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrewIntroFragment(val crewinfo : CrewInfoResult , val crewIdx:Int) : Fragment() {

    lateinit var binding : FragmentCrewintroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrewintroBinding.inflate(inflater , container , false)
        setListener()
        setInit()
        return binding.root
    }

    fun setListener(){
        binding.applyBtn.setOnClickListener {

            signUpCrew()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container_fl , HomeFragment())
                .addToBackStack(null).commitAllowingStateLoss()
        }
    }

    fun setInit(){
        binding.recruitment.text = crewinfo.name
        binding.introView.text = crewinfo.description
        binding.activeDate.text = "활동일 : ${crewinfo.targetDay.substring(0,10)}"
        binding.recruitMem.text = "모집현황 : 2/4"
        binding.phoneNum.text = "010-8793-2422"
    }

    fun signUpCrew(){
        val signUpService = getRetorfit().create(RecruitRetrofitInterface::class.java)
        signUpService.signUpCrew(MyApplication.jwt!! , crewIdx).enqueue(object : Callback<SignUpResponse>{
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                Toast.makeText(requireContext(),"신청성공",Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {

            }
        })
    }
}