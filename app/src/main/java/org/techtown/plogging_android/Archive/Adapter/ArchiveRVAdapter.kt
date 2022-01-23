package org.techtown.plogging_android.Archive.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.databinding.ListItemImageBinding


class ArchiveRVAdapter(var list:List<String>) : RecyclerView.Adapter<ArchiveRVAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ListItemImageBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : String){
            val imgRef:StorageReference = MyApplication.storage.reference.child("${item}")
            Glide.with(binding.root.context)
                .load(imgRef)
                .into(binding.imageList)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArchiveRVAdapter.MyViewHolder {
        val binding = ListItemImageBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ArchiveRVAdapter.MyViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}