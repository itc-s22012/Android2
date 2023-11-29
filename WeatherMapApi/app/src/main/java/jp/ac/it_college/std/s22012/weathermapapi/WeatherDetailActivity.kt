package jp.ac.it_college.std.s22012.weathermapapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.ac.it_college.std.s22012.weathermapapi.databinding.FragmentWeatherDetailActivityBinding

// WeatherDetailActivity

class WeatherDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SELECTED_PREFECTURE = "extra_selected_prefecture"
    }

    private lateinit var binding: FragmentWeatherDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWeatherDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedPrefecture = intent.getStringExtra(EXTRA_SELECTED_PREFECTURE)

        // 選択された都道府県に基づいて天気情報を取得し、表示するメソッドを呼び出す
        getWeatherForLocation(selectedPrefecture)
    }

    private fun getWeatherForLocation(location: String?) {
        // OpenWeatherMap APIを使用して、選択された場所の天気情報を取得するコードを追加
        // ネットワークリクエストや天気情報の処理は先程の例を参考にしてください
        // 結果を画面のViewに表示する
        binding.weatherTextView.text = "天気情報がここに表示されます。"
    }
}

