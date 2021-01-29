package com.teamounce.ounce.main

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.databinding.ItemBottomMainBinding
import com.teamounce.ounce.util.SharedPreferences

class BottomSheetAdapter(private var context: Context) :
    RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder>() {
    val mContext = getActivity(context)
    var bottomSheetProfileData = mutableListOf<BottomSheetProfileData>()
    private var checkedRadioButton: CompoundButton? = null
    private var mSelectedItem = -1
    val bottomSheetFragment = BottomSheetFragment()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BottomSheetViewHolder {
        val binding =
            ItemBottomMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.bind(bottomSheetProfileData[position], position, mSelectedItem)
        holder.binding.catSelectBtn.setOnCheckedChangeListener(checkedChangedListener)

        if (holder.binding.catSelectBtn.isChecked) {
            checkedRadioButton = holder.binding.catSelectBtn
        }
    }

    override fun getItemCount(): Int {
        return bottomSheetProfileData.size
    }

    fun changeCat(position: Int) {
        if (mContext is MainActivity) {
            mContext.setMainViewRetrofit(bottomSheetProfileData[position].catIndex)
            mContext.bottomSheetFragment.dismiss()
        }
    }

    private val checkedChangedListener =
        CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            checkedRadioButton?.apply {
                setChecked(!isChecked)
            }
            checkedRadioButton = compoundButton.apply {
                setChecked(isChecked)
                bottomSheetFragment.fragmentManager?.executePendingTransactions()
                bottomSheetFragment.dialog?.setOnDismissListener {
                    it.dismiss()
                }
            }

        }

    inner class BottomSheetViewHolder(var binding: ItemBottomMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var sharedPreferences = SharedPreferences(mContext)

        fun bind(
            bottomSheetProfileData: BottomSheetProfileData,
            position: Int,
            selectedPosition: Int
        ) {
            binding.bottomSheetProfileData = bottomSheetProfileData

            if (position == sharedPreferences.getCatPositionSelected()) {
                binding.catSelectBtn.isChecked = true
            } else {
                if (selectedPosition == position) {
                    binding.catSelectBtn.isChecked = true
                    changeCat(position)
                    sharedPreferences.setCatPositionSelected(position)
                } else {
                    binding.catSelectBtn.isChecked = false
                }
                binding.catSelectBtn.setOnClickListener {
                    mSelectedItem = adapterPosition
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun getActivity(context: Context): Activity {
        return when (context) {
            is Activity -> context
            is ContextWrapper -> getActivity(context.getBaseContext())
            else -> error("Non Activity based context")
        }
    }
}







