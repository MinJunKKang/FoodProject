package com.example.myfoodproject

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myfoodproject.databinding.FragmentWriteBinding
import com.example.myfoodproject.viewmodel.PostViewModel

class WriteFragment : Fragment() {

    private var selectedImageUri: Uri? = null
    private var binding: FragmentWriteBinding? = null
    private val postViewModel: PostViewModel by activityViewModels()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // 이미지가 선택된 경우, 선택된 이미지의 Uri를 저장
        selectedImageUri = uri
        // 선택된 이미지를 ImageView에 표시
        binding?.selectedImage?.setImageURI(selectedImageUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이미지 추가 버튼에 대한 클릭 리스너 설정
        binding?.btnImage?.setOnClickListener {
            openGallery()
        }

        binding?.btnPost?.setOnClickListener {
            val title = binding?.etTitle?.text.toString()
            val content = binding?.etDetail?.text.toString()
            val rating = binding?.etRating?.text.toString().toFloatOrNull()

            if ( rating == null || rating < 0 || rating > 5 ) {
                Toast.makeText(requireContext(), "평점은 0부터 5까지의 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            postViewModel.addPost(title, content, rating, selectedImageUri) { success, message ->
                if( success ) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
            findNavController().navigate(R.id.action_writeFragment_to_reviewHomeFragment)
        }
    }

    private fun openGallery() {
        getContent.launch("image/*")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}