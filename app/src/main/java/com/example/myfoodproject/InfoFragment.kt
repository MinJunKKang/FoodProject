package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myfoodproject.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    var binding: FragmentInfoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater)

        binding?.btnRepassword1?.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_newpasswordFragment2)
        }
        binding?.btnRenickname?.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_newnickFragment)
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}