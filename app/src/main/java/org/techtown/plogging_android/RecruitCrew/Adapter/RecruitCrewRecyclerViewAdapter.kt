package org.techtown.plogging_android.RecruitCrew.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.plogging_android.RecruitCrew.Retrofit.RecruitResult
import org.techtown.plogging_android.databinding.ListItemRecruitCrewBinding

class RecruitCrewRecyclerViewAdapter(var list:List<RecruitResult>): RecyclerView.Adapter<RecruitCrewRecyclerViewAdapter.MyViewHolder>() {

    //클릭 인터페이스 정의
    interface MyItemClickListener {
        fun onItemClick(recruitResult: RecruitResult)
    }

    //리스너 객체를 전달받는 함수와 리스너 객체를 저장할 변수
    private lateinit var mItemClickListener : MyItemClickListener       //리스너 객체 저장할 변수

    fun setMyItemClickListener(itemClickLister:MyItemClickListener){
        mItemClickListener = itemClickLister                            //리스너 객체를 전달받아서 리스너 객체 변수에 저장
    }

    inner class MyViewHolder(val binding:ListItemRecruitCrewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : RecruitResult){
            binding.listMyCrewTitleTv.text = item.name
            binding.listMyCrewDayNumTv.text = item.targetDay
            binding.listMyCrewCurrentNumTv.text = "${item.currentNum}/${item.howmany}"
            binding.listMyCrewRegionTv.text = item.region
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecruitCrewRecyclerViewAdapter.MyViewHolder {
        val binding = ListItemRecruitCrewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecruitCrewRecyclerViewAdapter.MyViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener{ mItemClickListener.onItemClick(list[position]) }
    }

    override fun getItemCount(): Int = list.size
}