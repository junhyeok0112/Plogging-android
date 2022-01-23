package org.techtown.plogging_android.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import org.techtown.plogging_android.Home.Adapter.RecordImageViewpagerAdapter
import org.techtown.plogging_android.Home.Retrofit.HomeRetrofitInterface
import org.techtown.plogging_android.Home.Retrofit.RecordResult
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.databinding.ActivityMyRecordBinding
import org.techtown.plogging_android.getRetorfit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRecordActivity : AppCompatActivity() {

    lateinit var binding :ActivityMyRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val idx = intent.getIntExtra("idx",0)
        if(idx != 0){
            getRecord(idx)
        } else{
            setZero()
        }
        Log.d("idx" , "${idx}")


    }

    fun getRecord(idx : Int){
        val recordSrvice = getRetorfit().create(HomeRetrofitInterface::class.java)

        recordSrvice.getRecord(MyApplication.jwt!! ,idx).enqueue(object : Callback<RecordResult>{
            override fun onResponse(call: Call<RecordResult>, response: Response<RecordResult>) {
                val resp = response.body()!!
                when(resp.code){
                  1000 -> {
                      bindRecord(resp)
                  }
                  else -> {
                      setZero()
                      Toast.makeText(this@MyRecordActivity, "기록이 없습니다. ", Toast.LENGTH_SHORT).show()
                  }
                }

            }

            override fun onFailure(call: Call<RecordResult>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun bindRecord(result: RecordResult){
        binding.myRecordKcalNum.text = result.result.calorie
        binding.myRecordDistanceNum.text = result.result.distance
        binding.myRecordTimeNum.text = result.result.time
        binding.myRecordDateTv.text = result.result.date
        binding.myRecrodWatchTv.text = result.result.record
        if(result.result.pictures.size == 2){
            setViewPager(result.result.pictures[0] , result.result.pictures[1])
        } else{
            setViewPager(result.result.pictures[0] , "")
        }
    }

    fun setZero(){
        binding.myRecordKcalNum.text = "0"
        binding.myRecordDistanceNum.text = "0"
        binding.myRecordTimeNum.text = "0"
        binding.myRecordDateTv.text = "0"
        binding.myRecrodWatchTv.text = "0"
    }

    //무조건 2개 온다고 생각
    fun setViewPager(map :String , auth:String ){
        val recordImageViewpagerAdapter = RecordImageViewpagerAdapter(this)
        if(map != ""){
            Log.d("setView" ,"실행1")
            recordImageViewpagerAdapter.addFragment(RecordImageFragment("${map}"))
        }
        if(auth != ""){
            Log.d("setView" ,"실행2")
            recordImageViewpagerAdapter.addFragment(RecordImageFragment("${auth}"))
        }
        binding.myRecordMapVp.adapter = recordImageViewpagerAdapter
        binding.myRecordMapVp.offscreenPageLimit = 2

        binding.myRecordIndicators.setViewPager(binding.myRecordMapVp)
        binding.myRecordIndicators.createIndicators(2, 0)

        binding.myRecordMapVp.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.myRecordIndicators.animatePageSelected(position)
            }
        })


    }
}