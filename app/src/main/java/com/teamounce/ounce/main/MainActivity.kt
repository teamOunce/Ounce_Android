package com.teamounce.ounce.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
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


class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    val bottomSheetFragment = BottomSheetFragment()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        LifeCycleEventLogger(javaClass.name).log()
        binding.executePendingBindings()
        sharedPreferences = SharedPreferences(this)
        setUIListener()
        refreshData()
        setBackgroundResource()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUIListener() {
        with(binding) {
            //수첩 아이콘 눌렀을 때, FeedActivity
            btnDrawer.setOnClickListener {
                val intent = Intent(this@MainActivity, FeedActivity::class.java)
                intent.putExtra("catName", textview_cat_name.text.toString())
                startActivity(intent)
            }
            //한입더! 버튼 눌렀을 때, SearchActivity
            homeBtnRecord.setOnClickListener {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
            //햄버거바 눌렀을 때, SettingActivity
            btnMenu.setOnClickListener {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            mainBackground.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
                override fun onSwipeUp() {
                    bottomSheetFragment.show(supportFragmentManager, "bottomsheet")
                }
            })
            textviewCatName.setOnClickListener {
                bottomSheetFragment.show(supportFragmentManager, "bottomsheet")
            }
            imageviewDropbox.setOnClickListener {
                bottomSheetFragment.show(supportFragmentManager, "bottomsheet")
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setBackgroundResource() {
//        with(binding) {
//            lottieFile = ScreenAnimation.by(reviewCount)
//            mainBackground.setBackgroundColor(BackgroundColor.of(reviewCount))
//            StatusBarUtil.setStatusBar(
//                this@MainActivity,
//                BackgroundColor.of(reviewCount),
//                BackgroundColor.alsoStatusBar(reviewCount)
//            )
//        }
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
        StatusBarUtil.setStatusBar(
            this@MainActivity,
            BackgroundColor.of(OunceLocalRepository.reviewCount),
            BackgroundColor.alsoStatusBar(OunceLocalRepository.reviewCount)
        )
    }

    private fun setBackgroundHotSourcr(backgroundColor: Int, lottieAnimation: Int) {
        binding.imageviewCat.setAnimation(lottieAnimation)
        binding.mainBackground.setBackgroundColor(getColor(backgroundColor))
    }

    private fun setCatName(name: String) {
        binding.textviewCatName.text = name
    }

    private fun setCatDday(dday: Int) {
        binding.textviewCatDday.text = "만난 지 ${dday}일째"
    }

    fun refreshData() {
        RetrofitObjects.getMainService()
            .mainViewRetrofit(OunceLocalRepository.catIndex)
            .enqueue(object : retrofit2.Callback<MainViewResponseData> {
                override fun onFailure(call: Call<MainViewResponseData>, t: Throwable) {
                    Log.d("서버통신 실패", "$t")
                }

                override fun onResponse(
                    call: Call<MainViewResponseData>,
                    response: Response<MainViewResponseData>
                ) {
                    if (response.isSuccessful) {
                        setCatName(response.body()!!.data.catName)
                        setCatDday((response.body()!!.data.fromMeet) + 1)
                        OunceLocalRepository.reviewCount = response.body()!!.data.reviewCount
                        OunceLocalRepository.catName = response.body()!!.data.catName
                        if (sharedPreferences.getCatPositionSelected() == null) {
                            sharedPreferences.setCatPositionSelected(0)
                        }
                        Log.d("이것은 서버통신 성공", "이것이 서버")
                        Log.d("고양이 review count", response.body()!!.data.reviewCount.toString())
                        Log.d("local review count", OunceLocalRepository.reviewCount.toString())
                        setBackgroundResource()
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