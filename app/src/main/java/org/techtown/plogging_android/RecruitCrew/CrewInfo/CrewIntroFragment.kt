package org.techtown.plogging_android.RecruitCrew.CrewInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.agatha_plog.RecruitFragment
import org.techtown.plogging_android.Mycrew.MyCrewFragment
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentCrewintroBinding

class CrewIntroFragment : Fragment() {

    lateinit var binding : FragmentCrewintroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrewintroBinding.inflate(inflater , container , false)
        setListener()
        return binding.root
    }

    fun setListener(){
        binding.applyBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container_fl , MyCrewFragment())
                .addToBackStack(null).commitAllowingStateLoss()
        }

    }
}