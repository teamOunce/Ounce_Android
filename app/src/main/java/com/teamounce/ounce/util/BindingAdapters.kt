package com.teamounce.ounce.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide


object BindingAdapters {
    @BindingAdapter("setSrcFromUrl")
    @JvmStatic
    fun setSrcFromUrl(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }

    @InverseBindingAdapter(attribute = "android:text", event = "textAttrChanged")
    @JvmStatic
    fun getTextString(view: EditText): String {
        return view.text.toString()
    }

    @JvmStatic
    @BindingAdapter("textAttrChanged")
    fun setTextWatcher(view: EditText, textAttrChagned: InverseBindingListener) {
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textAttrChagned.onChange();
            }
        })
    }

    @JvmStatic
    @BindingAdapter("login:setLottie")
    fun setLottieFile(view: LottieAnimationView, file: String) {
        view.setAnimation(file)
    }

    @JvmStatic
    @BindingAdapter("user:setEnabled")
    fun setButtonEnabled(view: Button, attr: MutableLiveData<Boolean>) {
        attr.value?.let { view.isEnabled = it }
    }

    @JvmStatic
    @BindingAdapter("toggleButtonText")
    fun setToggleButtonText(view: ToggleButton, text: String) {
        view.apply {
            textOn = text
            textOff = text
        }
    }
}