// WeatherFetcher.kt
package jp.ac.it_college.std.s22012.weathermapapi

import android.os.AsyncTask
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherFetcher(private val apiKey: String, private val listener: OnWeatherFetchListener) :
    AsyncTask<String, Void, String>() {

    interface OnWeatherFetchListener {
        fun onWeatherFetch(weatherData: WeatherData)
        fun onWeatherFetchError(errorMessage: String)
    }

    override fun doInBackground(vararg params: String): String? {
        val location = params[0]
        val apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=$location&appid=$apiKey"

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
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            parseWeatherData(result)
        } else {
            // Handle error
            listener.onWeatherFetchError("Error fetching weather data")
        }
    }

    private fun parseWeatherData(data: String) {
        try {
            val jsonObject = JSONObject(data)

            // 必要なデータを API レスポンスから取得
            val cityName = jsonObject.getString("name")
            val mainObject = jsonObject.getJSONObject("main")
            val temperature = mainObject.getDouble("temp")
            val description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description")
            val humidity = mainObject.getInt("humidity")

            // WeatherData クラスを構築
            val weatherData = WeatherData(
                cityName = cityName,
                temperature = temperature,
                description = description,
                humidity = humidity
                // 他にも必要なデータがあればここでセット
            )

            // リスナーに天気情報を渡す
            listener.onWeatherFetch(weatherData)
        } catch (e: Exception) {
            e.printStackTrace()
            // エラーハンドリング
            listener.onWeatherFetchError("Error parsing weather data")
        }
    }
}

