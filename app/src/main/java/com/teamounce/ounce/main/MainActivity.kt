package com.teamounce.ounce.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.teamounce.ounce.R
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.api.MainService
import com.teamounce.ounce.data.remote.singleton.RetrofitObjects
import com.teamounce.ounce.databinding.ActivityMainBinding
import com.teamounce.ounce.feed.ui.FeedActivity
import com.teamounce.ounce.review.ui.SearchActivity
import com.teamounce.ounce.settings.ui.SettingsActivity
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
    private var reviewCount = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        sharedPreferences = SharedPreferences(this)
        StatusBarUtil.setStatusBar(this)
        setBackGroundColor()

        //한입더! 버튼 눌렀을 때, SearchActivity
        activityMainBinding.homeBtnRecord.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java )
            startActivity(intent)
        }

        setMainViewRetrofit()
        goToSearchActivity()
        goToSettingsActivity()

//        operatebottomSheet()
        //수첩 아이콘 눌렀을 때, FeedActivity
        btn_drawer.setOnClickListener{
            val intent = Intent(this, FeedActivity::class.java)
            intent.putExtra("catName",textview_cat_name.text.toString())
            startActivity(intent)
        }

    }

    fun goToSearchActivity() {
        //한입더! 버튼 눌렀을 때, SearchActivity
        activityMainBinding.homeBtnRecord.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    fun goToSettingsActivity() {
        //햄버거바 눌렀을 때, SettingActivity
        activityMainBinding.btnMenu.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    fun operatebottomSheet() {
//        activityMainBinding.mainBackground.setOnTouchListener(object : OnSwipeTouchListener(this) {
//            override fun onSwipeUp() {
//                super.onSwipeUp()
//                bottomSheetFragment.show(supportFragmentManager, "tag")
//            }
//        })


        activityMainBinding.textviewCatName.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "bottomsheet")
        }

        activityMainBinding.imageviewDropbox.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "bottomsheet")
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setBackGroundColor() {
        when(reviewCount) {
            0 -> setBackgroundResource(R.color.white, R.drawable.ic_home_img_noth)
            in BACKGROUND_ONE_RANGE -> setBackgroundResource(R.color.mainbackground_one, R.drawable.ic_home_img_stepone)
            in BACKGROUND_TWO_RANGE -> setBackgroundResource(R.color.mainbackground_two, R.drawable.ic_home_img_step_two)
            in BACKGROUND_THREE_RANGE -> setBackgroundResource(R.color.mainbackground_three, R.drawable.ic_home_img_step_three)
            in BACKGROUND_FOUR_RANGE -> setBackgroundResource(R.color.mainbackground_four, R.drawable.ic_home_img_step_four)
            else -> setBackgroundResource(R.color.mainbackground_five, R.drawable.ic_home_img_step_five)
        }
    }

    private fun setBackgroundResource(backgroundColor: Int, backgroundResource: Int ){
        activityMainBinding.mainBackground.setBackgroundColor(getColor(backgroundColor))
        activityMainBinding.imageviewCat.setImageDrawable(getDrawable(backgroundResource))
    }



    fun setCatName(name: String) {
        activityMainBinding.textviewCatName.text = name
    }

    fun setCatDday(dday: Int) {
        activityMainBinding.textviewCatDday.text = "만난지 ${dday}일 째"
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_UP -> {
                bottomSheetFragment.show(supportFragmentManager, "tag")
            }
        }
        return true
    }

    fun setMainViewRetrofit() {
        val token = OunceLocalRepository.userAccessToken
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
                    reviewCount = response.body()!!.data.reviewCount
                    OunceLocalRepository.catName = response.body()!!.data.catName
                    setBackGroundColor()

                    if (sharedPreferences.getCatPositionSelected() == null) {
                        sharedPreferences.setCatPositionSelected(0)
                    }
                    Log.d("이것은 서버통신 성공", "이것이 서버")
                    Log.d("고양이 이름", response.body().toString())
                } else {
                    showError(response.errorBody())
                }
            }
        })
    }

    private fun showError(error : ResponseBody?){
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