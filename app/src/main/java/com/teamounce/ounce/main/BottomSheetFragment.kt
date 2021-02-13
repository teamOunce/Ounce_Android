package com.teamounce.ounce.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamounce.ounce.R
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.api.MainService
import com.teamounce.ounce.data.remote.singleton.RetrofitObjects
import com.teamounce.ounce.util.SharedPreferences
import kotlinx.android.synthetic.main.bottom_sheet_main.*
import retrofit2.Call
import retrofit2.Response


class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var bottomSheetAdapter: BottomSheetAdapter
    var bottomSheetDatas = mutableListOf<BottomSheetProfileData>()
    lateinit var mainViewRetrofitInterface: MainService
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
        val token = OunceLocalRepository.userRefreshToken
        mainViewRetrofitInterface = RetrofitObjects.getMainService()
        mainViewRetrofitInterface.bottomSheetProfileRetrofit(
            token,
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