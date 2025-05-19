package epi.gl4e.tp6.data

data class WeatherData(
    val sys: Sys,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class Weather(
    val description: String
)

data class Wind(
    val speed: Double
)

data class Sys(
    val country: String
)