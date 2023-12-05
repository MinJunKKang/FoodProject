package com.example.myfoodproject

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        binding?.btnWreview?.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_personalReviewFragment)
        }
        binding?.btnLogout?.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_infoFragment_to_loginFragment)
        }
        binding?.btnDelaccount?.setOnClickListener {
            // "계정 탈퇴" 버튼 클릭 시 다이얼로그 띄우기
            showWithdrawDialog()
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
                Log.d("UserInfo", "Name: ${user.name}, Nick: ${user.nick}")
                // 사용자 정보가 null이 아닌 경우에만 표시
                binding?.txtName?.text = user.name
                binding?.txtNickname?.text = user.nick
            }
        })
    }

    private fun showWithdrawDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("계정 탈퇴")
            .setMessage("정말로 탈퇴하시겠습니까?")
            .setPositiveButton("네") { _, _ ->
                // "네" 버튼을 클릭했을 때의 동작
                viewModel.deleteUser { success, message ->
                    if (success) {
                        findNavController().navigate(R.id.action_infoFragment_to_loginFragment)
                    } else {
                        // 탈퇴 실패 시 토스트 메시지 표시
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("아니오") { dialog, _ ->
                // "아니오" 버튼을 클릭했을 때의 동작
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}