package com.teamounce.ounce.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R

interface ChipFactory {
    fun create(layoutInflater: LayoutInflater): Chip
}

class ChipClient {
    companion object {
        fun create(layoutInflater: LayoutInflater, factory: ChipFactory = NormalChipFactory()): Chip {
            return factory.create(layoutInflater)
        }
    }
}

class NormalChipFactory : ChipFactory {
    @SuppressLint("InflateParams")
    override fun create(layoutInflater: LayoutInflater): Chip {
        return layoutInflater.inflate(R.layout.chip_filter, null, false) as Chip
    }
}

class SmallChipFactory : ChipFactory {
    @SuppressLint("InflateParams")
    override fun create(layoutInflater: LayoutInflater): Chip {
        return layoutInflater.inflate(R.layout.small_chip_filter, null, false) as Chip
    }
}