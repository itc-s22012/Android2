package jp.ac.it_college.std.s22012.weathermapapi

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val cityList = CityDataList.cityList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.cityListView)

       
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cityList.map { it.name })
        listView.adapter = adapter

     
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedCity = cityList[position]

         
            val intent = Intent(this, WeatherDetailActivity::class.java).apply {
                putExtra("EXTRA_SELECTED_CITY_ID", selectedCity.locationId)
            }
            startActivity(intent)
        }
    }
}









