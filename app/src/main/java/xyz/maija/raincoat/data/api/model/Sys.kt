package xyz.maija.apihomework.data.api.model


import com.google.gson.annotations.SerializedName

/*
    This is returned as part of the Weather API and keeps track of the part of the day this segment is in.
 */
data class Sys(
    @SerializedName("pod")
    val pod: String
)