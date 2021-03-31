package com.teamounce.ounce.util

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
    fun setTextWatcher(view: EditText, textAttrChanged: InverseBindingListener) {
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textAttrChanged.onChange()
            }
        })
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setEditTextString(view: EditText, text: MutableLiveData<String>) {
        if (text.value == null) {
            view.setText("")
        } else {
            if (view.text.toString() != text.value) view.setText(text.value)
        }
    }

    @JvmStatic
    @BindingAdapter("setLottie")
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

    @JvmStatic
    @BindingAdapter("record:setSrcFrom", "record:setForm")
    fun setSrcFromStringData(view: ImageView, data: String, isURL: Boolean) {
        if (isURL) {
            Glide.with(view.context)
                .load(data)
                .into(view)
        } else {
            Glide.with(view.context)
                .load(Uri.parse(data))
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("tag:enabled")
    fun isAddEnabled(view: ImageButton, enabled: MediatorLiveData<Boolean>) {
        view.isEnabled = enabled.value ?: true
    }

    @JvmStatic
    @BindingAdapter("feed:totalSize")
    fun setSizeText(view: TextView, size: LiveData<Int>) {
        val sizeText = "전체 리뷰 개수(${size.value})"
        view.text = sizeText
    }
}