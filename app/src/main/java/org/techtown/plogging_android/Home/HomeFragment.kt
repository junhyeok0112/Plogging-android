package org.techtown.plogging_android.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.techtown.plogging_android.Dacorator.SaturdayDecorator
import org.techtown.plogging_android.Dacorator.SundayDecorator
import org.techtown.plogging_android.Dacorator.TodayDecorator
import org.techtown.plogging_android.Plogging.MyRecordActivity
import org.techtown.plogging_android.databinding.FragmentHomeBinding
import java.util.*


class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setCalendar()
        return binding.root
    }

    fun setCalendar(){

        var startTimeCalendar = Calendar.getInstance()
        var endTimeCalendar = Calendar.getInstance()

        val currentYear = startTimeCalendar.get(Calendar.YEAR)
        val currentMonth = startTimeCalendar.get(Calendar.MONTH)
        val currentDate = startTimeCalendar.get(Calendar.DATE)

        val stCalendarDay = CalendarDay(currentYear, currentMonth, currentDate)
        val enCalendarDay = CalendarDay(endTimeCalendar.get(Calendar.YEAR), endTimeCalendar.get(Calendar.MONTH), endTimeCalendar.get(Calendar.DATE))

        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        val todayDecorator = TodayDecorator(requireContext())

        binding.homeCalnedarCv.addDecorators(sundayDecorator, saturdayDecorator,todayDecorator)

        binding.homeCalnedarCv.setOnDateChangedListener { widget, date, selected ->
            val intent = Intent(requireContext(),MyRecordActivity::class.java)
            startActivity(intent)
        }
    }



}