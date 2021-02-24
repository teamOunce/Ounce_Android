package com.teamounce.ounce.util

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R

class ChipFactory {
    companion object {
        fun create(layoutInflater: LayoutInflater): Chip {
            return layoutInflater.inflate(R.layout.chip_filter, null, false) as Chip
        }
    }
}