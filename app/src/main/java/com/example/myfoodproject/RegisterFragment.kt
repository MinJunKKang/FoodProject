package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myfoodproject.databinding.FragmentRegisterBinding
import com.example.myfoodproject.viewmodel.UserViewModel

class RegisterFragment : Fragment() {

    private var binding: FragmentRegisterBinding? = null
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnMregister?.setOnClickListener {

            val nick = binding?.etMnick?.text.toString().trim()
            val name = binding?.etMname?.text.toString().trim()
            val email = binding?.etMemail?.text.toString().trim()
            val password = binding?.etMpassword?.text.toString().trim()

            // 회원가입
            viewModel.signUp(nick, name, email, password) { success ->
                if (success) {
                    Toast.makeText(requireContext(), "회원가입 성공", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                    Toast.makeText(requireContext(), "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}