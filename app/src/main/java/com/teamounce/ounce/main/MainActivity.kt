package com.teamounce.ounce.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.teamounce.ounce.R
import com.teamounce.ounce.review.ui.SearchActivity
import com.teamounce.ounce.settings.SettingsActivity
import com.teamounce.ounce.settings.ui.SettingCareCatData
import com.teamounce.ounce.util.TransparentStatusBarObject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_main.*

class MainActivity : AppCompatActivity() {
    lateinit var bottomSheet: BottomSheetBehavior<View>
    var reviewCount = 8
    lateinit var bottomSheetAdapter: BottomSheetAdapter
    var datas = mutableListOf<SettingCareCatData>()
    val bottomSheetFragment = BottomSheetFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TransparentStatusBarObject.setStatusBar(this)
        setBackGroundColor()

        //한입더! 버튼 눌렀을 때, SearchActivity
        home_btn_record.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java )
            startActivity(intent)
        }

        //햄버거바 눌렀을 때, SettingActivity
        btn_menu.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java )
            startActivity(intent)
        }


        //수첩 아이콘 눌렀을 때, FeedActivity
//        btn_drawer.setOnClickListener{
//            val intent = Intent(this, FeedActivity::class.java)
//            startActivty(intent)
//        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_UP -> {
                bottomSheetFragment.show(supportFragmentManager,"tag")
            }
        }
        return true
    }

    fun setBackGroundColor() {
        if (reviewCount == 0) {
            main_background.setBackgroundColor(getColor(R.color.white))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_noth))
        } else if (reviewCount >= 1 && reviewCount <= 5) {
            main_background.setBackgroundColor(getColor(R.color.mainbackground_one))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_stepone))
        } else if (reviewCount >= 6 && reviewCount <= 10) {
            main_background.setBackgroundColor(getColor(R.color.mainbackground_two))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_two))

        } else if (reviewCount >= 11 && reviewCount <= 15) {
            main_background.setBackgroundColor(getColor(R.color.mainbackground_three))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_three))

        } else if (reviewCount >= 16 && reviewCount <= 20) {
            main_background.setBackgroundColor(getColor(R.color.mainbackground_four))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_four))
        } else {
            main_background.setBackgroundColor(getColor(R.color.mainbackground_five))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_five))

        }
    }


}