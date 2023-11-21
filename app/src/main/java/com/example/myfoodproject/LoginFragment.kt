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

    lateinit var binding:FragmentLoginBinding

    lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        mAuth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            login(email, password)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return binding.root
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    findNavController().navigate(R.id.action_loginFragment_to_entryFragment)
                    Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(requireContext(), "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }
}