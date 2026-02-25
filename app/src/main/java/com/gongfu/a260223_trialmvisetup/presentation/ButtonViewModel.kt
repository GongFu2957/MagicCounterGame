package com.gongfu.a260223_trialmvisetup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ButtonViewModel : ViewModel() {


    private val _state = MutableStateFlow(ButtonState())
    val state = _state.asStateFlow()

    private val _events = Channel<ButtonEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        // Initialize State
        viewModelScope.launch {
            initRandomState()
        }

        println("Random number start: ${_state.value.magicNumber}")
    }

    private suspend fun initRandomState() {
        viewModelScope.launch {
            //Resetting UI
            _state.update { it.copy(isResetting = true) }
            delay(1000)

            val magic = (1..10).random()

            var counter = (1..10).random()
            while (counter == magic) {
                counter = (1..10).random()
            }

            _state.update {
                it.copy(
                    counter = counter,
                    magicNumber = magic,
                    isResetting = false
                )
            }

            println("New game started. Random number is now: $magic")
        }
    }

    fun onAction(action: ButtonAction) {
        when (action) {
            is ButtonAction.onIncrement -> {
                onIncButtonClicked()
            }
            is ButtonAction.onDecrement -> {
                onDecButtonClicked()
            }
        }
    }

    private fun onIncButtonClicked() {
        viewModelScope.launch {
            _state.update { it.copy(counter = (it.counter + 1).coerceAtMost(10)) }
            checkErrorLimit()
        }
    }

    private fun onDecButtonClicked() {
        viewModelScope.launch {
            _state.update { it.copy(counter = (it.counter - 1).coerceAtLeast(0)) }
            checkErrorLimit()
        }
    }

    private fun checkErrorLimit() {
        val currentCount = _state.value.counter
        if (currentCount == _state.value.magicNumber) {
            viewModelScope.launch {
                _events.send(ButtonEvent.Error("Random Number is ${currentCount}! Resetting..."))

                // Reset back to Initial state
                initRandomState()
            }
        }
    }
}
