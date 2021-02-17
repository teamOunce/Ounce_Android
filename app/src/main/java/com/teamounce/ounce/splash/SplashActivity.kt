package com.teamounce.ounce.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.teamounce.ounce.R
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.login.ui.LoginActivity
import com.teamounce.ounce.login.viewmodel.LoginViewModel
import com.teamounce.ounce.main.MainActivity
import com.teamounce.ounce.register.ui.SignUpActivity
import com.teamounce.ounce.util.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        subscribeLoginPermission()
        StatusBarUtil.setStatusBar(this, R.color.orange)
        val handler = Handler(Looper.getMainLooper())
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
        val thread = object : Thread() {
            override fun run() {
                runOnUiThread {
                    handler.postDelayed({
                        if (isRegistered()) {
                            when (OunceLocalRepository.loginFrom) {
                                OunceLocalRepository.KAKAO -> kakaoLogin(this@SplashActivity)
                                OunceLocalRepository.GOOGLE -> googleLogin()
                                else -> throw IllegalArgumentException("도대체 어디서 로그인 한거니 ${OunceLocalRepository.loginFrom}")
                            }
                        } else {
                            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                            finish()
                        }

                    }, 1000)
                }
            }
        }
        thread.start()
    }

    private fun subscribeLoginPermission() {
        loginViewModel.isCatNull.observe(this) {
            if (it) {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }

    private fun isRegistered(): Boolean = OunceLocalRepository.loginFrom != "unregistered"

    private fun googleLogin() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        auth = Firebase.auth
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun kakaoLogin(context: Context) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) Log.e("Kakao", "로그인 실패", error)
            else if (token != null) {
                Log.i(
                    "Kakao",
                    "로그인 성공 ${token.accessToken}, ${token.refreshToken}, ${token.scopes}"
                )
                requestAdditionalUserInfo()
            }
        }
        if (LoginClient.instance.isKakaoTalkLoginAvailable(context)) {
            LoginClient.instance.loginWithKakaoTalk(context, callback = callback)
        }
        else LoginClient.instance.loginWithKakaoAccount(context, callback = callback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken!!)
            } catch (e: ApiException) {
                Log.w("Login", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { loginViewModel.googleLogin(idToken) }
                else { Toast.makeText(this, "Auth Fail", Toast.LENGTH_SHORT).show() }
            }
    }

    private fun requestAdditionalUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("Kakao", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                if (user.kakaoAccount?.emailNeedsAgreement == false) Log.d("Kakao", "사용자계정에 이메일 없음")
                else if (user.kakaoAccount?.emailNeedsAgreement == true) Log.d("Kakao", "사용자에게")
                loginViewModel.kakaoLogin(user.id.toString())
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}