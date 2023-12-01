package jp.ac.it_college.std.s22012.weathermapapi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherFetcher(
    private val apiKey: String,
    private val onWeatherFetchListener: OnWeatherFetchListener
) {

    interface OnWeatherFetchListener {
        fun onWeatherFetchList(weatherDataList: List<WeatherData>)
        fun onWeatherFetchError(errorMessage: String)
    }

    suspend fun fetch5DayWeatherData(locationId: String) {
        withContext(Dispatchers.IO) {
            val apiUrl = "https://api.openweathermap.org/data/2.5/forecast?id=$locationId&appid=$apiKey&lang=ja"

            try {
                val url = URL(apiUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                val inputStreamReader = InputStreamReader(connection.inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)

                val stringBuilder = StringBuilder()
                var line: String?

                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }

                bufferedReader.close()
                inputStreamReader.close()

                val weatherDataList = parse5DayWeatherData(stringBuilder.toString())

                withContext(Dispatchers.Main) {
                    onWeatherFetchListener.onWeatherFetchList(weatherDataList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    onWeatherFetchListener.onWeatherFetchError("Error fetching weather data")
                }
            }
        }
    }



    private fun parse5DayWeatherData(data: String): List<WeatherData> {
        val weatherDataList = mutableListOf<WeatherData>()

        try {
            val jsonObject = JSONObject(data)

            val cityName = jsonObject.getJSONObject("city").getString("name")

            val forecastList = jsonObject.getJSONArray("list")
            for (i in 0 until forecastList.length()) {
                val forecastObject = forecastList.getJSONObject(i)
                val dateTime = forecastObject.getString("dt_txt")
                val temperatureObject = forecastObject.getJSONObject("main")
                val temperature = temperatureObject.getDouble("temp")
                val weatherArray = forecastObject.getJSONArray("weather")
                val description = weatherArray.getJSONObject(0).getString("description")

                val weatherData = WeatherData(
                    cityName,
                    temperature,
                    temperature,
                    description,
                    0.0,
                    listOf(HourlyForecast(dateTime, temperature, description))
                )
                weatherDataList.add(weatherData)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return weatherDataList
    }
}

