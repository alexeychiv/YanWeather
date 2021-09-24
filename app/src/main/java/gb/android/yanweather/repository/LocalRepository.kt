package gb.android.yanweather.repository

import gb.android.yanweather.domain.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}