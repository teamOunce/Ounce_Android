package com.teamounce.ounce.settings


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.teamounce.ounce.R

class SettingIntroActivity : AppCompatActivity(){
    private lateinit var viewPager : ViewPager2
    private var datas = mutableListOf<SettingsIntroData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_intro)
        loadDatas()
        viewPager = findViewById(R.id.vp_intro_ounce)

        val introAdapter = SettingIntroAdapter(this)
        viewPager.adapter = introAdapter
    }
    fun loadDatas(){
        datas.apply{
            add(
                SettingsIntroData(
                    "R.drawable.ic_who_made_ounce_img_1"
                )
            )
            add(
                SettingsIntroData(
                    "R.drawable.ic_who_made_ounce_img_2"
                )
            )
            add(
                SettingsIntroData(
                    "R.drawable.ic_who_made_ounce_img_3"
                )
            )
        }

    }

    override fun onBackPressed() {
        if(viewPager.currentItem == 0){
            super.onBackPressed()
        }else{
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class SettingIntroAdapter ( fa : FragmentActivity) : FragmentStateAdapter(fa) {
        //private var layoutInflater : LayoutInflater?=null
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = SettingIntroFragment()

    }
    companion object{
        private const val NUM_PAGES = 3
    }

}