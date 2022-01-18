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
import org.techtown.plogging_android.databinding.FragmentMakeCrewBinding
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

    }


}