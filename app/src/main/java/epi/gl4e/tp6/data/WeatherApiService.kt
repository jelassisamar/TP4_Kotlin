package epi.gl4e.tp6.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apikey: String = "294b1883902e1813c69f6804e05215d4",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "fr"
    ): WeatherData
}