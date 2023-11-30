package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myfoodproject.databinding.FragmentWriteBinding
import com.example.myfoodproject.viewmodel.PostViewModel

class WriteFragment : Fragment() {

    private var binding: FragmentWriteBinding? = null
    private val postViewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnPost?.setOnClickListener {
            val title = binding?.etTitle?.text.toString() // content를 입력받는 부분
            val content = binding?.etDetail?.text.toString() // title을 입력받는 부분

            postViewModel.addPost(title, content) { success, message ->
                if( success ) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}