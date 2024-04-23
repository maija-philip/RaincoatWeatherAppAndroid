package xyz.maija.apihomework.data.api.model


import com.google.gson.annotations.SerializedName

/*
    The coordinates of the place that was entered in the Weather API
 */
data class Coord(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)