package com.teamounce.ounce.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ActivityMainBinding
import com.teamounce.ounce.review.ui.SearchActivity
import com.teamounce.ounce.settings.SettingsActivity
import com.teamounce.ounce.settings.ui.SettingCareCatData
import com.teamounce.ounce.util.OnSwipeTouchListener
import com.teamounce.ounce.util.TransparentStatusBarObject

class MainActivity : AppCompatActivity() {
    lateinit var bottomSheet: BottomSheetBehavior<View>
    var reviewCount = 8
    lateinit var bottomSheetAdapter: BottomSheetAdapter
    var datas = mutableListOf<SettingCareCatData>()
    val bottomSheetFragment = BottomSheetFragment()
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        TransparentStatusBarObject.setStatusBar(this)
        setBackGroundColor()

        //한입더! 버튼 눌렀을 때, SearchActivity
        activityMainBinding.homeBtnRecord.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java )
            startActivity(intent)
        }

        //햄버거바 눌렀을 때, SettingActivity
        activityMainBinding.btnMenu.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java )
            startActivity(intent)


        }
        operatebottomSheet()

        //수첩 아이콘 눌렀을 때, FeedActivity
//        btn_drawer.setOnClickListener{
//            val intent = Intent(this, FeedActivity::class.java)
//            startActivty(intent)
//        }

    }

    fun operatebottomSheet() {
        activityMainBinding.mainBackground.setOnTouchListener(object: OnSwipeTouchListener(this){
            override fun onSwipeUp() {
                super.onSwipeUp()
                bottomSheetFragment.show(supportFragmentManager,"tag")
            }
        })
    }

    fun setBackGroundColor() {
        if (reviewCount == 0) {
            activityMainBinding.mainBackground.setBackgroundColor(getColor(R.color.white))
            activityMainBinding.imageviewCat.setImageDrawable(getDrawable(R.drawable.ic_home_img_noth))
        } else if (reviewCount >= 1 && reviewCount <= 5) {
            activityMainBinding.mainBackground.setBackgroundColor(getColor(R.color.mainbackground_one))
            activityMainBinding.imageviewCat.setImageDrawable(getDrawable(R.drawable.ic_home_img_stepone))
        } else if (reviewCount >= 6 && reviewCount <= 10) {
            activityMainBinding.mainBackground.setBackgroundColor(getColor(R.color.mainbackground_two))
            activityMainBinding.imageviewCat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_two))

        } else if (reviewCount >= 11 && reviewCount <= 15) {
            activityMainBinding.mainBackground.setBackgroundColor(getColor(R.color.mainbackground_three))
            activityMainBinding.imageviewCat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_three))

        } else if (reviewCount >= 16 && reviewCount <= 20) {
            activityMainBinding.mainBackground.setBackgroundColor(getColor(R.color.mainbackground_four))
            activityMainBinding.imageviewCat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_four))
        } else {
            activityMainBinding.mainBackground.setBackgroundColor(getColor(R.color.mainbackground_five))
            activityMainBinding.imageviewCat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_five))

        }
    }


}