package com.teamounce.ounce.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.databinding.ItemBottomMainBinding
import com.teamounce.ounce.util.CatInfoStore

class BottomSheetAdapter(
    private val catInfoStore: CatInfoStore,
    private val listener: OnRefreshListener
) : RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder>() {
    fun interface OnRefreshListener {
        fun update()
    }

    var bottomSheetProfileData = mutableListOf<BottomSheetProfileData>()
    private var checkedRadioButton: CompoundButton? = null
    private var mSelectedItem = -1
    val bottomSheetFragment = BottomSheetFragment()

    inner class BottomSheetViewHolder(var binding: ItemBottomMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            bottomSheetProfileData: BottomSheetProfileData,
            position: Int,
            selectedPosition: Int
        ) {
            binding.bottomSheetProfileData = bottomSheetProfileData
            binding.catSelectBtn.setOnCheckedChangeListener(checkedChangedListener)

            if (position == catInfoStore.getCatPositionSelected()) {
                binding.catSelectBtn.isChecked = true
            } else {
                if (selectedPosition == position) {
                    binding.catSelectBtn.isChecked = true
                    changeCat(position)
                    catInfoStore.setCatPositionSelected(position)
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
        OunceLocalRepository.catIndex = bottomSheetProfileData[position].catIndex
        listener.update()
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
}
