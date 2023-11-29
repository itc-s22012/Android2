// jp.ac.it_college.std.s22012.weathermapapi.WeatherData
package jp.ac.it_college.std.s22012.weathermapapi

data class WeatherData(
    val cityName: String,
    val temperature: Double,
    val description: String,
    val humidity: Int
    // 他にも必要なプロパティを追加
)



