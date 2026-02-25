package com.gongfu.a260223_trialmvisetup.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class ButtonState(
    val counter: Int = 0,
    val magicNumber: Int = 0,
    val isResetting: Boolean = false,
)
