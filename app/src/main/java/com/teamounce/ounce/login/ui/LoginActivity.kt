package com.teamounce.ounce.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityLoginBinding
import com.teamounce.ounce.login.viewmodel.LoginViewModel
import com.teamounce.ounce.util.TransparentStatusBarObject

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TransparentStatusBarObject.setStatusBar(this)
        val onBoardingPagerAdapter = ScreenSlidePagerAdapter(this)
        binding.vpLoginOnboarding.adapter = onBoardingPagerAdapter

        binding.dotsLoginOnboarding.setViewPager2(binding.vpLoginOnboarding)

        binding.btnLoginKakao.setOnClickListener { kakaoLoginCall(this) }

        binding.btnTempNext.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        binding.btnLoginGoogle.setOnClickListener { signIn() }
        auth = Firebase.auth

        binding.btnTempDisconnectGoogle.setOnClickListener { disconnectGoogle() }
    }

    private fun disconnectGoogle() {
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this) {
            Toast.makeText(this, "Disconnect From Google", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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
                if(task.isSuccessful) {
                    val user = auth.currentUser
                    //updateUI(user)
                    loginViewModel.setCurrentUser(user)
                    val intent = Intent(this, SignUpActivity::class.java)
                    Toast.makeText(this, "${user?.email}", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Auth Fail", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private val onBoardingInfoList = loginViewModel.onBoardingInfoList
        override fun getItemCount() = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return OnBoardingFragment(onBoardingInfoList[position])
        }

    }

    private fun kakaoLoginCall(context: Context) {
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
        if (LoginClient.instance.isKakaoTalkLoginAvailable(context)) LoginClient.instance.loginWithKakaoTalk(
            context,
            callback = callback
        )
        else LoginClient.instance.loginWithKakaoAccount(context, callback = callback)
    }

    private fun requestAdditionalUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("Kakao", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                if (user.kakaoAccount?.emailNeedsAgreement == false) Log.d("Kakao", "사용자계정에 이메일 없음")
                else if (user.kakaoAccount?.emailNeedsAgreement == true) Log.d("Kakao", "사용자에게")
                Log.i(
                    "Kakao",
                    "${user.id}, ${user.kakaoAccount?.email}, ${user.kakaoAccount?.profile?.nickname}"
                )
            }
        }
    }

    companion object {
        private const val NUM_PAGES = 3
        private const val RC_SIGN_IN = 9001
    }
}