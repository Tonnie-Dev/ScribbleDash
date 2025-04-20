package com.tonyxlab.utils

import androidx.compose.ui.Modifier

inline fun Modifier.thenIf(flag: Boolean, modifierBuilder: Modifier.() -> Modifier): Modifier =
    if (flag) this.modifierBuilder() else this
