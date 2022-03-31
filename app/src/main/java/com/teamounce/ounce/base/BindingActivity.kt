package com.teamounce.ounce.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.teamounce.ounce.component.LoadingDialog

abstract class BindingActivity<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    AppCompatActivity() {
    protected lateinit var binding: T

    private val loadingDialog by lazy {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
    }

    override fun onDestroy() {
        dismissLoadingDialog()
        super.onDestroy()
    }

    protected inner class LifeCycleEventLogger(
        private val className: String
    ) : LifecycleEventObserver {
        fun registerLogger(lifecycle: Lifecycle) {
            lifecycle.addObserver(this)
        }

        override fun onStateChanged(
            source: LifecycleOwner,
            event: Lifecycle.Event
        ) {
            Log.d("${className}LifeCycleEvent", "${lifecycle.currentState}")
        }
    }

    fun showLoadingDialog() {
        try {
            if (!loadingDialog.isShowing)
                loadingDialog.show()
        } catch (e: Exception) {
        }
    }

    fun dismissLoadingDialog() {
        try {
            if (loadingDialog.isShowing)
                loadingDialog.dismiss()
        } catch (e: Exception) {
        }
    }
}