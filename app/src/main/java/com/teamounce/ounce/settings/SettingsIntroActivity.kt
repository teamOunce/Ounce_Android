package com.teamounce.ounce.settings

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ActivitySettingsIntroBinding
import kotlinx.android.synthetic.main.activity_settings_intro.*
import kotlinx.android.synthetic.main.item_ounceintro.*

class SettingsIntroActivity : AppCompatActivity(){
    private lateinit var viewPager : ViewPager2
    private lateinit var binding: ActivitySettingsIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings_intro)

        initViewPager()
    }

    fun initViewPager(){
        viewPager = binding.vpIntroOunce
        val pagerAdapter = SettingIntroAdapter(this)
        viewPager.adapter = pagerAdapter
    }

    private inner class SettingIntroAdapter ( fa : FragmentActivity) : FragmentStateAdapter(fa) {
        //private var layoutInflater : LayoutInflater?=null
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int) {
            return when(position){
                0 -> {
                    SettingIntroFragment(R.drawable.who_made_ounce_img_1)
                    ounceintro_background.setBackgroundColor(Color.parseColor("#9CFF60"))
                }
                1 -> {
                    SettingIntroFragment(R.drawable.who_made_ounce_img_2)
                    ounceintro_background.setBackgroundColor(Color.parseColor("#C98AEC"))
                }
                else -> {
                    SettingIntroFragment(R.drawable.who_made_ounce_img_3)
                    ounceintro_background.setBackgroundColor(Color.parseColor("#D47272"))
                }
            }
        }

    }
    companion object{
        private const val NUM_PAGES = 3
    }

}