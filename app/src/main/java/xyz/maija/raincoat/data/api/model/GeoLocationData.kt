package xyz.maija.apihomework.data.api.model


import com.google.gson.annotations.SerializedName

/*
    All the data you get back about the zip code and country given to the Geolocation API
 */
data class GeoLocationData(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("zip")
    val zip: String
)