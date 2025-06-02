package com.tonyxlab.scribbledash.presentation.screens.draw.handling

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.tonyxlab.scribbledash.data.ColorSerializer
import com.tonyxlab.scribbledash.data.ListOffsetSerializer
import com.tonyxlab.scribbledash.domain.model.DifficultyLevel
import com.tonyxlab.scribbledash.domain.model.GameMode
import kotlinx.serialization.Serializable

@Stable
data class DrawUiState(
    val selectedColor: Color = Color.Black,
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val currentSvgPath: RandomVectorData = RandomVectorData(),
    val buttonsState: ButtonsState = ButtonsState(),
    val undoStack: List<PathData> = emptyList(),
    val remainingPreviewSeconds: Int = 0,
    val remainingSpeedDrawSeconds: Int = 0,
    val canvasSize: Size = Size.Zero,
    val similarityScore: Int = 0,
    val counter:Int = 0,
    val gameMode: GameMode = GameMode.OneRoundWonder,
    val difficultyLevel: DifficultyLevel = DifficultyLevel.BEGINNER

) {

    @Serializable
    @Stable
    data class PathData(
        val id: Long = 0L,
        @Serializable(with = ColorSerializer::class)
        val color: Color = Color.Black,
        @Serializable(with = ListOffsetSerializer::class)
        val path: List<Offset> = emptyList()
    )

    @Stable
    data class ButtonsState(
        val undoButtonEnabled: Boolean = false,
        val redoButtonEnabled: Boolean = false,
        val submitButtonEnabled: Boolean = false
    )

    @Stable
    data class RandomVectorData(
        val paths: List<String> = emptyList(),
        val viewportWidth: Float = 0f,
        val viewportHeight: Float = 0f
    )


}