package com.teamounce.ounce.main

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamounce.ounce.R
import com.teamounce.ounce.settings.SettingCustomDialog
import com.teamounce.ounce.settings.ui.SettingCareCatData
import com.teamounce.ounce.util.VerticalItemDecoration
import kotlinx.android.synthetic.main.bottom_sheet_main.*


class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var bottomSheetAdapter: BottomSheetAdapter
    var datas = mutableListOf<SettingCareCatData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //dialog?.window?.setBackgroundDrawableResource(R.drawable.bottom_sheet_box)
        bottomSheetView(view)

    }


    fun bottomSheetView(view:View){
        bottomSheetAdapter = BottomSheetAdapter(view.context)
        recyclerview_catlist.adapter = bottomSheetAdapter

        loadDatas()
    }


    fun loadDatas(){
        datas.apply {
            add(
                SettingCareCatData(
                    "이겨울",
                    "만난지 1030일째"
                )
            )
            add(
                SettingCareCatData(
                    "주예",
                    "만난지 63일째"
                )
            )
            add(
                SettingCareCatData(
                    "애옹",
                    "만난지 3970일째"
                )
            )
            add (
                SettingCareCatData(
                    "예인",
                    "만난지 300일"
                )
            )

            bottomSheetAdapter.datas = datas
            bottomSheetAdapter.notifyDataSetChanged()
        }

    }
}