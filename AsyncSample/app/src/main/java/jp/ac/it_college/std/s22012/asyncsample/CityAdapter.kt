package jp.ac.it_college.std.s22012.asyncsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.ac.it_college.std.s22012.asyncsample.databinding.LowBinding

class CityAdapter(val callback: (City) -> Unit) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    class ViewHolder(val binding: LowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = cityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.apply {
            text = cityList[position].name
            setOnClickListener {
                callback(cityList[position])
            }
        }
    }
}