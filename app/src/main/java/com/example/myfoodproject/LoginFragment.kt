package com.example.myfoodproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myfoodproject.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        //인증 초기화
        mAuth = Firebase.auth

        //로그인 버튼
        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            login(email, password)
        }

        //회원가입 버튼
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return binding.root
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    //성공 -> 실행
                    findNavController().navigate(R.id.action_loginFragment_to_entryFragment)
                    Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT).show()
                } else {
                    //실패 -> 실행
                    Toast.makeText(requireContext(), "로그인 실패", Toast.LENGTH_SHORT).show()
                    //실패 원인을 보여줌, exception으로 확인 가능
                    Log.e("Login", "Error: ${task.exception}")
                }
            }
    }
}