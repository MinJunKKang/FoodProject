package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myfoodproject.databinding.FragmentEntryBinding
class EntryFragment : Fragment() {
    lateinit var binding: FragmentEntryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryBinding.inflate(inflater)

        binding?.btnInfo?.setOnClickListener {
            findNavController().navigate(R.id.action_entryFragment_to_infoFragment)
        }
        binding?.btnWrite?.setOnClickListener {
            findNavController().navigate(R.id.action_entryFragment_to_writeFragment)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}