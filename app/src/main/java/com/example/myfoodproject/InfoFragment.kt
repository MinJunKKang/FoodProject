package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myfoodproject.databinding.FragmentInfoBinding
import com.example.myfoodproject.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth

class InfoFragment : Fragment() {
    var binding: FragmentInfoBinding? = null
    lateinit var mAuth : FirebaseAuth
    private val userRepository = UserRepository()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater)

        // mAuth 변수 초기화
        mAuth = FirebaseAuth.getInstance()

        binding?.btnRepassword1?.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_newpasswordFragment2)
        }
        binding?.btnRenickname?.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_newnickFragment)
        }
        binding?.btnLogout?.setOnClickListener {
            mAuth.signOut()
            findNavController().navigate(R.id.action_infoFragment_to_loginFragment)
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