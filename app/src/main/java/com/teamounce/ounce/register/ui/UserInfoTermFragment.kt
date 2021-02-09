package com.teamounce.ounce.register.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.FragmentUserInfoTermBinding

class UserInfoTermFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentUserInfoTermBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInfoTermBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal = d.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheetInternal?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
        }
    }

    override fun onStart() {
        super.onStart()
        val height = resources.displayMetrics.heightPixels
        dialog!!.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}