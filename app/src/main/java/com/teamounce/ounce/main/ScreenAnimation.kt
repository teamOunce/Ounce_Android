package com.teamounce.ounce.main

import kotlin.Int.Companion.MIN_VALUE

enum class ScreenAnimation(
    private val catNumRange: IntRange,
    private val lottieFile: String
) {
    NOTHING(MIN_VALUE..0, "home_img_nothing.json"),
    FIRST(1..5, "home_img_step0.json"),
    SECOND(6..10, "home_img_step1.json"),
    THIRD(11..15, "home_img_step2.json"),
    FOURTH(16..20, "home_img_step3.json"),
    FIFTH(21..Int.MAX_VALUE, "home_img_step4.json");

    private fun belongsTo(catNum: Int): Boolean = catNumRange.contains(catNum)

    companion object {
        fun by(catNum: Int): String {
            return values().find { it.belongsTo(catNum) }?.lottieFile
                ?: throw IllegalArgumentException("도대체 이 숫자는 어디서 나온거죠? 고양이 수: $catNum")
        }
    }
}