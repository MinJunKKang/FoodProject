package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myfoodproject.databinding.FragmentInfoBinding
import com.example.myfoodproject.viewmodel.UserViewModel

class InfoFragment : Fragment() {
    private var binding: FragmentInfoBinding? = null

    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnRepassword1?.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_newpasswordFragment2)
        }
        binding?.btnRenickname?.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_newnickFragment)
        }
        binding?.btnLogout?.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_infoFragment_to_loginFragment)
        }

        // FirebaseUser를 가져와서 UI에 표시하는 Observer
        viewModel.getCurrentUser().observe(viewLifecycleOwner, Observer { currentUser ->
            if (currentUser != null) {
                // 사용자 정보가 null이 아닌 경우에만 observeUser 호출
                viewModel.observeUser(currentUser.uid)
            }
        })

        // UI 업데이트를 위한 Observer
        viewModel.userInfo.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                // 사용자 정보가 null이 아닌 경우에만 표시
                binding?.txtName?.text = user.name
                binding?.txtNickname?.text = user.nick
            }
        })
    }
}