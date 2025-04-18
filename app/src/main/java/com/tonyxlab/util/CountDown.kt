package com.tonyxlab.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CountDown {

    private val _remainingSecs = MutableStateFlow(0)
    val remainingSecs = _remainingSecs.asStateFlow()

    private fun countDown(secs:Int = 3){


        for (i in secs downTo 1){

            _remainingSecs.value = i
        }
    }
}