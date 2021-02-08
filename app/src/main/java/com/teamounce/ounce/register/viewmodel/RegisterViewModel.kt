package com.teamounce.ounce.register.viewmodel

import androidx.lifecycle.ViewModel
import com.teamounce.ounce.data.remote.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {
}