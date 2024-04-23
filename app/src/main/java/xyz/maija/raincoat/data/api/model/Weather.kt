package xyz.maija.apihomework.data.api.model


import com.google.gson.annotations.SerializedName

/*
    Stores basic information about the weather as well as the main information you will need. This is returned in the Weather Data API
 */
data class Weather(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)