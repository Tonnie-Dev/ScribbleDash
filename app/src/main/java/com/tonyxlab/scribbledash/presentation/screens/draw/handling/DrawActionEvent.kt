package com.tonyxlab.scribbledash.presentation.screens.draw.handling

sealed interface DrawActionEvent {

    data class NavigateToPreviewScreen(

        val sampleSvgPathData: List<String>,
        val userPathData: List<String>,
        val viewPortWidth: Float,
        val viewPortHeight: Float,
        val similarityScore: Int

    ) : DrawActionEvent

}