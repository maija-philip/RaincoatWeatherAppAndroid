package xyz.maija.apihomework.data.api.model


import com.google.gson.annotations.SerializedName

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