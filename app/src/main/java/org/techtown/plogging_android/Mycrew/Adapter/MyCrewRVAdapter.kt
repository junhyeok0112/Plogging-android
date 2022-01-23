package org.techtown.plogging_android.Mycrew.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.plogging_android.Mycrew.Retrofit.MyCrewResult
import org.techtown.plogging_android.databinding.ListItemMyCrewBinding
import org.techtown.plogging_android.util.dateToString

class MyCrewRVAdapter(var list:List<MyCrewResult>): RecyclerView.Adapter<MyCrewRVAdapter.MyViewHolder>() {

    //클릭 인터페이스 정의
    interface MyItemClickListener {
        fun onItemClick(mycrewResult: MyCrewResult)
    }

    //리스너 객체를 전달받는 함수와 리스너 객체를 저장할 변수
    private lateinit var mItemClickListener : MyItemClickListener       //리스너 객체 저장할 변수

    fun setMyItemClickListener(itemClickLister:MyItemClickListener){
        mItemClickListener = itemClickLister                            //리스너 객체를 전달받아서 리스너 객체 변수에 저장
    }

    inner class MyViewHolder(val binding:ListItemMyCrewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: MyCrewResult){
            binding.listMyCrewTitleTv.text = item.name
            //binding.listMyCrewDayNumTv.text = "${item.targetDay.month}/${item.targetDay.day}"
            binding.listMyCrewDayNumTv.text = item.targetDay.substring(0,10)
            binding.listMyCrewCurrentNumTv.text = "${item.currentNum}/${item.howmany}"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCrewRVAdapter.MyViewHolder {
        val binding = ListItemMyCrewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyCrewRVAdapter.MyViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener{ mItemClickListener.onItemClick(list[position]) }
    }

    override fun getItemCount(): Int = list.size
}