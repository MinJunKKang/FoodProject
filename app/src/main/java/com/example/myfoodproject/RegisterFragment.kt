package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myfoodproject.databinding.FragmentLoginBinding
import com.example.myfoodproject.databinding.FragmentRegisterBinding
import com.example.myfoodproject.repository.UserRepository
import com.example.myfoodproject.viewmodel.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding

    lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)

        mAuth = Firebase.auth

        mDbRef = Firebase.database.reference

        binding.btnMregister.setOnClickListener {

            val nick = binding.etMnick.text.toString().trim()
            val name = binding.etMname.text.toString().trim()
            val email = binding.etMemail.text.toString().trim()
            val password = binding.etMpassword.text.toString().trim()

            signup(nick, name, email, password)
        }

        return binding.root
    }

    private fun signup(nick:String, name:String, email: String, password:String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(requireContext(), "회원가입 성공", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    addUserToDatabase(nick, name, email, mAuth.currentUser?.uid!!)
                } else {

                    Toast.makeText(requireContext(), "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(nick: String, name:String, email: String, uId: String) {
        mDbRef.child("user").child(uId).setValue(UserRepository.User(nick, name, email, uId))
    }
}