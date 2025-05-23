package com.tonyxlab.scribbledash.presentation.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountdownTimer(private val totalSeconds: Int) {

    private val _remainingSeconds = MutableStateFlow(totalSeconds)
    val remainingSeconds = _remainingSeconds.asStateFlow()

    private var isRunning = false
    private var elapsedSeconds = 0

    private var job: Job? = null

    fun start() {
        if (isRunning) return
        isRunning = true
        launchTimer()
    }

    fun pause() {
        isRunning = false
        job?.cancel()
        job = null
    }

    fun resume() {
        if (!isRunning && elapsedSeconds < totalSeconds) {
            isRunning = true
            launchTimer()
        }
    }

    fun stop() {
        pause()
        elapsedSeconds = 0
        _remainingSeconds.value = totalSeconds
    }



    private fun launchTimer() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (elapsedSeconds < totalSeconds && isRunning) {
                delay(1_000)
                elapsedSeconds++
                _remainingSeconds.value = totalSeconds - elapsedSeconds
            }
            isRunning = false
        }
    }
}
