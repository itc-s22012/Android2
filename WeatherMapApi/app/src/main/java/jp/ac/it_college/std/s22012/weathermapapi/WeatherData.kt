package jp.ac.it_college.std.s22012.weathermapapi

data class HourlyForecast(
    val time: String,
    val temperature: Double,
    val description: String
)

data class WeatherData(
    val cityName: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val description: String,
    val humidity: Double,
    val hourlyForecasts: List<HourlyForecast>
) {

    val maxTemperatureCelsius: Double
        get() = maxTemperature - 273.15

    val minTemperatureCelsius: Double
        get() = minTemperature - 273.15
}





