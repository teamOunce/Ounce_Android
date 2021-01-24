package com.teamounce.ounce.main

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.teamounce.ounce.R
import com.teamounce.ounce.settings.ui.SettingCareCatData
import com.teamounce.ounce.util.TransparentStatusBarObject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_main.*

class MainActivity : AppCompatActivity() {
    lateinit var bottomSheet: BottomSheetBehavior<View>
    var reviewCount = 2
    lateinit var bottomSheetAdapter: BottomSheetAdapter
    var datas = mutableListOf<SettingCareCatData>()
    val bottomSheetFragment = BottomSheetFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        bottomSheet = BottomSheetBehavior.from<View>(bottom_sheet_view)
//        bottomSheet.state = BottomSheetBehavior.STATE_DRAGGING
        TransparentStatusBarObject.setStatusBar(this)

//        operateBottomSheet()
        setBackGroundColor()

//        home_btn_record.setOnClickListener {
//            bottomSheetFragment.show(supportFragmentManager,"tag")
//        }

    }
//    fun operateBottomSheet() {
//
//        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//            }
//
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_COLLAPSED -> {
//                        bottom_sheet_bulr.visibility = View.VISIBLE
//                        bottom_sheet_view.visibility = View.VISIBLE
//                    }
//                    BottomSheetBehavior.STATE_HIDDEN -> {
//                        bottom_sheet_bulr.visibility = View.GONE
//                        bottom_sheet_view.visibility = View.GONE
//
//                    }
//                }
//
//                bottomSheetAdapter = BottomSheetAdapter(this@MainActivity)
//                bottomSheet.recyclerview_catlist.adapter = bottomSheetAdapter
//                bottomSheetView(this@MainActivity)
//            }
//        })
//
//        bottomSheet.state = BottomSheetBehavior.STATE_DRAGGING
//
//        bottom_sheet_bulr.setOnClickListener {
//            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
//        }
//    }
//
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_UP -> {
                bottomSheetFragment.show(supportFragmentManager,"tag")
            }
            MotionEvent.ACTION_DOWN -> {
                bottomSheetFragment.dismiss()
            }
        }
        return true
    }

    fun setBackGroundColor() {
        if (reviewCount == 0) {
            main_background.setBackgroundColor(Color.parseColor("#FFFFFF"))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_noth))
        } else if (reviewCount >= 1 && reviewCount <= 5) {
            main_background.setBackgroundColor(Color.parseColor("#FFF9F3"))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_stepone))
        } else if (reviewCount >= 6 && reviewCount <= 10) {
            main_background.setBackgroundColor(Color.parseColor("#FFE9E0"))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_two))

        } else if (reviewCount >= 11 && reviewCount <= 15) {
            main_background.setBackgroundColor(Color.parseColor("#2963A2"))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_three))

        } else if (reviewCount >= 16 && reviewCount <= 20) {
            main_background.setBackgroundColor(Color.parseColor("#ECEAEB"))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_four))
        } else {
            main_background.setBackgroundColor(Color.parseColor("#B8C8C7"))
            imageview_cat.setImageDrawable(getDrawable(R.drawable.ic_home_img_step_five))

        }
    }


}