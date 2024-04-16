package xyz.maija.raincoat.classes

import androidx.compose.ui.graphics.Color

class User {

    var hair: Hairstyle = Hairstyle.BALD
    var hotcold: Double = 50.0 // from 0 to 100
    var skincolor: Color = DEFAULT_SKIN_COLOR
    var location: Location = Location()
    var useCelsius: Boolean = true

    companion object {
        var DEFAULT_SKIN_COLOR: Color = Color(0xFFB7B7B7) // default grey
    }

} // User