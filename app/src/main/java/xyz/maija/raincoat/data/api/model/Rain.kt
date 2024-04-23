package xyz.maija.apihomework.data.api.model


import com.google.gson.annotations.SerializedName

/*
    The millimeters of rain that happened in the last three hours
 */
data class Rain(
    @SerializedName("3h")
    val h: Double
)