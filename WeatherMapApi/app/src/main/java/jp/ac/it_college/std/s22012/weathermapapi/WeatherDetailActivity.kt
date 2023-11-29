package jp.ac.it_college.std.s22012.weathermapapi


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WeatherDetailActivity : AppCompatActivity(), WeatherFetcher.OnWeatherFetchListener {

    private val apiKey = BuildConfig.APP_ID // API キー

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)

        // MainActivity から選択された都市名を受け取る
        val selectedCity = intent.getStringExtra("EXTRA_SELECTED_CITY")

        // 天気情報を取得して表示
        getWeatherForLocation(selectedCity)
    }

    private fun getWeatherForLocation(location: String?) {
        WeatherFetcher(apiKey, this).execute(location)
    }

    override fun onWeatherFetch(weatherData: WeatherData) {
        // 天気情報を画面に表示する処理
        updateUI(weatherData)
    }

    override fun onWeatherFetchError(errorMessage: String) {
        // エラーを画面に表示する処理
        showError(errorMessage)
    }

    private fun updateUI(weatherData: WeatherData) {
        // 天気情報を画面に表示する処理をここに追加
        val cityNameTextView: TextView = findViewById(R.id.cityNameTextView)
        val temperatureTextView: TextView = findViewById(R.id.temperatureTextView)
        val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
        val humidityTextView: TextView = findViewById(R.id.humidityTextView)

        cityNameTextView.text = weatherData.cityName
        temperatureTextView.text = getString(R.string.temperature, weatherData.temperature)
        descriptionTextView.text = getString(R.string.description, weatherData.description)
        humidityTextView.text = getString(R.string.humidity, weatherData.humidity)
    }

    private fun showError(errorMessage: String) {
        // エラーを画面に表示する処理をここに追加
        val errorTextView: TextView = findViewById(R.id.errorTextView)
        errorTextView.text = errorMessage
    }
}






