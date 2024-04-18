package xyz.maija.raincoat.data.entities

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import xyz.maija.raincoat.classes.Hairstyle
import xyz.maija.raincoat.classes.Location

@Entity
class User {

    @ColumnInfo(name="hair")
    var hair: Hairstyle = Hairstyle.BALD
    @ColumnInfo
    var hotcold: Double = 50.0 // from 0 to 100
    @ColumnInfo
    var skincolor: Color = DEFAULT_SKIN_COLOR
    @ColumnInfo
    var location: Location? = null
    @ColumnInfo
    var useCelsius: Boolean = true

    companion object {
        var DEFAULT_SKIN_COLOR: Color = Color(0xFFB7B7B7) // default grey
    }

} // User