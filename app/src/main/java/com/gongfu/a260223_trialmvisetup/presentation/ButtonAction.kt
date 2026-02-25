package com.gongfu.a260223_trialmvisetup.presentation

sealed interface ButtonAction {
    data object onIncrement : ButtonAction
    data object onDecrement : ButtonAction
}