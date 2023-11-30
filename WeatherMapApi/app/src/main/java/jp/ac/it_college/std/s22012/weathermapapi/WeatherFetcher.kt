package jp.ac.it_college.std.s22012.weathermapapi

import android.os.AsyncTask
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherFetcher(
    private val apiKey: String,
    private val onWeatherFetchListener: OnWeatherFetchListener
) : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String): String? {
        val location = params[0]
        val locationId = CityDataList.getLocationId(location)

        if (locationId != null) {
            val apiUrl = "https://api.openweathermap.org/data/2.5/weather?id=$locationId&appid=$apiKey&lang=ja"

            return try {
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

                stringBuilder.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            onWeatherFetchListener.onWeatherFetchError("Invalid location: $location")
        }

        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            parseWeatherData(result)
        } else {
            onWeatherFetchListener.onWeatherFetchError("Error fetching weather data")
        }
    }

    private fun parseWeatherData(data: String) {
        try {
            val jsonObject = JSONObject(data)
            val cityName = jsonObject.getString("name")
            val main = jsonObject.getJSONObject("main")

            
            val temperatureKelvin = main.getDouble("temp")
            val temperatureCelsius = temperatureKelvin - 273.15

            val weatherArray = jsonObject.getJSONArray("weather")
            val weatherObject = weatherArray.getJSONObject(0)
            val description = weatherObject.getString("description")

          
            val weatherData = WeatherData(
                cityName,
                temperatureCelsius,
                description,
                main.getInt("humidity")
            )

            onWeatherFetchListener.onWeatherFetch(weatherData)
        } catch (e: Exception) {
            e.printStackTrace()
            onWeatherFetchListener.onWeatherFetchError("Error parsing weather data")
        }
    }

    interface OnWeatherFetchListener {
        fun onWeatherFetch(weatherData: WeatherData)
        fun onWeatherFetchError(errorMessage: String)
    }
}


