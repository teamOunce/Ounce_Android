package com.teamounce.ounce.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamounce.ounce.R
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.singleton.RetrofitObjects
import com.teamounce.ounce.databinding.BottomSheetMainBinding
import com.teamounce.ounce.util.SharedPreferences
import retrofit2.Call
import retrofit2.Response


class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var bottomSheetAdapter: BottomSheetAdapter
    private var bottomSheetDatas = mutableListOf<BottomSheetProfileData>()
    private lateinit var prefs: SharedPreferences
    private var _binding: BottomSheetMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetMainBinding.inflate(inflater, container, false)
        setAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = SharedPreferences(view.context)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    private fun setAdapter() {
        bottomSheetAdapter = BottomSheetAdapter(requireContext())
        binding.recyclerviewCatlist.adapter = bottomSheetAdapter
        loadBottomsheetDatas()
    }

    private fun loadBottomsheetDatas() {
        RetrofitObjects.getMainService()
            .bottomSheetProfileRetrofit(OunceLocalRepository.catIndex)
            .enqueue(object :
                retrofit2.Callback<BottomSheetResponseData> {
                override fun onFailure(call: Call<BottomSheetResponseData>, t: Throwable) {
                    Log.d("서버 실패", "${t}")
                }
                override fun onResponse(
                    call: Call<BottomSheetResponseData>,
                    response: Response<BottomSheetResponseData>
                ) {
                    if (response.isSuccessful) {
                        bottomSheetDatas = response.body()!!.data

                        response.body()!!.data.forEach{
                            if(it.state) OunceLocalRepository.catIndex = it.catIndex
                        }

                        bottomSheetAdapter.bottomSheetProfileData = bottomSheetDatas
                        prefs.setCatList(bottomSheetDatas)
                        bottomSheetAdapter.notifyDataSetChanged()
                    }
                }
            })
    }

    override fun dismiss() {
        super.dismiss()
    }


}