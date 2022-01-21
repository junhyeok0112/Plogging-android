package org.techtown.plogging_android.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.techtown.plogging_android.Dacorator.*
import org.techtown.plogging_android.Mycrew.MyCrewFragment
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentHomeBinding
import org.techtown.plogging_android.util.stringToDate
import sun.bob.mcalendarview.vo.DateData
import java.util.*


class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setCalendar()
        setListener()
        return binding.root
    }

    fun setCalendar(){

        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        //달력 주말 색 셋팅 , 달력 처음 켯을때 오늘날 클릭 셋팅
        binding.homeCalnedarCv.addDecorators( sundayDecorator, saturdayDecorator)

        //오늘 선택
        //binding.homeCalnedarCv.setSelectedDate(CalendarDay())

        //markedDates 더미 데이터 생산 -> 나중에 여기에 api로 받아와야함
        val stringDate = ArrayList<String>()
        stringDate.add("2022-01-18")
        stringDate.add("2022-01-10")
        stringDate.add("2022-01-03")


        //체크해야할 날짜들이 marekdDates에 있음.
        val list: MutableList<CalendarDay> = ArrayList()
        val calendar = Calendar.getInstance()

        for (date in stringDate) {
            // might be a more elegant way to do this part, but this is very explicit
            val year = date.substring(0,4).toInt()
            val month: Int = date.substring(5,7).toInt() -1  // months are 0-based in Calendar
            val day: Int = date.substring(8,10).toInt()
            calendar[year, month] = day
            val calendarDay = CalendarDay.from(calendar)
            list.add(calendarDay)
            Log.d("day","${calendarDay}")
        }
        val calendarDays = list;

        binding.homeCalnedarCv.addDecorators(EventDecorator(requireContext(), R.color.main_color , calendarDays))

        binding.homeCalnedarCv.setOnDateChangedListener { widget, date, selected ->
            val intent = Intent(requireContext(), MyRecordActivity::class.java)
            startActivity(intent)
        }

    }

    fun setListener(){
        binding.mainMyCrewBtnTv.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_container_fl , MyCrewFragment())
                        .addToBackStack(null).commit()
        }
    }


}