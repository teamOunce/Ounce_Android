package com.teamounce.ounce.main

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.RetrofitService
import com.teamounce.ounce.databinding.ActivityMainBinding
import com.teamounce.ounce.databinding.ItemBottomMainBinding
import com.teamounce.ounce.settings.ui.SettingCareCatData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_bottom_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheetAdapter(private var context: Context) : RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder>() {
    var bottomSheetProfileData = mutableListOf<BottomSheetProfileData>()
    private var checkedRadioButton: CompoundButton? = null
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
        holder.bind(bottomSheetProfileData[position])
        holder.binding.catSelectBtn.setOnCheckedChangeListener(checkedChangedListener)

        holder.binding.catSelectBtn.setOnClickListener {
            changeCat(position)
        }

        if (holder.binding.catSelectBtn.isChecked) {
            checkedRadioButton = holder.binding.catSelectBtn
            changeCat(position)
        }
    }

    override fun getItemCount(): Int {
        return bottomSheetProfileData.size
    }

    fun changeCat(position: Int){
        val mContext = getActivity(context)
        if(mContext is MainActivity) {
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
        fun bind(bottomSheetProfileData: BottomSheetProfileData) {
            binding.bottomSheetProfileData = bottomSheetProfileData
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







