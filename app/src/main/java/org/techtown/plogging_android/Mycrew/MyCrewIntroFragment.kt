package org.techtown.plogging_android.Mycrew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentMyCrewintroBinding

class MyCrewIntroFragment : Fragment() {

    lateinit var binding : FragmentMyCrewintroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCrewintroBinding.inflate(inflater, container,false)
        return binding.root
    }


    fun setListener(){
        binding.myIntroQuitBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container_fl , MyCrewFragment())
                .addToBackStack(null).commitAllowingStateLoss()
        }
    }
}