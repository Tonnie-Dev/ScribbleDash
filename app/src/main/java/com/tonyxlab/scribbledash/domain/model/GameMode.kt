package com.tonyxlab.scribbledash.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.theme.Primary
import com.tonyxlab.scribbledash.presentation.theme.Success
import com.tonyxlab.scribbledash.presentation.theme.TertiaryContainer

sealed class GameMode(
    @StringRes val title: Int,
    @DrawableRes val image: Int,
    val outlineColor: Color
) {

    data object OneRoundWonder : GameMode(
            title = R.string.game_mode_one_round_wonder,
            image = R.drawable.game_mode_one_round_wonder,
            outlineColor = Success
    )

    data object SpeedDraw : GameMode(
            title = R.string.game_mode_speed_draw,
            image = R.drawable.game_mode_speed_draw,
            outlineColor = Primary

    )

    data object EndlessMode : GameMode(
            title = R.string.game_mode_endless,
            image = R.drawable.game_mode_endless,
            outlineColor = TertiaryContainer
    )

    companion object{

        fun getGameModeByTitle(@StringRes title: Int): GameMode {

            return when (title) {
                R.string.game_mode_speed_draw -> SpeedDraw
                R.string.game_mode_endless -> EndlessMode
                else -> OneRoundWonder
            }
        }

        fun getTitleByGameMode(gameMode: GameMode): Int {
            return when (gameMode) {
                SpeedDraw -> R.string.game_mode_speed_draw
                EndlessMode -> R.string.game_mode_endless
                else -> R.string.game_mode_one_round_wonder
            }
        }

        fun getGameModeByCode(code:Int): GameMode{

           return when(code){

               1 -> OneRoundWonder
               2 -> SpeedDraw
               else -> EndlessMode
           }
        }

    }
}