package xyz.maija.raincoat.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.maija.apihomework.data.api.model.WeatherData


// documentation for api https://openweathermap.org/forecast5
const val BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/" //?lat=44.34&lon=10.99&units=metric&count=8&appid=9ec235daafa8f93bad9066f04ac55f07

interface APIServiceWeather {

    @GET("forecast")
    suspend fun getWeatherData(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("cnt") cnt: String, @Query("appid") appid: String): WeatherData
    // will add body and create body when compiles

    companion object {
        // for logging if needed
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client: OkHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()

        var apiService: APIServiceWeather? = null

        fun getInstance(): APIServiceWeather {

            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL_WEATHER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(APIServiceWeather::class.java)
            }

            return apiService!! // don't wanna have to unwrap it later

        } // getInstance

    } // companion object

} // APIService