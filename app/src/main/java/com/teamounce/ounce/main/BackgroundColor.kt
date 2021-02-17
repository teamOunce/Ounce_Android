package com.teamounce.ounce.main

import android.graphics.Color

enum class BackgroundColor(
    private val catNumRange: IntRange,
    private val color: Int,
    private val lightBar: Boolean
) {
    NOTHING(Int.MIN_VALUE..0, Color.parseColor("#FFFFFF"), false),
    FIRST(1..5, Color.parseColor("#FFF9F3"), false),
    SECOND(6..10, Color.parseColor("#FFE9E0"), false),
    THIRD(11..15, Color.parseColor("#D4E0EC"), true),
    FOURTH(16..20, Color.parseColor("#ECEAEB"), false),
    FIFTH(21..Int.MAX_VALUE, Color.parseColor("#D8E1E0"), false);

    private fun belongsTo(catNum: Int): Boolean = catNumRange.contains(catNum)

    companion object {
        fun of(catNum: Int): Int {
            return values().find { it.belongsTo(catNum) }?.color
                ?: throw IllegalArgumentException("도대체 이 숫자는 어디서 나온거죠? 고양이 수: $catNum")
        }

        fun alsoStatusBar(catNum: Int): Boolean {
            return values().find { it.belongsTo(catNum) }?.lightBar
                ?: throw IllegalArgumentException("도대체 이 숫자는 어디서 나온거죠? 고양이 수: $catNum")
        }
    }
}