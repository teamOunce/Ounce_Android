package com.teamounce.ounce.review.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityRecordBinding

class RecordActivity : BindingActivity<ActivityRecordBinding>(R.layout.activity_record) {
    private val foodImages = mutableListOf<ImageSliderFragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodImages.add(ImageSliderFragment("https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png"))
        binding.vpRecordSlider.adapter = CatFoodImageSliderPagerAdapter(this)
    }

    private inner class CatFoodImageSliderPagerAdapter(fa: FragmentActivity) :
        FragmentStateAdapter(fa) {
        override fun getItemCount() = foodImages.size

        override fun createFragment(position: Int): Fragment {
            return foodImages[position]
        }
    }
}