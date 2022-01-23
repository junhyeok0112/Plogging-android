package org.techtown.plogging_android.RecruitCrew.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.plogging_android.RecruitCrew.Retrofit.CrewMemberResult
import org.techtown.plogging_android.databinding.ListItemMemberBinding


class MemberRVAdapter(var list:List<CrewMemberResult>) : RecyclerView.Adapter<MemberRVAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ListItemMemberBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : CrewMemberResult){
            binding.listMemberName.text = item.name
            binding.listMemberIntro.text = item.comment
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberRVAdapter.MyViewHolder {
        val binding = ListItemMemberBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MemberRVAdapter.MyViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}