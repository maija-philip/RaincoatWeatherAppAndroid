package xyz.maija.raincoat.classes

import androidx.compose.ui.graphics.Color

class User {

    var hair: Hairstyle = Hairstyle.BALD
    var hotcold: Double = 50.0 // from 0 to 100
    var skincolor: Color = Color.Red
    var location: Location = Location()
    var useCelsius: Boolean = true

} // User