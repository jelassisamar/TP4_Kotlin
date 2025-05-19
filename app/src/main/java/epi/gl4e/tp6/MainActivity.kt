package epi.gl4e.tp6

import adapters.CityAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import epi.gl4e.tp6.data.WeatherApiService
import epi.gl4e.tp6.data.WeatherData
import epi.gl4e.tp6.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CityAdapter.OncityClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherApiService: WeatherApiService

    private val cities = arrayOf("Tunis", "Paris", "London", "Beijing",
        "Tokyo", "Ottawa", "Algiers", "Moscow", "Berlin", "Madrid")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApiService = retrofit.create(WeatherApiService::class.java)

        binding.recyclerview.adapter = CityAdapter(cities, this)
        binding.recyclerview.setHasFixedSize(true)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCityClick(city: String) {
        getWeatherData(city)
    }

    private fun getWeatherData(city: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = weatherApiService.getCurrentWeather(city)
                withContext(Dispatchers.Main) {
                    updateWeatherUI(response)
                    Toast.makeText(
                        this@MainActivity,
                        "Météo chargée pour $city",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Erreur: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun updateWeatherUI(weatherData: WeatherData) {
        binding.apply {
            textCountry.text = "Pays: ${weatherData.sys.country}"
            textTemperature.text = "Température: ${weatherData.main.temp}°C"
            textHumidity.text = "Humidité: ${weatherData.main.humidity}%"
            textWindSpeed.text = "Vent: ${weatherData.wind.speed} km/h"
            textDescription.text = "Temps: ${weatherData.weather[0].description.capitalize()}"
        }
    }
}