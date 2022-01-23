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
import org.techtown.plogging_android.Home.Retrofit.HomeGetResult
import org.techtown.plogging_android.Home.Retrofit.HomeRetrofitInterface
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.Mycrew.MyCrewFragment
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentHomeBinding
import org.techtown.plogging_android.getRetorfit
import org.techtown.plogging_android.util.dateToString
import org.techtown.plogging_android.util.stringToDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sun.bob.mcalendarview.vo.DateData
import java.util.*


class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    var stringData = ArrayList<String>()
    lateinit var homeResult :HomeGetResult

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setListener()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setCalendar()
    }

    fun setCalendar(){

        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        //달력 주말 색 셋팅 , 달력 처음 켯을때 오늘날 클릭 셋팅
        binding.homeCalnedarCv.addDecorators( sundayDecorator, saturdayDecorator)

        binding.homeCalnedarCv.setOnDateChangedListener { widget, date, selected ->
            Log.d("date", "${date}")
            val stringDate = dateToString(date.date)
            Log.d("date", "${stringDate}")
            var targetIdx:Int = 0

            for(res in homeResult.result.calendar){
                if(res.date.substring(0,10) == stringDate){
                    targetIdx = res.plogIdx
                }
            }

            val intent = Intent(requireContext(), MyRecordActivity::class.java)
            intent.putExtra("idx" , targetIdx)
            startActivity(intent)
        }

        getHome()

    }

    fun setListener(){
        binding.mainMyCrewBtnTv.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_container_fl , MyCrewFragment())
                        .addToBackStack(null).commit()
        }


    }

    fun getHome(){
        val homeService = getRetorfit().create(HomeRetrofitInterface::class.java)

        homeService.getHome(MyApplication.jwt!!).enqueue(object: Callback<HomeGetResult>{
            override fun onResponse(call: Call<HomeGetResult>, response: Response<HomeGetResult>) {
                val resp = response.body()!!
                homeResult = resp
                when(resp.code){
                  1000 -> {
                      //markedDates 더미 데이터 생산 -> 나중에 여기에 api로 받아와야함
                      for(date in resp.result!!.calendar!!){
                          stringData.add(date.date)
                      }

                      //체크해야할 날짜들이 marekdDates에 있음.
                      val list: MutableList<CalendarDay> = ArrayList()
                      val calendar = Calendar.getInstance()

                      for (date in stringData) {
                          // might be a more elegant way to do this part, but this is very explicit
                          val year = date.substring(0,4).toInt()
                          val month: Int = date.substring(5,7).toInt() -1  // months are 0-based in Calendar
                          val day: Int = date.substring(8,10).toInt()
                          calendar[year, month] = day
                          val calendarDay = CalendarDay.from(calendar)
                          list.add(calendarDay)
                      }
                      val calendarDays = list;
                      binding.homeCalnedarCv.addDecorators(EventDecorator(requireContext(), R.color.main_color , calendarDays))
                      bindInfo(homeResult)

                  }
                  2002 -> {
                      Log.d("home" , "유효하지 않은 JWT입니다.")
                  }
                }
            }

            override fun onFailure(call: Call<HomeGetResult>, t: Throwable) {

            }
        })
    }

    fun bindInfo(homeResult : HomeGetResult){
        binding.homeTitleTv.text = "${homeResult.result.name}님의 \n 플로깅 기록"
        binding.homeCountNumTv.text = "${homeResult.result.plogSum}"
        binding.homeDistanceNumTv.text = "${homeResult.result.distanceSum}"
        binding.homeTotalTimeNumTv.text = "${homeResult.result.timeSum}"

    }



}