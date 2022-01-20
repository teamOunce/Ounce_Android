package com.teamounce.ounce.settings.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import com.teamounce.ounce.R
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.singleton.RetrofitObjects
import com.teamounce.ounce.databinding.ActivitySettingsBinding
import com.teamounce.ounce.login.ui.LoginActivity
import com.teamounce.ounce.settings.util.SettingCustomDialogBuilder
import com.teamounce.ounce.settings.util.SettingCustomDialogListener
import com.teamounce.ounce.settings.adapter.SettingButtonAdapter
import com.teamounce.ounce.settings.model.ResponseExpire
import com.teamounce.ounce.settings.model.SettingButtonData
import com.teamounce.ounce.util.StatusBarUtil
import com.teamounce.ounce.util.VerticalItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsActivity : AppCompatActivity() {
    lateinit var settingButtonAdapter: SettingButtonAdapter
    val datas = mutableListOf<SettingButtonData>()
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtil.setStatusBar(this)

        settingButton()
        ounceversion(this)

        //설정뷰에 오른쪽 화살표 눌렀을때 MainActivity
        binding.btnDrawer.setOnClickListener { finish() }

        binding.txtOunceagree.setOnClickListener {
            val intent = Intent(this, SettingsAgreeActivity::class.java)
            startActivity(intent)
        }
        binding.txtOuncepolicy.setOnClickListener {
            val intent = Intent(this, SettingsPolicyActivity::class.java)
            startActivity(intent)
        }

        binding.txtOunceopensrc.setOnClickListener {
            val intent = Intent(this, SettingsOpensourceActivity::class.java)
            startActivity(intent)
        }
        binding.txtOunceintro.setOnClickListener {
            val intent = Intent(this, SettingsIntroActivity::class.java)
            startActivity(intent)
        }


        //로그아웃 버튼 눌렀을 때
        binding.txtOuncelogout.setOnClickListener {
            val dialog = SettingCustomDialogBuilder()
                .setTitle("로그아웃 하시겠어요?")
                .setPositiveButton("네")
                .setNegativeButton("잘못 눌렀어요")
                .setButtonClickListener(object : SettingCustomDialogListener {
                    override fun onClickPositiveButton() {
                        Toast.makeText(this@SettingsActivity, "로그아웃 되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        OunceLocalRepository.apply {
                            userRefreshToken = ""
                            userAccessToken = ""
                            catIndex = -1
                            loginFrom = "unregistered"
                        }
                        startActivity(intent)
                    }

                    override fun onClickNegativeButton() {}
                })
                .create()
            dialog.show(supportFragmentManager, dialog.tag)
        }

        //탈퇴하기 버튼 눌렀을 때
        binding.txtOunceout.setOnClickListener {
            val dialog = SettingCustomDialogBuilder()
                .setTitle("탈퇴 시 계정 정보,기록 등\n모든 것이 삭제돼요.\n정말 탈퇴하시겠어요?")
                .setPositiveButton("네")
                .setNegativeButton("잘못 눌렀어요")
                .setButtonClickListener(object : SettingCustomDialogListener {
                    override fun onClickPositiveButton() { expireUser() }
                    override fun onClickNegativeButton() {}
                })
                .create()

            dialog.show(supportFragmentManager, dialog.tag)

        }
    }

    private fun expireUser() {
        when(OunceLocalRepository.loginFrom) {
            OunceLocalRepository.KAKAO -> expireKakao()
            OunceLocalRepository.GOOGLE -> expireGoogle()
            else -> throw IllegalArgumentException("잘못된 접근입니다: ${OunceLocalRepository.loginFrom}")
        }
    }

    private fun expireKakao() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Toast.makeText(this, "탈퇴 과정 중 오류가 발생했습니다: KAKAO", Toast.LENGTH_SHORT).show()
            } else {
                expireOunce()
            }
        }
    }

    private fun expireGoogle() {
        Firebase.auth
            .currentUser
            ?.delete()
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    expireOunce()
                } else {
                    Toast.makeText(this, "탈퇴 과정 중 오류가 발생했습니다: GOOGLE", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun expireOunce() {
        RetrofitObjects.getMainService()
            .deleteUser()
            .enqueue(object : Callback<ResponseExpire>{
                override fun onResponse(
                    call: Call<ResponseExpire>,
                    response: Response<ResponseExpire>
                ) {
                    val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    OunceLocalRepository.apply {
                        userRefreshToken = ""
                        userAccessToken = ""
                        catIndex = -1
                        loginFrom = "unregistered"
                    }
                    startActivity(intent)
                }

                override fun onFailure(call: Call<ResponseExpire>, t: Throwable) {
                    Toast.makeText(this@SettingsActivity, "탈퇴 과정 중 오류가 발생했습니다: SERVER", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun settingButton() {
        settingButtonAdapter = SettingButtonAdapter(this)
        binding.recyclerviewSettingButtons.adapter = settingButtonAdapter
        binding.recyclerviewSettingButtons.addItemDecoration(VerticalItemDecoration(12))
        loadSettingButton()
    }


    private fun loadSettingButton() {
        datas.apply {
            add(
                SettingButtonData(
                    R.drawable.ic_cat,
                    "고양이 관리"
                )
            )
            add(
                SettingButtonData(
                    R.drawable.ic_notice,
                    "인스타그램 및 공지"
                )
            )

            add(
                SettingButtonData(
                    R.drawable.ic__question,
                    "건의함"
                )
            )

            settingButtonAdapter.datas = datas
            settingButtonAdapter.notifyDataSetChanged()
        }
    }

    private fun ounceversion(context: Context) {
        val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val version = info.versionName

        binding.txtOunceversion.text = version
    }
}