package com.teamounce.ounce.settings

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.teamounce.ounce.R
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.login.ui.LoginActivity
import com.teamounce.ounce.main.MainActivity
import com.teamounce.ounce.util.StatusBarUtil
import com.teamounce.ounce.util.VerticalItemDecoration
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_setting_catdltdialog.*

class SettingsActivity : AppCompatActivity() {
    lateinit var settingButtonAdapter: SettingButtonAdapter
    val datas = mutableListOf<SettingButtonData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        StatusBarUtil.setStatusBar(this)

        settingButton()
        ounceversion(this)

        //설정뷰에 오른쪽 화살표 눌렀을때 MainActivity
        btn_drawer.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        txt_ounceagree.setOnClickListener {
            val intent = Intent(this, SettingsAgreeActivity::class.java)
            startActivity(intent)
        }

        txt_ouncepolicy.setOnClickListener {
            val intent = Intent(this, SettingsPolicyActivity::class.java)
            startActivity(intent)
        }

        txt_ounceopensrc.setOnClickListener {
            val intent = Intent(this, SettingsOpensourceActivity::class.java)
            startActivity(intent)
        }
        txt_ounceintro.setOnClickListener {
            val intent = Intent(this, SettingsIntroActivity::class.java)
            startActivity(intent)
        }


        //로그아웃 버튼 눌렀을 때
        txt_ouncelogout.setOnClickListener {
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
                        }
                        startActivity(intent)
                    }

                    override fun onClickNegativeButton() {}
                })
                .create()
            dialog.show(supportFragmentManager, dialog.tag)
        }

        //탈퇴하기 버튼 눌렀을 때
        txt_ounceout.setOnClickListener {
            val dialog = SettingCustomDialogBuilder()
                .setTitle("탈퇴 시 계정 정보,기록 등\n모든 것이 삭제돼요.\n정말 탈퇴하시겠어요?")
                .setPositiveButton("네")
                .setNegativeButton("잘못 눌렀어요")
                .setButtonClickListener(object : SettingCustomDialogListener {
                    override fun onClickPositiveButton() {
                        Toast.makeText(this@SettingsActivity, "ㅋ", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onClickNegativeButton() {
                        Toast.makeText(this@SettingsActivity, "", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SettingsActivity, SettingsActivity::class.java)
                        startActivity(intent)
                    }
                })
                .create()

            dialog.show(supportFragmentManager, dialog.tag)

        }
    }

    fun settingButton() {
        settingButtonAdapter = SettingButtonAdapter(this)
        recyclerview_setting_buttons.adapter = settingButtonAdapter
        recyclerview_setting_buttons.addItemDecoration(VerticalItemDecoration(12))
        loadSettingButton()

    }


    fun loadSettingButton() {
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
                    "문의하기"
                )
            )

            add(
                SettingButtonData(
                    R.drawable.ic_backup,
                    "기록 백업 및 복원"
                )
            )

            settingButtonAdapter.datas = datas
            settingButtonAdapter.notifyDataSetChanged()
        }
    }

    fun ounceversion(context: Context) {
        val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val version = info.versionName

        txt_ounceversion.text = version
    }
}