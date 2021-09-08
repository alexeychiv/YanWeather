package gb.android.yanweather.repository

import gb.android.yanweather.domain.Weather
import gb.android.yanweather.domain.getRussianCities
import gb.android.yanweather.domain.getWorldCities

class RepositoryImpl : Repository {

    override fun getWeatherFromRemoteSource(): Weather = Weather()

    override fun getWeatherFromLocalSource(): Weather = Weather()

    override fun getWeatherFromLocalStorageRus(): List<Weather> = getRussianCities()

    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCities()
}