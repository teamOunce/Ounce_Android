package com.teamounce.ounce.review.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.FragmentToolTipBinding

class ToolTipFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentToolTipBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Demo_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // dialog!!.window?.setBackgroundDrawableResource(R.drawable.bottom_sheet_background)
        _binding = FragmentToolTipBinding.inflate(
            inflater,
            container,
            false
        )
        binding.btnTooltipCheck.setOnClickListener { dismiss() }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}