package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myfoodproject.databinding.FragmentNewpasswordBinding
import com.example.myfoodproject.viewmodel.UserViewModel

class NewpasswordFragment : Fragment() {

    private var binding: FragmentNewpasswordBinding? = null
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewpasswordBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnRepassword2?.setOnClickListener {
            val currentPassword = binding?.curPassword?.text.toString()
            val newPassword = binding?.etNewPassword?.text.toString()
            val confirmPassword = binding?.etChkPassword?.text.toString()

            if (currentPassword.isNotBlank() && newPassword.isNotBlank() && confirmPassword.isNotBlank() && newPassword == confirmPassword) {
                viewModel.updatePassword(currentPassword, newPassword) { success, message ->
                    if (success) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_newpwFragment_to_infoFragment)
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}