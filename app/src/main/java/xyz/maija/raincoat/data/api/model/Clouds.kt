package xyz.maija.apihomework.data.api.model


import com.google.gson.annotations.SerializedName

/*
    Data from Weather API which stores the percent cloudy the day is.
 */
data class Clouds(
    @SerializedName("all")
    val all: Int
)