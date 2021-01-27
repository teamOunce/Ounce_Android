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
        bottomSheetView()

    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme


    fun bottomSheetView(){
        bottomSheetAdapter = BottomSheetAdapter()
        recyclerview_catlist.adapter = bottomSheetAdapter

        loadDatas()
    }


    fun loadDatas(){

        datas = mutableListOf(
            SettingCareCatData(
                "이겨울",
                "만난지 1030일째"
            ),
            SettingCareCatData(
                "이겨울",
                "만난지 1030일째"
            ),
            SettingCareCatData(
                "이겨울",
                "만난지 1030일째"
            ),
            SettingCareCatData(
                "이겨울",
                "만난지 1030일째"
            )
        )
        bottomSheetAdapter.settingCareCatData = datas
        bottomSheetAdapter.notifyDataSetChanged()

    }
}