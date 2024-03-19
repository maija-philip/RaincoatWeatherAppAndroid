package xyz.maija.raincoat.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import xyz.maija.raincoat.R

val rubikFont = FontFamily(
    Font(R.font.rubik_extrabold, FontWeight.ExtraBold),
    Font(R.font.rubik_black, FontWeight.Black),
    Font(R.font.rubik_bold, FontWeight.Bold),
    Font(R.font.rubik_semibold, FontWeight.SemiBold),
    Font(R.font.rubik_medium, FontWeight.Medium),
    Font(R.font.rubik_regular, FontWeight.Normal),
    Font(R.font.rubik_light, FontWeight.Light),
)

data class Temperature (val min: Int, val max: Int);