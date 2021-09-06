package gb.android.yanweather.view

import gb.android.yanweather.domain.Weather

interface OnItemViewClickListener {
    fun onItemClick(weather: Weather)
}