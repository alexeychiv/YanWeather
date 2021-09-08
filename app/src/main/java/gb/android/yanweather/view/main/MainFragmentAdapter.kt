package gb.android.yanweather.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import gb.android.yanweather.R
import gb.android.yanweather.domain.Weather
import gb.android.yanweather.view.OnItemViewClickListener

class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder>() {

    private var weatherData: List<Weather> = listOf()
    private lateinit var listener: OnItemViewClickListener
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    fun setOnItemViewClickListener(onItemViewClickListener: OnItemViewClickListener) {
        listener = onItemViewClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val holder = MainFragmentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_item, parent, false)
        )
        return holder;
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) = holder.render(weatherData[position])

    override fun getItemCount() = weatherData.size

    inner class MainFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun render(weather: Weather) {
            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text =
                weather.city.name
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "РАБОТАЕТ", Toast.LENGTH_SHORT).show()
                listener.onItemClick(weather)
            }
        }
    }
}