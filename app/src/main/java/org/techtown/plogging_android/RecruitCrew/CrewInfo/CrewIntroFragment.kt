package org.techtown.plogging_android.RecruitCrew.CrewInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.techtown.plogging_android.databinding.FragmentCrewintroBinding

class CrewIntroFragment : Fragment() {

    lateinit var binding : FragmentCrewintroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrewintroBinding.inflate(inflater , container , false)

        return binding.root
    }
}