package xyz.maija.raincoat.classes

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import xyz.maija.raincoat.R

// TODO: I thing there is probably a better way to implement this, but I haven't found out how
// I need to turn temperature section, hair, and color into the res drawable images
@Composable
fun getWeatherImg(name: String): Int {
    val isDarkMode = isSystemInDarkTheme()

    Log.d("MEP", "getWeatherImg: we got here")

    when (name.lowercase()) {
        "cold_bald" -> return if(isDarkMode) R.drawable.cold_bald_dark else R.drawable.cold_bald_light
        "cold_long" -> return if(isDarkMode) R.drawable.cold_long_dark else R.drawable.cold_long_light
        "cold_short" -> return if(isDarkMode) R.drawable.cold_short_dark else R.drawable.cold_short_light
        "cool_bald" -> return if(isDarkMode) R.drawable.cool_bald_dark else R.drawable.cool_bald_light
        "cool_long" -> return if(isDarkMode) R.drawable.cool_long_dark else R.drawable.cool_long_light
        "cool_short" -> return if(isDarkMode) R.drawable.cool_short_dark else R.drawable.cool_short_light
        "freezing_bald" -> return if(isDarkMode) R.drawable.freezing_bald_dark else R.drawable.freezing_bald_light
        "freezing_long" -> return if(isDarkMode) R.drawable.freezing_long_dark else R.drawable.freezing_long_light
        "freezing_short" -> return if(isDarkMode) R.drawable.freezing_short_dark else R.drawable.freezing_short_light
        "frigid_bald" -> return if(isDarkMode) R.drawable.frigid_bald_dark else R.drawable.frigid_bald_light
        "frigid_long" -> return if(isDarkMode) R.drawable.frigid_long_dark else R.drawable.frigid_long_light
        "frigid_short" -> return if(isDarkMode) R.drawable.frigid_short_dark else R.drawable.frigid_short_light
        "frozen_bald" -> return if(isDarkMode) R.drawable.frozen_bald_dark else R.drawable.frozen_bald_light
        "frozen_long" -> return if(isDarkMode) R.drawable.frozen_long_dark else R.drawable.frozen_long_light
        "frozen_short" -> return if(isDarkMode) R.drawable.frozen_short_dark else R.drawable.frozen_short_light
        "hot_bald" -> return if(isDarkMode) R.drawable.hot_bald_dark else R.drawable.hot_bald_light
        "hot_long" -> return if(isDarkMode) R.drawable.hot_long_dark else R.drawable.hot_long_light
        "hot_short" -> return if(isDarkMode) R.drawable.hot_short_dark else R.drawable.hot_short_light
        "scorching_bald" -> return if(isDarkMode) R.drawable.scorching_bald_dark else R.drawable.scorching_bald_light
        "scorching_long" -> return if(isDarkMode) R.drawable.scorching_long_dark else R.drawable.scorching_long_light
        "scorching_short" -> return if(isDarkMode) R.drawable.scorching_short_dark else R.drawable.scorching_short_light
        "warm_bald" -> return if(isDarkMode) R.drawable.warm_bald_dark else R.drawable.warm_bald_light
        "warm_long" -> return if(isDarkMode) R.drawable.warm_long_dark else R.drawable.warm_long_light
        "warm_short" -> return if(isDarkMode) R.drawable.warm_short_dark else R.drawable.warm_short_light
        else -> {
            Log.d("MEP", "getWeatherImg: We shouldn't be going here right now! Please use a specific string")
            return if(isDarkMode) R.drawable.scorching_bald_dark else R.drawable.scorching_bald_light
        }
    } // when (aka switch statement)
} // getWeatherImg
