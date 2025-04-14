package com.tonyxlab.scribbledash.presentation.theme


import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tonyxlab.scribbledash.R

val BagelFat = FontFamily(Font(R.font.bagel_fat, FontWeight.Normal))
val Outfit = FontFamily(Font(R.font.outfit_var, FontWeight.Normal))

val Typography = Typography(
        displayLarge = TextStyle(
                fontFamily = BagelFat,
                fontWeight = FontWeight.Normal,
                fontSize = 66.sp,
                lineHeight = 80.sp,
        ),
        displayMedium = TextStyle(
                fontFamily = BagelFat,
                fontWeight = FontWeight.Normal,
                fontSize = 40.sp,
                lineHeight = 44.sp,
        ),
        headlineLarge = TextStyle(
                fontFamily = BagelFat,
                fontWeight = FontWeight.Normal,
                fontSize = 34.sp,
                lineHeight = 48.sp,
        ),
        headlineMedium = TextStyle(
                fontFamily = BagelFat,
                fontWeight = FontWeight.Normal,
                fontSize = 26.sp,
                lineHeight = 30.sp,
        ),
        headlineSmall = TextStyle(
                fontFamily = BagelFat,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                lineHeight = 26.sp,
        ),
        labelLarge = TextStyle(
                fontFamily = Outfit,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                lineHeight = 28.sp,
        ),
        labelMedium = TextStyle(
                fontFamily = Outfit,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight =24.sp,
        ),
        labelSmall = TextStyle(
                fontFamily = Outfit,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 18.sp,
        ),
        bodyLarge = TextStyle(
                fontFamily = Outfit,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                lineHeight = 24.sp,
        ),
        bodyMedium = TextStyle(
                fontFamily = Outfit,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
        ),
        bodySmall = TextStyle(
                fontFamily = Outfit,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 18.sp,
        )
)

object ExtendedTypography {
    val headlineXSmall = TextStyle(
            fontFamily = BagelFat,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp,
    )
    val labelXLarge = TextStyle(
            fontFamily = Outfit,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp,
    )
}

val Typography.headlineXSmall: TextStyle
    @Composable
    get() = ExtendedTypography.headlineXSmall

val Typography.labelXLarge: TextStyle
    @Composable
    get() = ExtendedTypography.labelXLarge
