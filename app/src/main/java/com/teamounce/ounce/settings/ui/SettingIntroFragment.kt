package com.teamounce.ounce.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemOunceintroBinding


class SettingIntroFragment(private val src: Int) : Fragment() {
    private lateinit var binding: ItemOunceintroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.item_ounceintro, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImageView()
    }

    private fun initImageView() {
        binding.ounceIntroImage.setImageResource(src)
    }

}