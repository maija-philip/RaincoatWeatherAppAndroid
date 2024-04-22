package xyz.maija.raincoat.data.entities

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter
import xyz.maija.raincoat.classes.Hairstyle
import xyz.maija.raincoat.classes.Location

// https://developer.android.com/training/data-storage/room/referencing-data

// Convert my objs and enums into basic types in order to user room to store in database
class Converters {
    @TypeConverter
    fun toHair(value: Int?): Hairstyle? {
        return value?.let { Hairstyle.fromInt(value) }
    }
    @TypeConverter
    fun fromHair(hair: Hairstyle?): Int? {
        return hair?.value
    }

    @TypeConverter
    fun toLocation(value: String?): Location? {
        return value?.let { Location.fromString(value) }
    }
    @TypeConverter
    fun fromLocation(location: Location?): String? {
        return location?.toString()
    }

    @TypeConverter
    fun toColor(value: Int?): Color? {
        return value?.let { Color(value) }
    }
    @TypeConverter
    fun fromColor(color: Color?): Int? {
        return color?.toArgb()
    }

} // Converters