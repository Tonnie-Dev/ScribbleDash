package com.tonyxlab.scribbledash.domain.model

data class Game(
    val mode: GameMode = GameMode.OneRoundWonder,
    val drawingsCount: Int = 0,
    val remainingSecs: Int = 0
)
fun Game.formatRemainingSecs(): String {
        val minutes = remainingSecs / 60
        val secondsText = "%02d".format(remainingSecs % 60)
        return "$minutes:$secondsText"
    }