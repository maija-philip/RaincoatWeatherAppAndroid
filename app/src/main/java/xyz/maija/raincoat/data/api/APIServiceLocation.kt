package xyz.maija.raincoat.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.maija.apihomework.data.api.model.GeoLocationData
import xyz.maija.apihomework.data.api.model.WeatherData

// documentation for api https://openweathermap.org/api/geocoding-api
const val BASE_URL_GEOLOCATION = "https://api.openweathermap.org/geo/1.0/" //?zip=E14,GB&appid=9ec235daafa8f93bad9066f04ac55f07

/*
    Facilitates getting the Geolocation Data from the api, with logging included to show what is happening.
 */
interface APIServiceLocation {

    @GET("zip")
    suspend fun getGeolocationData(@Query("zip") zip: String, @Query("appid") appid: String): GeoLocationData

    companion object {
        // for logging if needed
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client: OkHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()

        var apiService: APIServiceLocation? = null

        fun getInstance(): APIServiceLocation {

            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL_GEOLOCATION)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(APIServiceLocation::class.java)
            }

            return apiService!! // don't wanna have to unwrap it later

        } // getInstance

    } // companion object

} // APIService
