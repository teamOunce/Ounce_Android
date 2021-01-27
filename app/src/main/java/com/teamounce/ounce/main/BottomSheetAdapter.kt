package com.teamounce.ounce.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemBottomMainBinding
import com.teamounce.ounce.settings.ui.SettingCareCatData
import kotlinx.android.synthetic.main.item_bottom_main.view.*

class BottomSheetAdapter : RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder>() {

    var settingCareCatData = mutableListOf<SettingCareCatData>()
    private var checkedRadioButton: CompoundButton? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BottomSheetViewHolder {
        val binding =
            ItemBottomMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.bind(settingCareCatData[position])

        holder.binding.catSelectBtn.setOnCheckedChangeListener(checkedChangedListener)

        if (holder.binding.catSelectBtn.isChecked) checkedRadioButton = holder.binding.catSelectBtn


    }


    override fun getItemCount(): Int {
        return settingCareCatData.size
    }


    private val checkedChangedListener =
        CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            checkedRadioButton?.apply {
                setChecked(!isChecked)
            }
            checkedRadioButton = compoundButton.apply {
                setChecked(isChecked)
            }

        }

    inner class BottomSheetViewHolder(var binding: ItemBottomMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(settingCareCatData: SettingCareCatData) {
            binding.settingCareCatData = settingCareCatData
        }
    }
}







