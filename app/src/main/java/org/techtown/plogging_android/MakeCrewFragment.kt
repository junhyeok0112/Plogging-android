package org.techtown.plogging_android

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import org.techtown.plogging_android.RecruitCrew.Retrofit.RecruitRetrofitInterface
import org.techtown.plogging_android.RecruitCrew.Retrofit.makreCrew.Crew
import org.techtown.plogging_android.RecruitCrew.Retrofit.makreCrew.MakeCrewResponse
import org.techtown.plogging_android.databinding.FragmentMakeCrewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MakeCrewFragment : Fragment() {

    lateinit var binding : FragmentMakeCrewBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakeCrewBinding.inflate(inflater , container , false)
        setListener()
        return binding.root
    }

    fun setListener(){
        //캘린더 이미지 선택시 달력 나오게하는 리스너
        binding.makeCrewCalendarSelectBtn.setOnClickListener {
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date: Int = today.get(Calendar.DATE)
            val dlg = DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    binding.makeCrewDateEt.setText("${year}년 ${month+1}월 ${dayOfMonth}일")
                }
            }, year, month, date)
            dlg.show()
        }

        binding.makeCrewMakeBtn.setOnClickListener {
            var name =binding.makeCrewInputNameEt.text.toString()
            var contacts =  "010-8793-2422"
            var descr = binding.makeCrewInputIntroduceEt.text.toString()
            var region = binding.makeCrewRegionSelectSp.selectedItem.toString()
            var howmany = binding.makeCrewPersonnelSelectSp.selectedItem.toString().toInt()
            var targetDay = binding.makeCrewDateEt.text.toString()
            val crew = Crew(contacts,"",descr,howmany,name, region,targetDay)
            makeCrew(crew)
        }

    }

    fun makeCrew(crew: Crew){
        val makeService = getRetorfit().create(RecruitRetrofitInterface::class.java)
        makeService.makeCrew(MyApplication.jwt!!,crew).enqueue(object : Callback<MakeCrewResponse>{
            override fun onResponse(
                call: Call<MakeCrewResponse>,
                response: Response<MakeCrewResponse>
            ) {
                Toast.makeText(requireContext(),"개설 성공",Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<MakeCrewResponse>, t: Throwable) {
                Toast.makeText(requireContext(),"개설실패",Toast.LENGTH_SHORT).show()
            }
        })

    }


}