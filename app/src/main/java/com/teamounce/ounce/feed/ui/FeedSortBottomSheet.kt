package com.teamounce.ounce.feed.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamounce.ounce.data.enum.EnumFeedSort
import com.teamounce.ounce.databinding.BottomSheetFeedSortBinding
import com.teamounce.ounce.feed.adapter.FeedSortAdapter

class FeedSortBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetFeedSortBinding? = null
    private val binding get() = _binding!!

    private val sortAdapter by lazy { FeedSortAdapter() }

    private var sortListener: FeedSortAdapter.ItemListener? = null

    fun setSortListener(l: FeedSortAdapter.ItemListener) {
        sortListener = l
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showSortBottomSheet(context: AppCompatActivity, sort: EnumFeedSort?) {
        sort?.let { _sort ->
            sortAdapter.checkedPosition = _sort.ordinal
            sortAdapter.notifyDataSetChanged()
            show(context.supportFragmentManager, "feed_sort")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFeedSortBinding.inflate(inflater, container, false)
        initRcv()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRcv() {
        sortAdapter.run {
            sortStatus = listOf(
                EnumFeedSort.WRITE.title,
                EnumFeedSort.PREFERENCE_HIGH.title,
                EnumFeedSort.PREFERENCE_LOW.title
            )
            setClickItemListener(object : FeedSortAdapter.ItemListener {
                override fun clickSortStatus(status: EnumFeedSort) {
                    sortListener?.clickSortStatus(status)
//                    dismiss()
                }
            })
            notifyDataSetChanged()
        }

        binding.rcvFeedSort.run {
            adapter = sortAdapter
        }
    }
}