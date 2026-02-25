package com.gongfu.a260223_trialmvisetup.presentation

sealed interface ButtonEvent {
    data class Error(val error: String): ButtonEvent
}