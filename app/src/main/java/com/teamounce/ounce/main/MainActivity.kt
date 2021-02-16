package com.teamounce.ounce.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.teamounce.ounce.R
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.api.MainService
import com.teamounce.ounce.data.remote.singleton.RetrofitObjects
import com.teamounce.ounce.databinding.ActivityMainBinding
import com.teamounce.ounce.feed.ui.FeedActivity
import com.teamounce.ounce.review.ui.SearchActivity
import com.teamounce.ounce.settings.ui.SettingsActivity
import com.teamounce.ounce.util.OnSwipeTouchListener
import com.teamounce.ounce.util.SharedPreferences
import com.teamounce.ounce.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    val bottomSheetFragment = BottomSheetFragment()
    lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewRetrofitInterface: MainService
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        sharedPreferences = SharedPreferences(this)
        StatusBarUtil.setStatusBar(this)

        operateBottomSheet()
        setMainViewRetrofit()
        goToSearchActivity()
        goToSettingsActivity()
        goToFeedBackActivity()
        setBackGroundResource()
    }


    private fun goToFeedBackActivity() {
        //수첩 아이콘 눌렀을 때, FeedActivity
        btn_drawer.setOnClickListener {
            val intent = Intent(this, FeedActivity::class.java)
            intent.putExtra("catName", textview_cat_name.text.toString())
            startActivity(intent)
        }
    }

    private fun goToSearchActivity() {
        //한입더! 버튼 눌렀을 때, SearchActivity
        activityMainBinding.homeBtnRecord.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToSettingsActivity() {
        //햄버거바 눌렀을 때, SettingActivity
        activityMainBinding.btnMenu.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun operateBottomSheet() {
        activityMainBinding.mainBackground.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeUp() {
                super.onSwipeUp()
                bottomSheetFragment.show(supportFragmentManager, "tag")
            }
        })

        activityMainBinding.textviewCatName.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "bottomsheet")
        }

        activityMainBinding.imageviewDropbox.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "bottomsheet")
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setBackGroundResource() {
        when (OunceLocalRepository.reviewCount) {
            0 -> setBackgroundHotSourcr(R.color.white, R.raw.home_img_nothing)
            in BACKGROUND_ONE_RANGE -> setBackgroundHotSourcr(
                R.color.mainbackground_one,
                R.raw.home_img_step0
            )
            in BACKGROUND_TWO_RANGE -> setBackgroundHotSourcr(
                R.color.mainbackground_two,
                R.raw.home_img_step1
            )
            in BACKGROUND_THREE_RANGE -> setBackgroundHotSourcr(
                R.color.mainbackground_three,
                R.raw.home_img_step2
            )
            in BACKGROUND_FOUR_RANGE -> setBackgroundHotSourcr(
                R.color.mainbackground_four,
                R.raw.home_img_step3
            )
            else -> setBackgroundHotSourcr(R.color.mainbackground_five, R.raw.home_img_step4)
        }
    }

    private fun setBackgroundHotSourcr(backgroundColor: Int, lottieAnimation: Int) {
        activityMainBinding.imageviewCat.setAnimation(lottieAnimation)
        activityMainBinding.mainBackground.setBackgroundColor(getColor(backgroundColor))
    }

    private fun setCatName(name: String) {
        activityMainBinding.textviewCatName.text = name
    }

    private fun setCatDday(dday: Int) {
        activityMainBinding.textviewCatDday.text = "만난지 ${dday}일 째"
    }

    fun setMainViewRetrofit() {
        mainViewRetrofitInterface = RetrofitObjects.getMainService()
        mainViewRetrofitInterface.mainViewRetrofit(
            OunceLocalRepository.catIndex
        ).enqueue(object : retrofit2.Callback<MainViewResponseData> {
            override fun onFailure(call: Call<MainViewResponseData>, t: Throwable) {
                Log.d("서버통신 실패", "$t")
            }

            override fun onResponse(
                call: Call<MainViewResponseData>,
                response: Response<MainViewResponseData>
            ) {
                if (response.isSuccessful) {
                    setCatName(response.body()!!.data.catName)
                    setCatDday(response.body()!!.data.fromMeet)
                    OunceLocalRepository.reviewCount = response.body()!!.data.reviewCount
                    OunceLocalRepository.catName = response.body()!!.data.catName

                    if (sharedPreferences.getCatPositionSelected() == null) {
                        sharedPreferences.setCatPositionSelected(0)
                    }
                    Log.d("이것은 서버통신 성공", "이것이 서버")
                    Log.d("고양이 review count", response.body()!!.data.reviewCount.toString())
                    Log.d("local review count", OunceLocalRepository.reviewCount.toString())

                } else {
                    showError(response.errorBody())
                }
            }
        })
    }

    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.d("errorbody ----> ", ob.getString("status"))
    }

    companion object {
        private val BACKGROUND_ONE_RANGE = 1..5
        private val BACKGROUND_TWO_RANGE = 6..10
        private val BACKGROUND_THREE_RANGE = 11..15
        private val BACKGROUND_FOUR_RANGE = 16..20
    }
}