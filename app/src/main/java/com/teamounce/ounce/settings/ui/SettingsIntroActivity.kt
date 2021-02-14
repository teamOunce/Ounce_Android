package com.teamounce.ounce.settings.ui


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ActivitySettingsIntroBinding
import com.teamounce.ounce.util.StatusBarUtil

class SettingsIntroActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: ActivitySettingsIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings_intro)
        binding.btnIntroBack.setOnClickListener { finish() }
        initViewPager()
    }

    private fun initViewPager() {
        viewPager = binding.vpIntroOunce
        val pagerAdapter = SettingIntroAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        setBackgroundColor(getColorFromResource(R.color.ounceintro_one))
                        binding.btnIntroLeft.visibility = View.GONE
                    }
                    1 -> {
                        setBackgroundColor(getColorFromResource(R.color.ounceintro_two))
                        binding.btnIntroLeft.visibility = View.VISIBLE
                        binding.btnIntroRight.visibility = View.VISIBLE
                    }
                    else -> {
                        setBackgroundColor(getColorFromResource(R.color.ounceintro_three))
                        binding.btnIntroRight.visibility = View.GONE
                    }
                }
            }
        })
        binding.btnIntroLeft.setOnClickListener {
            binding.vpIntroOunce.setCurrentItem(binding.vpIntroOunce.currentItem - 1, true)
        }
        binding.btnIntroRight.setOnClickListener {
            binding.vpIntroOunce.setCurrentItem(binding.vpIntroOunce.currentItem + 1, true)
        }
    }

    private inner class SettingIntroAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> SettingIntroFragment(R.drawable.who_made_ounce_img_1)
                1 -> SettingIntroFragment(R.drawable.who_made_ounce_img_2)
                else -> SettingIntroFragment(R.drawable.who_made_ounce_img_3)
            }
        }
    }

    private fun getColorFromResource(color: Int): Int = resources.getColor(color, null)

    private fun setBackgroundColor(color: Int) {
        binding.ounceintroBackground.setBackgroundColor(color)
        StatusBarUtil.setStatusBar(this, color)
    }

    companion object {
        private const val NUM_PAGES = 3
    }

}