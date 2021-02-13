package com.teamounce.ounce.main

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamounce.ounce.R
import com.teamounce.ounce.RetrofitService
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.api.MainService
import com.teamounce.ounce.data.remote.singleton.RetrofitObjects
import com.teamounce.ounce.settings.SettingCustomDialog
import com.teamounce.ounce.settings.ui.SettingCareCatData
import com.teamounce.ounce.util.SharedPreferences
import com.teamounce.ounce.util.VerticalItemDecoration
import kotlinx.android.synthetic.main.bottom_sheet_main.*
import retrofit2.Call
import retrofit2.Response


class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var bottomSheetAdapter: BottomSheetAdapter
    var bottomSheetDatas = mutableListOf<BottomSheetProfileData>()
    lateinit var mainViewRetrofitInterface: MainViewRetrofitInterface
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = SharedPreferences(view.context)
        //dialog?.window?.setBackgroundDrawableResource(R.drawable.bottom_sheet_box)
        bottomSheetView(view)

    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme


    fun bottomSheetView(view: View) {
        bottomSheetAdapter = BottomSheetAdapter(view.context)
        recyclerview_catlist.adapter = bottomSheetAdapter
        loadBottomsheetDatas()
    }


    fun loadBottomsheetDatas() {

        mainViewRetrofitInterface = RetrofitService.create(MainViewRetrofitInterface::class.java)
        mainViewRetrofitInterface.bottomSheetProfileRetrofit(
            OunceLocalRepository.catIndex
        ).enqueue(object : retrofit2.Callback<BottomSheetResponseData> {

            override fun onFailure(call: Call<BottomSheetResponseData>, t: Throwable) {
                Log.d("서버 실패", "${t}")
            }

            override fun onResponse(
                call: Call<BottomSheetResponseData>,
                response: Response<BottomSheetResponseData>
            ) {
                if (response.isSuccessful) {
                    bottomSheetDatas = response.body()!!.data
                    bottomSheetAdapter.bottomSheetProfileData = bottomSheetDatas
                    prefs.setCatList(bottomSheetDatas)
                    Log.d("datas", response.body()!!.data.toString())
                    bottomSheetAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}