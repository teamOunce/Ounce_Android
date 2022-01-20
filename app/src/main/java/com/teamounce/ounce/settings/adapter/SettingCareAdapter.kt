package com.teamounce.ounce.settings.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.singleton.RetrofitObjects
import com.teamounce.ounce.databinding.ItemSettingCatcareBinding
import com.teamounce.ounce.main.BottomSheetProfileData
import com.teamounce.ounce.main.MainDeleteResponseData
import com.teamounce.ounce.settings.ui.SettingsCareActivity
import com.teamounce.ounce.settings.util.SettingCustomDialogBuilder
import com.teamounce.ounce.settings.util.SettingCustomDialogListener
import retrofit2.Call
import retrofit2.Response

class SettingCareAdapter(
    private val context: Context,
    private val listener: CatDeleteButton
) : RecyclerView.Adapter<SettingCareViewHolder>() {
    var datas = mutableListOf<BottomSheetProfileData>()
    private val fragmentManager = (context as SettingsCareActivity).supportFragmentManager

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingCareViewHolder {
        val binding = ItemSettingCatcareBinding.inflate(LayoutInflater.from(context), parent, false)
        return SettingCareViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SettingCareViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(datas[position])

        if (datas[position].catIndex == OunceLocalRepository.catIndex) {
            holder.binding.settingCatdlt.apply {
                this.setBackgroundResource(R.drawable.ic_cat_gray_selected)
                this.width = ViewGroup.LayoutParams.WRAP_CONTENT
                this.height = ViewGroup.LayoutParams.WRAP_CONTENT
                this.text = null
            }

        }

        holder.binding.settingCatdlt.setOnClickListener {
            if (datas[position].catIndex == OunceLocalRepository.catIndex) {
                val dialog = SettingCustomDialogBuilder()
                    .setTitle("현재 여행중인 고양이예요")
                    .setSubTitle("지우길 원한다면,\n다른 고양이로 접속해 주세요!")
                    .setNegativeButton("확인")
                    .setDisablePositiveButton(true)
                    .create()
                dialog.show(fragmentManager, dialog.tag)
            } else {
                val dialog = SettingCustomDialogBuilder()
                    .setTitle("정말 지우시겠어요?")
                    .setSubTitle("저장했던 기록들도 함께 사라져요.")
                    .setPositiveButton("네")
                    .setNegativeButton("잘못 눌렀어요")
                    .setButtonClickListener(object : SettingCustomDialogListener {
                        override fun onClickPositiveButton() {
                            setMainDeleteRetrofit(position)
                            Toast.makeText(context, "고양이를 삭제했습니다.", Toast.LENGTH_SHORT).show()
                        }

                        override fun onClickNegativeButton() {
                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .create()

                dialog.show(fragmentManager, dialog.tag)
            }
        }
    }

    fun setMainDeleteRetrofit(position: Int) {
        RetrofitObjects.getMainService()
            .mainDeleteRetrofit(datas[position].catIndex)
            .enqueue(object : retrofit2.Callback<MainDeleteResponseData> {
                override fun onFailure(call: Call<MainDeleteResponseData>, t: Throwable) {
                    Log.d("서버 통신 실패", "${t}")
                }

                override fun onResponse(
                    call: Call<MainDeleteResponseData>,
                    response: Response<MainDeleteResponseData>
                ) {
                    if (response.isSuccessful) {
                        Log.d("고양이 삭제 성공", response.body()!!.data.toString())
                        listener.OnClickListener(position)
                    } else {
                        Log.d("서버에러", response.body()!!.responseMessage)
                    }
                }
            })
    }

    interface CatDeleteButton {
        fun OnClickListener(position: Int)
    }
}
