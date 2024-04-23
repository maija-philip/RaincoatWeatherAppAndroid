package xyz.maija.apihomework.data.api.model


import com.google.gson.annotations.SerializedName

/*
    This is the object that is returned by the Weather API. It has the data about the place you searched as well as the number of segments you wanted and the list of segments with their data.
 */
data class WeatherData(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<WeatherSectionData>,
    @SerializedName("message")
    val message: Int
)