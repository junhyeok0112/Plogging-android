package org.techtown.plogging_android.Archive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import org.techtown.plogging_android.Archive.Adapter.ArchiveRVAdapter
import org.techtown.plogging_android.Archive.Retrofit.ArchiveResponse
import org.techtown.plogging_android.Archive.Retrofit.ArchiveRetroInterface
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.databinding.FragmentMyArchiveBinding
import org.techtown.plogging_android.getRetorfit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyArchiveFragment : Fragment() {

    lateinit var binding : FragmentMyArchiveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyArchiveBinding.inflate(inflater , container, false)

        getArchive()
        return binding.root
    }

    fun getArchive(){
        val archiveService = getRetorfit().create(ArchiveRetroInterface::class.java)
        archiveService.getArchive(MyApplication.jwt!!).enqueue(object : Callback<ArchiveResponse>{
            override fun onResponse(
                call: Call<ArchiveResponse>,
                response: Response<ArchiveResponse>
            ) {
                val resp = response.body()!!
                binding.archiveNameTv.text = "${resp.result.nickName} 님의\n플로깅 아카이브"
                val number = 5
                val archiveRVAdapter = ArchiveRVAdapter(resp.result.archiveImg)
                binding.archiveRv.layoutManager = GridLayoutManager(requireContext(),number)
                binding.archiveRv.adapter = archiveRVAdapter
            }

            override fun onFailure(call: Call<ArchiveResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}