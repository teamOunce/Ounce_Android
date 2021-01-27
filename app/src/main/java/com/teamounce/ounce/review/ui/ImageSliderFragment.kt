package com.teamounce.ounce.review.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.FragmentImageSliderBinding
import com.teamounce.ounce.singleton.BindingAdapters

class ImageSliderFragment(private val uri: String) : Fragment() {
    private lateinit var binding: FragmentImageSliderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_image_slider, container, false)
        BindingAdapters.setSrcFromUrl(binding.imgSlider, uri)
        return binding.root
    }

}