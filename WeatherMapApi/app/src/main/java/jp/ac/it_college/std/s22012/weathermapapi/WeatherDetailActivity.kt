// WeatherDetailActivity.kt
package jp.ac.it_college.std.s22012.weathermapapi

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherDetailActivity : AppCompatActivity(), WeatherFetcher.OnWeatherFetchListener {

    private val apiKey = BuildConfig.APP_ID // API キー
    private val weatherFetcher = WeatherFetcher(apiKey, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)

   
        val selectedCity = intent.getStringExtra("EXTRA_SELECTED_CITY")

        if (selectedCity != null) {
            val locationId = CityDataList.getLocationId(selectedCity)

            if (locationId != null) {
                fetch5DayWeatherData(locationId.toString())
            } else {
                showError("無効な都市データです")
            }
        } else {
            showError("都市データが選択されていません")
        }
    }

    private fun fetch5DayWeatherData(locationId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            weatherFetcher.fetch5DayWeatherData(locationId)
        }
    }

    override fun onWeatherFetchList(weatherDataList: List<WeatherData>) {
        runOnUiThread {
            if (weatherDataList.isNotEmpty()) {
                updateUI(weatherDataList)
            } else {
                showError("利用可能な天気データがありません")
            }
        }
    }

    override fun onWeatherFetchError(errorMessage: String) {
        showError(errorMessage)
    }

    private fun updateUI(weatherDataList: List<WeatherData>) {
        val listView: ListView = findViewById(R.id.weatherListView)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            weatherDataList.flatMap { weatherData ->
                val mainInfo = "${weatherData.cityName}: " +
                        "最高気温: ${weatherData.maxTemperatureCelsius.toInt()}°C, " +
                        "最低気温: ${weatherData.minTemperatureCelsius.toInt()}°C, " +
                        "\n天気: ${weatherData.description}, " +
                        "\n湿度: ${weatherData.humidity}%"

                val hourlyInfo = weatherData.hourlyForecasts.joinToString("\n") { forecast ->
                    "時間: ${forecast.time}, 気温: ${forecast.temperature.toInt()}°C, 天気: ${forecast.description}"
                }

                listOf(mainInfo, hourlyInfo)
            }
        )
        listView.adapter = adapter
    }

    private fun showError(errorMessage: String) {
        val errorTextView: TextView = findViewById(R.id.errorTextView)
        errorTextView.text = errorMessage
    }
}


