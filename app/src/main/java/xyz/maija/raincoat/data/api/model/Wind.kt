package xyz.maija.apihomework.data.api.model


import com.google.gson.annotations.SerializedName

/*
    Information about the windiness of a specific weather section from the Weather API
 */
data class Wind(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double,
    @SerializedName("speed")
    val speed: Double
)