package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myfoodproject.databinding.FragmentNewnickBinding
import com.example.myfoodproject.viewmodel.UserViewModel

class NewnickFragment : Fragment() {
    private var binding: FragmentNewnickBinding? = null
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewnickBinding.inflate(inflater)

        binding?.btnNewnickname?.setOnClickListener {
            val newNickname = binding?.etRenick?.text?.toString()
            if (!newNickname.isNullOrBlank()) {
                viewModel.updateNickname(newNickname) { success, message ->
                    if (success) {
                        findNavController().navigate(R.id.action_newnickFragment_to_infoFragment)
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}