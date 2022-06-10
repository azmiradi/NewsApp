package azmithabet.com.news.representation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import bumblebee.io.mid.ui.theme.BlueDark
import bumblebee.io.mid.ui.theme.TextColor

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = NotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = BlueDark
    ),
    body1 = TextStyle(
        fontFamily = NotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = TextColor
    ),
    body2 = TextStyle(
        fontFamily = NotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextColor
    )
)
