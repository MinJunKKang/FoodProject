package com.example.foodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.foodproject.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    var binding: FragmentCategoryBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding?.btnRamen?.setOnClickListener{
            findNavController().navigate(R.id.action_categoryFragment_to_ramenFragment)
        }
        binding?.btnSushi?.setOnClickListener{
            findNavController().navigate(R.id.action_categoryFragment_to_sushiFragment)
        }
        binding?.btnTonkatsu?.setOnClickListener{
            findNavController().navigate(R.id.action_categoryFragment_to_tonkatsuFragement)
        }
    }

    override fun onDestroyView(){
        super.onDestroyView()
        binding = null
    }

}