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
import com.teamounce.ounce.util.CatInfoStore
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {
    @Inject
    lateinit var prefs: CatInfoStore
    private var bottomSheetAdapter: BottomSheetAdapter? = null
    private var bottomSheetDatas = mutableListOf<BottomSheetProfileData>()
    private var _binding: BottomSheetMainBinding? = null
    private val binding get() = _binding!!
    var listener: BottomSheetAdapter.OnRefreshListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetMainBinding.inflate(inflater, container, false)
        setAdapter()
        return binding.root
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    private fun setAdapter() {
        bottomSheetAdapter = BottomSheetAdapter(prefs) {
            listener?.update()
            dismiss()
        }
        binding.recyclerviewCatlist.adapter = bottomSheetAdapter
        loadBottomsheetDatas()
    }

    private fun loadBottomsheetDatas() {
        RetrofitObjects.getMainService()
            .bottomSheetProfileRetrofit(OunceLocalRepository.catIndex)
            .enqueue(object : retrofit2.Callback<BottomSheetResponseData> {
                override fun onFailure(call: Call<BottomSheetResponseData>, t: Throwable) {
                    Log.d("서버 실패", "$t")
                }

                override fun onResponse(
                    call: Call<BottomSheetResponseData>,
                    response: Response<BottomSheetResponseData>
                ) {
                    if (response.isSuccessful) {
                        bottomSheetDatas = response.body()!!.data

                        response.body()?.data?.forEach {
                            if (it.state) OunceLocalRepository.catIndex = it.catIndex
                        }

                        bottomSheetAdapter?.bottomSheetProfileData = bottomSheetDatas
                        prefs.setCatList(bottomSheetDatas)
                        bottomSheetAdapter?.notifyDataSetChanged()
                    }
                }
            })
    }

    override fun onDestroyView() {
        bottomSheetAdapter = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(listener: BottomSheetAdapter.OnRefreshListener) =
            BottomSheetFragment().apply {
                this.listener = listener
            }
    }
}