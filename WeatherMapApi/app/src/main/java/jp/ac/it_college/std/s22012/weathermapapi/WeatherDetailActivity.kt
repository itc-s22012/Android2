package jp.ac.it_college.std.s22012.weathermapapi

import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherDetailActivity : AppCompatActivity() {

    private lateinit var cityNameTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var humidityTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)

        cityNameTextView = findViewById(R.id.cityNameTextView)
        temperatureTextView = findViewById(R.id.temperatureTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        humidityTextView = findViewById(R.id.humidityTextView)

        val selectedCityId = intent.getIntExtra("EXTRA_SELECTED_CITY_ID", 0)
        FetchWeatherTask().execute(selectedCityId.toString())
    }

    private inner class FetchWeatherTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String? {
            val locationId = params[0]
            val apiKey = BuildConfig.APP_ID // ここにOpenWeatherMapのAPIキーを設定

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
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                parseWeatherData(result)
            } else {
                // エラー時の処理
                // 例: エラーメッセージを表示する
                cityNameTextView.text = "エラーが発生しました"
            }
        }

        private fun parseWeatherData(data: String) {
            try {
                val jsonObject = JSONObject(data)
                val cityName = jsonObject.getString("name")
                val main = jsonObject.getJSONObject("main")

                // ケルビンから摂氏に変換
                val temperatureKelvin = main.getDouble("temp")
                val temperatureCelsius = temperatureKelvin - 273.15

                val weatherArray = jsonObject.getJSONArray("weather")
                val weatherObject = weatherArray.getJSONObject(0)
                val description = weatherObject.getString("description")

                // 翻訳が必要なデータを使ってWeatherDataオブジェクトを作成
                val weatherData = WeatherData(
                    cityName,
                    temperatureCelsius,
                    description,
                    main.getInt("humidity")
                )

                // UIにデータを表示
                displayWeatherData(weatherData)
            } catch (e: Exception) {
                e.printStackTrace()
                // エラー時の処理
                // 例: エラーメッセージを表示する
                cityNameTextView.text = "エラーが発生しました"
            }
        }

        private fun displayWeatherData(weatherData: WeatherData) {
            cityNameTextView.text = weatherData.cityName
            temperatureTextView.text = "${weatherData.temperature.toInt()} ℃"
            descriptionTextView.text = weatherData.description
            humidityTextView.text = "湿度: ${weatherData.humidity}%"
        }
    }
}






