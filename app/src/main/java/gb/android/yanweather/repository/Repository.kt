package gb.android.yanweather.repository

import gb.android.yanweather.domain.Weather

interface Repository {
    fun getWeatherFromRemoteSource(): Weather
    fun getWeatherFromLocalSource(): Weather
}