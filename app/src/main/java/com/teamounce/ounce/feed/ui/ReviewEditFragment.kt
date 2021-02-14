package com.teamounce.ounce.feed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.FragmentReviewEditBinding
import com.teamounce.ounce.feed.viewmodel.FeedViewModel

class ReviewEditFragment(
    private val viewModel: FeedViewModel,
    private val reviewIndex: Int,
    private val listener: ReviewEditView
) : BottomSheetDialogFragment() {
    private var _binding: FragmentReviewEditBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Demo_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewEditBinding.inflate(
            inflater,
            container,
            false
        )
        setUIListener()
        return binding.root
    }

    private fun setUIListener() {
        binding.linearReviewEdit.setOnClickListener {
            listener.OnEditClickListener(reviewIndex)
            dismiss()
        }
        binding.linearReviewDelete.setOnClickListener {
            listener.OnDeleteClickListener(reviewIndex, viewModel)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface ReviewEditView {
        fun OnEditClickListener(reviewIndex: Int)
        fun OnDeleteClickListener(reviewIndex: Int, viewModel: FeedViewModel)
    }
}