package com.example.homecook.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.homecook.databinding.FragmentIntroBinding

class IntroFragment : Fragment() {

    private val TAG = "IntroFragmentTAG"
    private lateinit var _binding: FragmentIntroBinding
    private val binding get() = _binding
    private var imageId: Int = 0
    private var title = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageId = it.getInt("imageId")
            title = it.getString("title").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = title
        Glide.with(requireContext())
            .load(imageId)
            .into(binding.imageView)
    }
}