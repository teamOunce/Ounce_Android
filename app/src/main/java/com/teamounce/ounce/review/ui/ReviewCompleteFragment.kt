package com.teamounce.ounce.review.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.FragmentReviewCompleteBinding

class ReviewCompleteFragment(
    private val preference: Int,
    private val disMissClickListener: DisMissClickListener
) : DialogFragment() {
    private var _binding: FragmentReviewCompleteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        _binding = FragmentReviewCompleteBinding.inflate(
            inflater,
            container,
            false
        )
        initView()
        return binding.root
    }

    private fun initView() {
        isCancelable = false
        setUIListener()
        setDialogBackgroundImage()
    }

    private fun setUIListener() {
        binding.btnReviewComplete.setOnClickListener { dismiss() }
    }

    private fun setDialogBackgroundImage() {
        when(preference) {
            in 1..3 -> binding.imgReviewComplete.setImageResource(R.drawable.ic_complete_not_enough)
            else -> binding.imgReviewComplete.setImageResource(R.drawable.ic_complete_enough)
        }
    }

    override fun dismiss() {
        super.dismiss()
        disMissClickListener.onClick(requireContext())
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface DisMissClickListener {
        fun onClick(context: Context)
    }
}